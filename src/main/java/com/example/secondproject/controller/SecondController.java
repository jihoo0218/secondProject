package com.example.secondproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {

    @GetMapping("/")
    public String niceToMeetYou(Model model){
        String username = "JiHoo";
        model.addAttribute("username",username);
        return "greetings";
    }
    @GetMapping("/bye")
    public String goodBye(){
        return "goodBye";
    }

}
