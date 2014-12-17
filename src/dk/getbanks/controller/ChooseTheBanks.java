/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getbanks.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import dk.getbanks.dto.ComplexMessageDTO;
import dk.getbanks.dto.LoanRequestDTO;
import dk.getbanks.messaging.Receive;
import dk.getbanks.messaging.Send;
import dk.rulebaseservice.service.RuleBaseService_Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        List<String> selectedBanks;
        
        while (true) 
        {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          
          loanRequestDTO = gson.fromJson(message, LoanRequestDTO.class);
          
          RuleBaseService_Service service = new RuleBaseService_Service();
          selectedBanks = service.getRuleBaseServicePort().getAppropriateBanks(loanRequestDTO.getCreditScore());
          
          System.out.println(loanRequestDTO.toString());
          
          sendMessage(loanRequestDTO, selectedBanks);

          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
    }
    
    public static void sendMessage(LoanRequestDTO dto, List<String> selectedBanks) throws IOException
    {
        ComplexMessageDTO messageDTO = new ComplexMessageDTO(dto, selectedBanks);
        
        String message = gson.toJson(messageDTO);
        
        System.out.println(message);
        
        Send.sendMessage(message);
    }
}
