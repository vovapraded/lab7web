package org.example.managers;
import org.common.utility.InvalidFormatException;
/**
 * a class for parsing strings into arguments
 */
public class ParseInput   {
    private String arg1="";
    private String arg2="";
    private String arg3="";
    private int arg2IsNotEmpty=0;
    private int arg3Exist=0;

    public int getArg3Exist() {
        return arg3Exist;
    }

    public void setArg3Exist(int arg3Exist) {
        this.arg3Exist = arg3Exist;
    }

    private String[] arg3m;
    public ParseInput(){}
    private void resetTheValues(){
        arg1="";
        arg2="";
        arg3="";
        arg2IsNotEmpty=0;
        arg3Exist = 0;
        }
    public  void parseInput(String s ) throws InvalidFormatException {
        resetTheValues();
        String[] parts = s.split(" ");
        try {
            arg1 = parts[0];
            arg2 = parts[1];
            arg2IsNotEmpty=1;
            arg3 = parts[2];
            arg3Exist = 1;
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public int getArg2IsNotEmpty(){
        return arg2IsNotEmpty;
    }


    public String getArg1() {
        return arg1;
    }
    public String getArg2() {
        return arg2;
    }
}
