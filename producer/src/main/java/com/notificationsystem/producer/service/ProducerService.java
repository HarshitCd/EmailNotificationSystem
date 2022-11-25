package com.notificationsystem.producer.service;

import org.springframework.stereotype.Service;

import com.notificationsystem.producer.model.EmailModel;

@Service
public class ProducerService {
    
    public void sendEmail(EmailModel emailModel) {
        if(emailModel.getPriority().equals("high")){
            System.out.println("In High Priority");
        }
    }
}
