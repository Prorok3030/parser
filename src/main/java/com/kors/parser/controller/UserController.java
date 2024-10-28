package com.kors.parser.controller;

import com.kors.parser.model.Publication;
import com.kors.parser.model.Role;
import com.kors.parser.model.UserEntity;
import com.kors.parser.repository.RoleRepository;
import com.kors.parser.service.PublicationService;
import com.kors.parser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private PublicationService publicationService;
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PublicationService publicationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.publicationService = publicationService;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/allUsers")
    public String allUsers(Model model){
        List<UserEntity> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "allUsers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/userDelete/{id}")
    public String userDelete(@PathVariable("id") long id, Principal principal){
        UserEntity user = userService.findByLogin(principal.getName());
        UserEntity deletableUser = userService.findById(id);
        if (user.getId() == deletableUser.getId()){
            userService.delete(id);
            return "redirect:/logout";
        }
        else {
            userService.delete(id);
            return "redirect:/admin/allUsers";
        }
    }


    @GetMapping("/profileEdit/{id}")
    public String profileEdit(@PathVariable Long id, Model model){
        UserEntity user = userService.findById(id);
        model.addAttribute("user", user);
        return "profileEdit";
    }
    @PostMapping("/profileEdit/{id}")
    public String profilePostEdit(@ModelAttribute("user") UserEntity user, Model model){
        UserEntity DbUser = userService.findByLogin(user.getLogin());
        if (DbUser != null) {
            model.addAttribute("message", "Пользователь уже существует");
            return "profileEdit";
        }
        user.setRole(roleRepository.findByName("USER").get());
        userService.save(user);
        return "redirect:/logout";
    }

    @GetMapping("favorites/{id}")
    public String favorites(@PathVariable Long id, Model model) {
        UserEntity user = userService.findById(id);
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        List<Publication> publications = publicationService.findByUsers(users);
        model.addAttribute("publications", publications);
        return "favorites";
    }

    @GetMapping("/publicationFavoriteSave/")
    public String favoriteAdd(@RequestParam(required = false) String title, @RequestParam(required = false) String text,
                                  @RequestParam(required = false) String image, @RequestParam(required = false) String date,
                                  @RequestParam(required = false) String sourceLink, @RequestParam(required = false) String sourceName,
                              Principal principal) {
        Publication publication = new Publication(title,text,image,date,sourceLink,sourceName);
        UserEntity user = userService.findByLogin(principal.getName());
        List<Publication> publications = new ArrayList<>();
        publications.addAll(user.getFavorites());
        publications.add(publication);
        user.setFavorites(publications);
        userService.save(user);
        return "redirect:/publications";
    }

    @GetMapping("/profile")
    public String Profile (Principal principal, Model model){
        UserEntity user = userService.findByLogin(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }
}
