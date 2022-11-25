package com.notificationsystem.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notificationsystem.producer.model.EmailModel;
import com.notificationsystem.producer.service.ProducerService;

@RestController
public class ProducerController {
    
    @Autowired
    ProducerService producerService;

    @PostMapping("/sendemail")
    public void sendEmail(@RequestBody EmailModel emailData) {
        producerService.sendEmail(emailData);
    }
}
