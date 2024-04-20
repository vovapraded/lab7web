package org.common.utility;

import java.net.SocketAddress;
import java.util.Scanner;

/**
 * a class for reading and writing from the console
 */
public abstract   class Console {
    protected Console(){
    }

    public abstract  void addToSend( String s,SocketAddress address);

    public abstract void selectFileScanner(Scanner scanner);

    public abstract void send(SocketAddress address);

    public abstract String getInputFromCommand(int minCountOfArgs,int maxCountOfArgs) ;

    public void print(String s) {
        System.out.println(s);
    }
}