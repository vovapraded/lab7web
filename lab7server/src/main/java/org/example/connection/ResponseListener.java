package org.example.connection;

import java.net.SocketAddress;

public interface ResponseListener {
    void onResponse(byte[] data, SocketAddress address);
}
