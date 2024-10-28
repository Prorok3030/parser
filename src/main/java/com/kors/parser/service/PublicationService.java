package com.kors.parser.service;

import com.kors.parser.model.Publication;
import com.kors.parser.model.UserEntity;
import com.kors.parser.repository.PublicationRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public String formatDate(Date date) {
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM, HH:mm", Locale.ENGLISH);
        return outputFormatter.format(date);
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

    public List<Publication> parserDTF() {
        try {
            var document = Jsoup.connect("https://dtf.ru/games").get();
            Elements elements = document.select("div.content-title");
            Elements elements1 = document.select("a.noshrink");
            Elements elements2 = document.select("time");
            List<Publication> publicationList = new ArrayList<>();
            for (int i=0 ; i<elements.size(); i++){
                Publication publication = new Publication();
                publication.setTitle(elements.get(i).text());
                publication.setSourceLink("https://dtf.ru" + elements1.get(i).attr("href"));
                String dateString = elements2.get(i).attr("datetime");
                // Преобразование ISO даты в LocalDateTime
                LocalDateTime dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
                // Определение дней между датами
                long daysBetween = ChronoUnit.DAYS.between(dateTime.toLocalDate(), LocalDate.now());
                // Форматирование даты
                String formattedDate;
                if (daysBetween == 0) {
                    formattedDate = "Сегодня, " + dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                } else if (daysBetween == 1) {
                    formattedDate = "Вчера, " + dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                } else {
                    formattedDate = dateTime.format(DateTimeFormatter.ofPattern("d MMMM"));
                }
                publication.setDate(formattedDate);
                publication.setSourceName("DTF");
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
