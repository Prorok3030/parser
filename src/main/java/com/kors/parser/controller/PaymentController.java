package com.kors.parser.controller;

import com.kors.parser.model.UserEntity;
import com.kors.parser.service.PaymentService;
import com.kors.parser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PaymentController {
    private PaymentService paymentService;
    private UserService userService;

    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/getPaymentWidget")
    public String getPaymentWidget(@RequestParam("value") double value, Model model) {
        String confirmationToken = paymentService.getConfirmationToken(value);
        if (confirmationToken != null) {
            model.addAttribute("confirmationToken", confirmationToken);
            return "paymentWidget";
        } else {
            throw new NullPointerException("ConfirmationToken is null!");
        }
    }

    @GetMapping("/paymentConfirm")
    public String paymentConfirm(Principal principal) {
        UserEntity user = userService.findByLogin(principal.getName());
        user.setSubscribed(true);
        userService.save(user);
        return "paymentConfirmation";
    }
}
