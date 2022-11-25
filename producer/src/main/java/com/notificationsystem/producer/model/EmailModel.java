package com.notificationsystem.producer.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailModel implements Serializable {
    private String priority;
    private String to;
    private String subject;
    private String body;
}
