package com.activemq.sender;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

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

				// Create a producer
				MessageProducer producer = session.createProducer(queue);

				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				Random random = new Random();
				String msg = "Hello World! "+ random.nextInt(10);
				Thread.sleep(2000);

				// insert message
				TextMessage message = session.createTextMessage(msg);
				System.out.println("Producer Sent: " + msg);
				producer.send(message);

				session.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
