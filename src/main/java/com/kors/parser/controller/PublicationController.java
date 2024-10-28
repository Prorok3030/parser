package com.kors.parser.controller;

import com.kors.parser.model.Publication;
import com.kors.parser.model.UserEntity;
import com.kors.parser.service.PublicationService;
import com.kors.parser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class PublicationController {

    private PublicationService publicationService;
    private UserService userService;

    @Autowired
    public PublicationController(PublicationService publicationService, UserService userService) {
        this.publicationService = publicationService;
        this.userService = userService;
    }

    @GetMapping("/publications")
    public String parseAll(Model model, Principal principal) {
        UserEntity user = userService.findByLogin(principal.getName());
        List<Publication> publicationsSG = publicationService.parserStopGame();
        model.addAttribute("publicationsSG", publicationsSG);
        model.addAttribute("user", user);
        return "publications";
    }

    @GetMapping("/publications/dtf")
    public String publicationsDTF(Model model, Principal principal) {
        UserEntity user = userService.findByLogin(principal.getName());
        if (user.getSubscribed()) {
            List<Publication> publications = publicationService.parserDTF();
            model.addAttribute("publicationsDTF", publications);
            model.addAttribute("user", user);
            return "publications_dtf";
        } else {
            return "redirect:/publications";
        }
    }

    @GetMapping("/publicationAdd")
    public String publicationAdd(Publication publication) {
        return "publicationAdd";
    }

    @PostMapping("/publicationAdd")
    public String publicationAddPost(Publication publication) {
        publicationService.save(publication);
        return "redirect:/publications";
    }

    @GetMapping("/publicationSave/")
    public String publicationSave(@RequestParam(required = false) String title, @RequestParam(required = false) String text,
                                  @RequestParam(required = false) String image, @RequestParam(required = false) String date,
                                  @RequestParam(required = false) String sourceLink, @RequestParam(required = false) String sourceName){
        Publication publication = new Publication(title,text,image,date,sourceLink,sourceName);
        publicationService.save(publication);
        return "redirect:/publications";
    }

}
