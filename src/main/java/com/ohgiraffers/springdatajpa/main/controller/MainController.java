package com.ohgiraffers.springdatajpa.main.controller;


import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {


    @GetMapping(value = {"/","/main"})
    public String main(){
        return "main/main";
    }



}
