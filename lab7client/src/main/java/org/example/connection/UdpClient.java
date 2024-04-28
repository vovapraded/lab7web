package org.example.connection;
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
import java.util.Arrays;
import java.util.Iterator;

import com.google.common.primitives.Bytes;


public class UdpClient  {
    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;
    private final CurrentConsole currentConsole = CurrentConsole.getInstance();
private DatagramChannel client;
    private final InetSocketAddress serverSocketAddress;
    private final InetAddress serverAddress;

    private final int serverPort = PropertyUtil.getPort();
    private  SocketAddress clientAddress;

    {
        try {
            serverAddress = InetAddress.getByName(PropertyUtil.getAddress());
            serverSocketAddress = new InetSocketAddress(serverAddress, PropertyUtil.getPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public UdpClient()  {
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
            byte k = 0;
            if (i == 0){
                k+=1;
            }
            if (i == packets.length - 1) {
                k +=2;
            }
            packets[i] = Bytes.concat(Arrays.copyOfRange(data,i*DATA_SIZE,(i+1)*DATA_SIZE), new byte[]{k});

        }

        for (byte[] packet : packets) {
            ByteBuffer buffer = ByteBuffer.wrap(packet);
            client.send(buffer, serverSocketAddress);
        }
   }

    private byte[] receiveData(int bufferSize,boolean isOnce) throws IOException,NoResponseException {
        var buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress address = null;
        Selector selector = Selector.open();
        client.register(selector, SelectionKey.OP_READ);
        int timeout = 5000;
        if (isOnce) timeout=100;
        while(address == null) {
                address = waitResponse(selector,timeout,buffer,isOnce);
                if (isOnce) break;
        }
        return buffer.array();
    }

    private SocketAddress  waitResponse(Selector selector, int timeout, ByteBuffer buffer,boolean isOnce) throws IOException,NoResponseException {
        while (true) {
            if (selector.select(timeout) == 0) {
                throw new NoResponseException("Нет ответа более " + timeout / 1000 + " секунд. Проверьте соединение и повторите запрос");
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isReadable()) {
                    return client.receive(buffer);
                }
            }

        }

    }


    private byte[] receiveData(boolean isOnce) throws NoResponseException {
        var received = false;
        var result = new byte[0];
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
                if (data[data.length - 1] == 1) {
                    received = true;
                }
                result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
            }
            return result;
        }


    public Response getResponse(boolean isOnce) throws  NoResponseException, DeserializeException {
        return Deserializer.deserialize(receiveData(isOnce), Response.class);
    }

}
