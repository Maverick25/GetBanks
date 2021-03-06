/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getbanks.messaging;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;

/**
 *
 * @author marekrigan
 */
public class Send 
{
    private static final String TASK_QUEUE_NAME = "queue_recipList";
    
    public static void sendMessage(String message, AMQP.BasicProperties props) throws IOException 
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
	factory.setUsername("student");
	factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        
        channel.basicPublish( "", TASK_QUEUE_NAME, 
                props,
                message.getBytes());
        
        channel.close();
        connection.close();
    }
}
