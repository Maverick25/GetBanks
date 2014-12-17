/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getbanks.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import dk.getbanks.dto.LoanRequestDTO;
import dk.getbanks.messaging.Receive;
import dk.getbanks.messaging.Send;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author marekrigan
 */
public class ChooseTheBanks 
{
    private static Gson gson;
    
    public static void receiveMessages() throws IOException,InterruptedException
    {
        gson = new Gson();
        
        HashMap<String,Object> objects = Receive.setUpReceiver();
        
        QueueingConsumer consumer = (QueueingConsumer) objects.get("consumer");
        Channel channel = (Channel) objects.get("channel");
        
        LoanRequestDTO loanRequestDTO;
        
        while (true) 
        {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          
          loanRequestDTO = gson.fromJson(message, LoanRequestDTO.class);
          
//          CreditScoreService_Service service = new CreditScoreService_Service();
//          int creditScore = service.getCreditScoreServicePort().creditScore(loanRequestDTO.getSsn());
//          
//          loanRequestDTO.setCreditScore(creditScore);
//          
          System.out.println(loanRequestDTO.toString());
//          
//          sendMessage(loanRequestDTO);

          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
    }
    
    public static void sendMessage(LoanRequestDTO dto) throws IOException
    {
        String message = gson.toJson(dto);
        
        Send.sendMessage(message);
    }
}
