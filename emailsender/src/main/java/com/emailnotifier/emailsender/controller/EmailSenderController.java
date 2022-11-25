package com.emailnotifier.emailsender.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSenderController {
    
    @PostMapping("/sendemail")
    public void sendEmail(@RequestBody JSONObject emailData) {
        System.out.println(emailData);
    }
}
