package com.server.talkster.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dev")
public class DevController
{
    @RequestMapping("/test")
    public String test()
    {
        System.out.println("Test");
        return "Test";
    }
}
