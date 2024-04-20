package org.example.utility;

import org.common.commands.Command;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class Deserializer {

    public static Command deserialize(byte[] data) throws RecieveDataException{
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        try {
            // Создание объектного потока ввода для десериализации объекта
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);

// Десериализация объекта из потока и приведение его к нужному типу
            Object deserializedObject = objectStream.readObject();
            return (Command) deserializedObject;
        }
        catch (Exception e){
            throw new RecieveDataException("Ошибка десереализации команды");
        }


    }
}