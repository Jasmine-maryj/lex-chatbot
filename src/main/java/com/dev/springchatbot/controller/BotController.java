package com.dev.springchatbot.controller;

import com.dev.springchatbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BotController {

    @Autowired
    BotService botService;

    @GetMapping("/")
    public String greeting(Model model){
        return "index";
    }

    @RequestMapping(value = "/text" , method = RequestMethod.POST)
    @ResponseBody
    String addItems(HttpServletRequest request, HttpServletResponse response){
        String text = request.getParameter("text");
        String message = botService.getText(text);
        return message;
    }
}
