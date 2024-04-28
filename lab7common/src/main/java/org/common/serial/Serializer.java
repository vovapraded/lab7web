package org.common.serial;

import java.io.*;

public class Serializer {

    public static  byte[] serialize(Object object ) throws SerializeException {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            objectStream.flush();
            return byteStream.toByteArray();
        }catch (IOException e){
            throw new SerializeException("Ошибка сереализации объекта "+object.getClass());

        }


    }
}