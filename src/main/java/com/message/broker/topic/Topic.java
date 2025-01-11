package com.message.broker.topic;

import com.message.broker.consumer.Consumer;
import com.message.broker.exception.GetMessageException;
import com.message.broker.exception.MessagePublishException;
import com.message.broker.messge.Message;
import com.message.broker.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Topic {
    private final String name;
    private final int retentionPeriodMillis;
    private final Queue<Message> messages = new ConcurrentLinkedQueue<>();
    private final AtomicInteger offsetCounter = new AtomicInteger(0);
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
    private final List<Consumer> consumers = new ArrayList<>(); // For Observer pattern

    public Topic(String name, int retentionPeriodMillis) {
        this.name = name;
        this.retentionPeriodMillis = retentionPeriodMillis;
        startRetentionCleaner();
    }

    public String getName() {
        return name;
    }

    public synchronized void publish(String content) {
        try {
            Message message = new Message(offsetCounter.getAndIncrement(), content);
            messages.offer(message);
            Logger.info("Message published to topic: " + name);
            notifyConsumers(message);
        } catch (MessagePublishException e) {
            Logger.log("Failed to publish message to topic: " + name, e);
        }
    }

    public synchronized List<Message> getMessagesFromOffset(int offset) {
        List<Message> result = new ArrayList<>();
        try {
            for (Message message : messages) {
                if (message.getOffset() >= offset) {
                    result.add(message);
                }
            }
            if(result.isEmpty()){
                throw new GetMessageException("No Message found with given offset !!");
            }
        } catch (GetMessageException e) {
            Logger.log("Failed to consume messages from topic: " + name, e);
        }
        return result;
    }

    private void startRetentionCleaner() {
        cleaner.scheduleAtFixedRate(() -> {
            try {
                long expiryTime = System.currentTimeMillis() - retentionPeriodMillis;
                messages.removeIf(message -> message.getOffset() < expiryTime);
                Logger.info("Retention cleaner ran for topic: " + name);
            } catch (Exception e) {
                Logger.log( "Error in retention cleaner for topic: " + name, e);
            }
        }, retentionPeriodMillis, retentionPeriodMillis, TimeUnit.MILLISECONDS);
    }

    public void shutdownCleaner() {
        try {
            cleaner.shutdown();
            Logger.info("Retention cleaner shut down for topic: " + name);
        } catch (Exception e) {
            Logger.log( "Error shutting down cleaner for topic: " + name, e);
        }
    }

    public synchronized void addConsumer(Consumer consumer) {
        consumers.add(consumer);
    }

    private synchronized void notifyConsumers(Message message) throws MessagePublishException {
        if(consumers.isEmpty()) {
            throw new MessagePublishException("No consumer available.");
        }
        for (Consumer consumer : consumers) {
            consumer.onMessage(message);
        }
    }
}
