package com.message.broker;

import com.message.broker.broker.Broker;
import com.message.broker.messge.Message;
import com.message.broker.topic.Topic;
import com.message.broker.utils.Logger;

import java.util.List;
import java.util.Scanner;

public class MessageBroker {

    public static void main(String[] args) {
        Broker broker = Broker.getInstance();
        Scanner scanner = new Scanner(System.in);

        Logger.info("Welcome to the In-Memory Message Broker!");

        while (true) {
            try {
                System.out.println("\nChoose an option:");
                System.out.println("1. Create Topic");
                System.out.println("2. Delete Topic");
                System.out.println("3. Publish Message");
                System.out.println("4. Consume Messages");
                System.out.println("5. Display Topics");
                System.out.println("6. Add Consumer");
                System.out.println("7. Reset and reply");
                System.out.println("8. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter topic name:");
                        String topicName = scanner.nextLine();
                        System.out.println("Enter retention period in milliseconds:");
                        int retention = scanner.nextInt();
                        broker.createTopic(topicName, retention);
                        break;

                    case 2:
                        System.out.println("Enter topic name to delete:");
                        String deleteTopic = scanner.nextLine();
                        broker.deleteTopic(deleteTopic);
                        break;

                    case 3:
                        System.out.println("Enter topic name:");
                        String publishTopic = scanner.nextLine();
                        System.out.println("Enter message to publish:");
                        String message = scanner.nextLine();
                        broker.publish(publishTopic, message);
                        break;

                    case 4:
                        System.out.println("Enter topic name:");
                        String consumeTopic = scanner.nextLine();
                        System.out.println("Enter offset:");
                        int offset = scanner.nextInt();
                        List<Message> messages = broker.consume(consumeTopic, offset);
                        for (Message msg : messages) {
                            System.out.println("[Offset: " + msg.getOffset() + "] " + msg.getContent());
                        }
                        break;

                    case 5:
                        broker.displayTopics();
                        break;

                    case 6:
                        System.out.println("Enter topic name:");
                        String consumerTopic = scanner.nextLine();
                        Topic topic = broker.getTopic(consumerTopic);
                        if (topic != null) {
                            topic.addConsumer(msg -> System.out.println("Consumer received: [Offset: " + msg.getOffset() + "] " + msg.getContent()));
                            Logger.info("Consumer added to topic: " + consumerTopic);
                        } else {
                            Logger.warning("Topic does not exist: " + consumerTopic);
                        }
                        break;

                    case 7:
                        System.out.println("Enter topic name:");
                        String replyTopic = scanner.nextLine();
                        List<Message> replymessages = broker.consume(replyTopic, 0);
                        for (Message msg : replymessages) {
                            System.out.println("[Offset: " + msg.getOffset() + "] " + msg.getContent());
                        }
                        break;


                    case 8:
                        Logger.info("Exiting...");
                        scanner.close();
                        return;




                    default:
                        Logger.warning("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                Logger.log( "Error occurred during operation", e);
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }
}
