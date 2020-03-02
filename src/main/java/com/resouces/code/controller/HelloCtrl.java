package com.resouces.code.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/",method = RequestMethod.GET)
public class HelloCtrl {

    @RequestMapping(value = "hello")
    public String hello(@RequestParam String name) {

        return "hello,"+name;
    }

}
