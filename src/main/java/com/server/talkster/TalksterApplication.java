package com.server.talkster;

import com.server.talkster.services.MailSenderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TalksterApplication
{

    public static void main(String[] args) { SpringApplication.run(TalksterApplication.class, args); }

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

}
