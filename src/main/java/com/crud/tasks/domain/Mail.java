package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCc;
    public Mail(String mailTo,String subject,String message){   //tylko dla dodatkowych testów
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }
}

