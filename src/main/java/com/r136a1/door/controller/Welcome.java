package com.r136a1.door.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    private String welcome="欢迎登陆";

    @RequestMapping("/welcome")
    public String welcome(){
        return welcome;
    }
    @RequestMapping("/edit")
    public String edit(@RequestParam("word")String word){
        this.welcome=word;
        return "ok";
    }

}
