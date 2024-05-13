package org.example.connection;

import org.example.network.Response;

import java.net.SocketAddress;

public interface ResponseListener {
    void onResponse(Response response, SocketAddress address);
}
