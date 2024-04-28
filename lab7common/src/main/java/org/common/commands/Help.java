package org.common.commands;

import java.io.*;
import java.util.Scanner;

/**
 * Command for help info
 */
public class Help extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Help".hashCode();


    @Override
    public void execute() {
        String filePath = "/help.txt";
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    responseManager.addToSend(line,this);
                }
            } catch (Exception e) {
                responseManager.addToSend("Ошибка при чтении файла: " + e.getMessage(),this);
            }
        } else {
            responseManager.addToSend("Файл help.txt не найден",this);
        }
        responseManager.send(this);
    }

    @Override
    public void validate(String arg1) {

    }
}
