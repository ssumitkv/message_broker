package com.message.broker.broker;

import com.message.broker.messge.Message;
import com.message.broker.topic.Topic;
import com.message.broker.topic.TopicFactory;
import com.message.broker.utils.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    private static Broker instance;
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();

    private Broker() {}

    public static synchronized Broker getInstance() {
        if (instance == null) {
            instance = new Broker();
        }
        return instance;
    }

    public synchronized void createTopic(String name, int retentionPeriodMillis) {
        try {
            if (!topics.containsKey(name)) {
                Topic topic = TopicFactory.createTopic(name, retentionPeriodMillis);
                topics.put(name, topic);
                Logger.info("Topic created: " + name);
            } else {
                Logger.warning("Topic already exists: " + name);
            }
        } catch (Exception e) {
            Logger.info( "Failed to create topic: " + name, e);
        }
    }

    public synchronized void deleteTopic(String name) {
        try {
            Topic topic = topics.remove(name);
            if (topic != null) {
                topic.shutdownCleaner();
                Logger.info("Topic deleted: " + name);
            } else {
                Logger.warning("Topic does not exist: " + name);
            }
        } catch (Exception e) {
            Logger.info("Failed to delete topic: " + name, e);
        }
    }

    public void publish(String topicName, String message) {
        try {
            Topic topic = topics.get(topicName);
            if (topic != null) {
                topic.publish(message);
            } else {
                Logger.warning("Topic does not exist: " + topicName);
            }
        } catch (Exception e) {
            Logger.log( "Failed to publish message to topic: " + topicName, e);
        }
    }

    public List<Message> consume(String topicName, int offset) {
        try {
            Topic topic = topics.get(topicName);
            if (topic != null) {
                return topic.getMessagesFromOffset(offset);
            } else {
                Logger.warning("Topic does not exist: " + topicName);
            }
        } catch (Exception e) {
            Logger.log("Failed to consume messages from topic: " + topicName, e);
        }
        return Collections.emptyList();
    }

    public void displayTopics() {
        try {
            Logger.info("Available Topics:");
            topics.keySet().forEach(topic -> Logger.info(topic));
        } catch (Exception e) {
            Logger.log( "Failed to display topics", e);
        }
    }

    public Topic getTopic(String topicName) {
        return topics.get(topicName);
    }
}
