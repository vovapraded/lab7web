package org.example.utility;

import org.example.entity.TicketType;
import org.example.entity.VenueType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Validator {
    /**
     * auxiliary class for validating arguments
     */
    public Validator() {}
    public static boolean validate(String arg, TypesOfArgs type, boolean isCanBeNull)   {
        if (isCanBeNull && arg==null){return true;}
        if (!isCanBeNull && arg==null){return false;}

        if (isCanBeNull && arg.isEmpty()){return true;}
        if (!isCanBeNull && arg.isEmpty()){return false;}
        if (type.getClas()==String.class ){return (!arg.contains(" ")) && (!arg.contains("\t")) && (!arg.contains("\n")) ;}
        if (type.getClas()==Boolean.class){
            if (arg.equals("true") || arg.equals("false")) return true;
            else return false;
        }
        Class<?>[] parameterTypes = {String.class};
        Method method = null;
        //если это мой класс enum
        if (type.getClas()== TicketType.class ||type.getClas()== Commands.class||type.getClas()== VenueType.class ){
            arg=arg.toLowerCase();
            for (Object enumValue : type.getClas().getEnumConstants()) {
               if (enumValue.toString().toLowerCase().equals(arg)){
                   return true;
               }
            }
            return false;
        }
        //если это класс из java.lang
        try {
            method = type.getClas().getMethod("parse"+type.getClas().getName().split("\\.")[2], parameterTypes);
            method.invoke(null, arg);
            return true;
        }catch (NumberFormatException  e){
            return false;
        }
        catch (InvocationTargetException e){
            if (e.getCause().getClass()==NumberFormatException.class)return false;
            else throw new RuntimeException(e);
        }
        catch (NoSuchMethodException | IllegalAccessException  e) {
            throw new RuntimeException(e);
        }

    }


}
