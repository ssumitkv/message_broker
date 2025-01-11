package com.message.broker.utils;

import java.util.logging.Level;

public class Logger {

    public static void debug(String message) {
        System.out.println("DEBUG: "+message);
    }


    public static void info(String message) {
        System.out.println("INFO: "+message);
    }

    public static void warning(String message) {
        System.out.println("WARNING: "+message);
    }

    public static void info(String message, Exception e) {
        System.out.println("MESSAGE: "+message+" DETAILS: "+e.getMessage());
    }

    public static void log( String message, Exception e) {
        System.out.println("MESSAGE: "+message+" DETAILS: "+e.getMessage());

    }
}
