package com.kors.parser.controller;

import com.kors.parser.model.Publication;
import com.kors.parser.service.PaymentService;
import com.kors.parser.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/home")
    public String parseAll() {
        return "home";
    }
}
