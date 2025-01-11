# In-Memory Message Broker

In-memory message broker library

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
