package org.example.network;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = "Response".hashCode();
    @Builder.Default
    private ArrayList<String> message = new ArrayList<>();
    private transient SocketAddress address;
    private boolean loginCorrect;
    private boolean passwordCorrect;
    public String getMessageBySingleString(){
       return  String.join("\n", message);
    }

}
