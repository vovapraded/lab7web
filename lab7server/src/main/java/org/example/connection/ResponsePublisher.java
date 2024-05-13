package org.example.connection;

import org.example.network.Response;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ResponsePublisher {
    private static List<ResponseListener> listeners = new ArrayList<>();

    // Метод для добавления слушателей
    public static void addListener(ResponseListener listener) {
        listeners.add(listener);
    }

    // Метод для генерации события
    public static  void sendResponse(Response response, SocketAddress address) {
        for (ResponseListener listener : listeners) {
            listener.onResponse(response,address);

        }
    }
}
