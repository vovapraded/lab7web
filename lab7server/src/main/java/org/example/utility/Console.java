package org.example.utility;

import java.util.Scanner;

/**
 * a class for reading and writing from the console
 */
public interface     Console {


    public abstract void selectFileScanner(Scanner scanner);


    public abstract Scanner getScanner();

    void printHello();

    public abstract String getInput();

    public abstract void  goToMenu();

    public abstract String getInputFromCommand(int minCountOfArgs, int maxCountOfArgs) ;

    default void print(String s) {
        System.out.println(s);
    }

    public abstract void selectConsoleScanner();
}