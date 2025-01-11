# In-Memory Message Broker

In-memory message broker library

- DONE - The library should have the notion of a Topic, Publisher, Consumer. The publisher publishes to a topic, multiple consumers can consume from a topic.
- DONE - One topic can have multiple consumers and publishers.
- DONE - The library should be able to manage multiple topics.
- DONE - Create topics
- DONE - Delete topics
- DONE - Consumers should be able to consume when a message is received for the topic.
- DONE - Ability to publish messages in parallel.
- DONE - Graceful handling of exceptions.
- DONE - Offset Management for Consumers.
- DONE - Topics should have a max retention period beyond which the messages should be deleted. This is a topic level property.
- DONE - [Bonus-1] Ability to reset offset and replay messages from a particular offset.
- [Bonus-2] Ability to provide visibility at a consumer and topic based on last offset read or lag.

## How It Works

- **Topics**: A `Topic` holds messages and manages their lifecycle based on a retention period.
- **Retention Cleaner**: Periodically removes expired messages.
- **Consumers**: Implement the Observer pattern to receive notifications when new messages are published.
- **Broker**: Manages topics and provides APIs for publishing and consuming messages.

## Usage

When the application starts, it presents a menu for interaction
Run: **MessageBroker**

1. **Create Topic**:
    - Enter a topic name and a retention period in milliseconds.

2. **Delete Topic**:
    - Specify the name of the topic to delete.

3. **Publish Message**:
    - Provide the topic name and the message content to publish.

4. **Consume Messages**:
    - Enter the topic name and an offset.

5. **Display Topics**:
    - Lists all available topics.

6. **Add Consumer**:
    - Specify a topic name to add a consumer that listens for new messages.

7. **Reply Topic from 0 offset**
    - Specify a topic name
8. **Exit**:
    - Terminates the application.


## Design Patterns Used

1. **Singleton Pattern**:
    - Ensures a single instance of the `Broker` class.

2. **Observer Pattern**:
    - Consumers are notified of new messages in topics they subscribe to.

3. **Factory Pattern**:
    - Creates topics using the `TopicFactory` class.

## Key Classes

1. **Message**:
    - Represents a message with an offset and content.

2. **Topic**:
    - Manages messages, consumers, and retention policies.

3. **Broker**:
    - Centralized interface for managing topics and operations.

4. **Consumer**:
    - Interface for receiving messages.

5. **TopicFactory**:
    - Simplifies the creation of `Topic` instances.

## Logging

- Created Logger class, to log the event/message/error.
