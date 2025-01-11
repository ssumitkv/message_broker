package com.message.broker.messge;


public class Message {
    private final int offset;
    private final String content;

    public Message(int offset, String content) {
        this.offset = offset;
        this.content = content;
    }

    public int getOffset() {
        return offset;
    }

    public String getContent() {
        return content;
    }
}
