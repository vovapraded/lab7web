package org.example.connection;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.common.commands.Command;
import org.common.utility.PropertyUtil;
import org.example.managers.ExecutorOfCommands;
import org.example.utility.Deserializer;
import org.example.utility.RecieveDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;

public class UdpServer implements ResponseListener {

    private final InetSocketAddress serverAddress;
    private final ExecutorOfCommands executor;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);
    private HashMap<SocketAddress, AbstractMap.SimpleEntry< byte[], Integer>> clients = new HashMap<>();

    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;

    private static final Logger logger = LoggerFactory.getLogger(UdpServer.class);

    private DatagramChannel datagramChannel;
    private boolean running=true;
    public UdpServer(ExecutorOfCommands executor)  {
        this.executor =executor;
        this.serverAddress = new InetSocketAddress(PropertyUtil.getAddress(),PropertyUtil.getPort());
        openNewSocket();
        logger.debug("Открыт сокет");
    }


    private void openNewSocket(){
        try {
            this.datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false); // Устанавливаем неблокирующий режим
            datagramChannel.bind(serverAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void run() throws RecieveDataException, SocketException {

        while (running) {
                var commandAndAddr = receiveData();
                executor.executeCommand(commandAndAddr);
        }
    }
//    public void connectToClient(SocketAddress addr) throws SocketException {
//        datagramChannel.setSoTimeout(10000);
//
//    }
//
//    public void disconnectFromClient() throws SocketException {
//        datagramChannel.setSoTimeout(0);
//
//    }



    public ImmutablePair receiveData() throws RecieveDataException {
        var received = false;
        var result = new byte[0];
        SocketAddress addr = null;
        int i=1;
        while (!received){
            var buffer=  ByteBuffer.allocate(PACKET_SIZE);
            buffer.clear();
            try {
                addr = datagramChannel.receive(buffer);
                if (buffer.position() == 0) {
                    // Если размер данных в буфере равен нулю, пропускаем итерацию цикла и продолжаем ожидать данных
                    continue;
                }
            } catch (IOException e) {
                throw new RecieveDataException("Не удалось получить данные, адрес клиента: " + addr);
            }
            var data = buffer.array();
            if (data[data.length-1] == 3 || data[data.length-1] == 1){
                i = 1;
            }else{
                i = clients.get(addr).getValue();
            }
            if ((data[data.length-1] == 1  )&& clients.containsKey(addr)){
                logger.debug("Получен " + Arrays.hashCode(data) + " пакет запроса с адреса " + addr + " - пакет " + i+" предыдущая команда не была получена полностью");
            }
            if ((data[data.length-1] == 3  )&& clients.containsKey(addr)){
                logger.debug("Получен " + Arrays.hashCode(data) + "последний пакет запроса с адреса " + addr + " - пакет " + i+" предыдущая команда не была получена полностью");
            }



            if (data[data.length - 1] == 2 || data[data.length - 1] == 3) {
                received = true;

                logger.debug("Получен " + Arrays.hashCode(data) + " последний пакет запроса с адреса " + addr + " - пакет " + i);
            }
            else {
                logger.debug("Получен "+ Arrays.hashCode(data) +" пакет "+i+" с адреса "+addr);
            }
           var resultPrevious = new byte[0];
            if (clients.containsKey(addr)) resultPrevious = clients.get(addr).getKey();
            result = Bytes.concat(resultPrevious, Arrays.copyOf(data, data.length - 1));
            clients.put(addr, new AbstractMap.SimpleEntry<>(result, i + 1));

        }
        clients.remove(addr);
        Command command = Deserializer.deserialize(result);
        logger.debug("Команда "+command.getClass().getName()+" десериализованна успешно");
        return new ImmutablePair<>(command, addr);
    }

    private void sendData(byte[] data, SocketAddress address) throws IOException {
        byte[][] packets=new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][PACKET_SIZE];
        for (int i = 0; i<packets.length;i++){

            if (i == packets.length - 1) {
                packets[i] = Bytes.concat(Arrays.copyOfRange(data,i*DATA_SIZE,(i+1)*DATA_SIZE), new byte[]{1});
            } else {
                packets[i] = Bytes.concat(Arrays.copyOfRange(data,i*DATA_SIZE,(i+1)*DATA_SIZE), new byte[]{0});
            }
        }

        for (byte[] packet : packets) {
            // Создаем буфер для текущего пакета
            ByteBuffer buffer = ByteBuffer.wrap(packet);
            // Отправляем пакет на указанный адрес
            datagramChannel.send(buffer, address);
            // Очищаем буфер после отправки пакета (это необходимо в неблокирующем режиме)
            buffer.clear();

        }
    }


    @Override
    public void onResponse(byte[] data, SocketAddress address) {
        try {
            sendData(data,address);
        } catch (IOException e) {
            logger.error("Не получилось отправить ответ клиенту: "+address);
        }
    }
}
