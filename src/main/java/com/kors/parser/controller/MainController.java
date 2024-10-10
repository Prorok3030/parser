package com.kors.parser.controller;

import com.kors.parser.model.Publication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @GetMapping("/home")
    public String parseAll() {
        return "home";
    }
}
