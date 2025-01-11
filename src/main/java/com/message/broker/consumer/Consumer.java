package com.message.broker.consumer;


import com.message.broker.messge.Message;

public interface Consumer {
    void onMessage(Message message);
}