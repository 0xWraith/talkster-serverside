package com.server.talkster.models;

import lombok.Data;
import java.util.Map;

@Data
public class MessageNotification
{

   private String subject;
   private String content;
   private Map<String, String> data;

    public MessageNotification() { }

    public MessageNotification(String subject, String content, Map<String, String> data)
    {
        this.subject = subject;
        this.content = content;
        this.data = data;
    }

    public String getSubject() { return subject; }
    public String getContent() { return content; }

    public Map<String, String> getData() { return data; }

}


