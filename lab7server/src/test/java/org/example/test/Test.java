package org.example.test;

import java.util.HashMap;

public class Test {
    @org.junit.jupiter.api.Test
    public void check(){
        HashMap hashMap = new HashMap<>();
        var val = hashMap.values();
        hashMap.put(1,1);
        System.out.println(val);
    }
}
