package com.kors.parser.service;

import com.kors.parser.model.Publication;
import com.kors.parser.repository.PublicationRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    @Autowired
    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public Publication findById(Publication publication) {
        return publicationRepository.findById(publication.getId()).orElse(null);
    }

    public List<Publication> findAll() {
        return publicationRepository.findAll();
    }

    public Publication save(Publication publication) {
        return publicationRepository.save(publication);
    }

    public Publication parserStopGame() {
        try {
            Document document = Jsoup.connect("https://stopgame.ru/news").get();
            Elements elements = document.select("a._title_1vlem_60");
            Elements elements1 = document.select("div._info-row_1vlem_121");
            for (int i=0 ; i<elements.size(); i++){
                System.out.println(elements.get(i).text());
                System.out.println("https://stopgame.ru/" + elements.get(i).attr("href"));
                System.out.println(elements1.get(i).select("span").text());
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}