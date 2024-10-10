package com.kors.parser.controller;

import com.kors.parser.model.Publication;
import com.kors.parser.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PublicationController {

    private PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/publications")
    public String parseAll(Model model) {
        List<Publication> publications = publicationService.findAll();
        List<Publication> publicationsSG = publicationService.parserStopGame();
        model.addAttribute("publications", publications);
        model.addAttribute("publicationsSG", publicationsSG);
        return "publications";
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
