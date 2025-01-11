package com.message.broker.topic;

public // Factory pattern for creating Topics
class TopicFactory {
    public static Topic createTopic(String name, int retentionPeriodMillis) {
        return new Topic(name, retentionPeriodMillis);
    }
}
