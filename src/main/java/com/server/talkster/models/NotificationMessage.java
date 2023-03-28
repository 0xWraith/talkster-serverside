package com.server.talkster.models;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class NotificationMessage {


   private String subject;
   private String content;
   private Map<String, String> data;
   private String image;

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Map<String, String> getData() {
        return data;
    }
}


