package com.activemq.receiver;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
	public static void main(String[] args) {
		while(true) {
			try {
				ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
				Connection connection = factory.createConnection();

				connection.start();

				// Create a session which is non transactional
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				// Create Destination queue
				Destination queue = session.createQueue("MyQueue");

				MessageConsumer consumer = session.createConsumer(queue);

				Message message = consumer.receive(1000);
				
//				Message message = consumer.setMessageListener(new MessageListener());

				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("Consumer Received: " + text);
				}
				session.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
