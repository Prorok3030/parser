package com.kors.parser.service;

import com.kors.parser.model.Publication;
import com.kors.parser.model.UserEntity;
import com.kors.parser.repository.PublicationRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Publication> parserStopGame() {
        try {
            Document document = Jsoup.connect("https://stopgame.ru/news").get();
            Elements elements = document.select("a._title_1vlem_60");
            Elements elements1 = document.select("div._info-row_1vlem_121");
            List<Publication> publicationList = new ArrayList<>();
            for (int i=0 ; i<elements.size(); i++){
                Publication publication = new Publication();
                publication.setTitle(elements.get(i).text());
                publication.setSourceLink("https://stopgame.ru/" + elements.get(i).attr("href"));
                publication.setDate(elements1.get(i).select("span").text());
                publication.setSourceName("StopGame.ru");
                publicationList.add(publication);
            }
            return publicationList;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Publication> findByUsers(List<UserEntity> users) {
        return publicationRepository.findByUsers(users);
    }
}
