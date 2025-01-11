package com.message.broker.exception;


import com.message.broker.utils.Logger;

public class CustomException extends Exception{

    public CustomException(String message) {
        super(message);
        Logger.debug("Customer exception:");
    }
}
