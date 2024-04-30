package org.example.connection;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.common.commands.Command;
import org.common.network.Response;
import org.common.network.SendException;
import org.common.serial.DeserializeException;
import org.common.serial.Deserializer;
import org.common.serial.SerializeException;
import org.common.serial.Serializer;
import org.example.utility.CurrentConsole;
import org.example.utility.NoResponseException;
import org.common.utility.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

import com.google.common.primitives.Bytes;
public class UdpClient  {
    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;
    private final CurrentConsole currentConsole = CurrentConsole.getInstance();
private DatagramChannel client;
    private UdpClient(){
    }
    @Getter
    private static UdpClient instance = new UdpClient();
    private final InetAddress serverAddress;


    private final InetSocketAddress serverSocketAddress;

    private final int serverPort = PropertyUtil.getPort();
    private  SocketAddress clientAddress;
    {
        try {
            serverAddress = InetAddress.getByName(PropertyUtil.getAddress());
            serverSocketAddress = new InetSocketAddress(serverAddress, PropertyUtil.getPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        currentConsole.print("Пытаемся открыть канал для соединения с сервером");
        boolean channelIsOpen = false;
        int i = 0;
           while (!channelIsOpen && i<10) {
               try {
                       this.client = DatagramChannel.open().bind(null).connect(serverSocketAddress);
                       this.client.configureBlocking(false);
                       channelIsOpen=true;
               } catch (IOException e) {
                   i++;
               }
           }
           if (channelIsOpen) {
               currentConsole.print("Канал открыт");
               currentConsole.printHello();

           }else {
               currentConsole.print("Не удалось открыть канал, проверьте настройки соединения и перезапустите программу");

        }
    }




    public void sendCommand(Command command) throws SerializeException {
        try {
            sendData(Serializer.serialize(command));
        } catch (IOException e) {
            throw new SendException("Ошибка отправки данных, поробуйте ещё раз");
        }


    }
    public void sendData(byte[] data) throws IOException {
        byte[][] packets=new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][PACKET_SIZE];
        for (int i = 0; i<packets.length;i++){

            if (i == packets.length - 1) {
                packets[i] = Bytes.concat(Arrays.copyOfRange(data,i*DATA_SIZE,(i+1)*DATA_SIZE), new byte[]{(byte) -(i+1)});
            }
            else {
                packets[i] = Bytes.concat(Arrays.copyOfRange(data, i * DATA_SIZE, (i + 1) * DATA_SIZE), new byte[]{(byte) (i + 1)});
            }

        }
        for (byte[] packet : packets) {
            ByteBuffer buffer = ByteBuffer.wrap(packet);
            client.send(buffer, serverSocketAddress);
            System.out.println("Пакет отправлен");


        }
   }

    private byte[] receiveData(int bufferSize,boolean isOnce) throws IOException,NoResponseException {
        var buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress address = null;
        Selector selector = Selector.open();
        client.register(selector, SelectionKey.OP_READ);
        long startTime = System.currentTimeMillis();
        int timeout = isOnce ? 1 : 5000; // Устанавливаем таймаут в зависимости от значения isOnce
        address = waitResponse(selector,timeout,buffer);
        selector.close(); // Закрываем селектор после использования
        if (address == null) throw new NoResponseException("Нет ответа более " + timeout / 1000 + " секунд. Проверьте соединение и повторите запрос");
        return buffer.array();
    }

    private SocketAddress  waitResponse(Selector selector, int timeout, ByteBuffer buffer) throws IOException,NoResponseException {
        selector.select(timeout); // Ожидаем таймаут
        Set<SelectionKey> keys = selector.selectedKeys();
        var iter = keys.iterator();
        if (iter.hasNext()) {
            SelectionKey key = iter.next(); iter.remove();
            if (key.isValid()) {
               if (key.isReadable()) {
                   return client.receive(buffer);
               }
            }
        }
        return null;
    }





    private byte[] receiveData(boolean isOnce) throws NoResponseException {
        var received = false;
        var result = new ArrayList();
        var sizeOfResponse = Byte.MAX_VALUE;
        while (!received) {
            byte[] data = new byte[0];
            try {
                data = receiveData(PACKET_SIZE, isOnce);

            } catch (IOException e) {
                throw new NoResponseException("Не получилось получить ответ от сервера, проверьте настройки соединения и повторите запрос");
            }
                if (data.length == 0) {
                    throw new NoResponseException("Ответ пустой");
                }
            var lastChunk = data[data.length - 1];
            data = Arrays.copyOf(data, data.length - 1);
            if (lastChunk < 0) {
                    sizeOfResponse = (byte) -lastChunk;
                }
            result.add(new ImmutablePair<byte[], Byte>(data, (byte) Math.abs(lastChunk)));
            if (result.size() == sizeOfResponse){
                received = true;
            }
        }
        return sortPackets(result);
    }
    private byte[] sortPackets(ArrayList<ImmutablePair<byte[],Byte>> result)  {
        result.sort(Comparator.comparing(pair -> pair.getRight()));
        var response = result.stream().map((pair) -> pair.getLeft()).reduce(new byte[0], (arr1, arr2) ->
            Bytes.concat(arr1, arr2));
        return response;
    }


        public Response getResponse(boolean isOnce) throws  NoResponseException, DeserializeException {
         return Deserializer.deserialize(receiveData(isOnce), Response.class);
    }

}
