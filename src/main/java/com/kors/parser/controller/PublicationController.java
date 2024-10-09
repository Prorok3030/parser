package com.kors.parser.controller;

import com.kors.parser.model.Publication;
import com.kors.parser.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PublicationController {

    private PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/")
    public String parseAll(Model model) {
        List<Publication> publications = publicationService.findAll();
        model.addAttribute("publications", publications);
        return "publications";
    }

    @GetMapping("/publicationAdd")
    public String publicationAdd(Publication publication) {
        return "publicationAdd";
    }

    @PostMapping("/publicationAdd")
    public String publicationAddPost(Publication publication) {
        publicationService.save(publication);
        return "redirect:/";
    }
}
