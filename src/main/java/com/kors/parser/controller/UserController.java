package com.kors.parser.controller;

import com.kors.parser.model.Role;
import com.kors.parser.model.UserEntity;
import com.kors.parser.repository.RoleRepository;
import com.kors.parser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/registration")
    public String registration(UserEntity userEntity) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(UserEntity userEntity, Map<String, Object> model){
        if (userService.existsByLogin(userEntity.getLogin())){
            model.put("message","Такой пользователь уже существует");
            return "registration";
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        Role role = roleRepository.findByName("USER").get();
        userEntity.setRole(role);
        userService.save(userEntity);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String Profile (Principal principal, Model model){
        UserEntity user = userService.findByLogin(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }
}
