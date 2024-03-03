package com.fastcampus.fastcampusprojectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "forward:/articles"; // 포워딩 방식으로 변경
    }
}
