package com.kors.parser.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private String image;
    private String date;
    private String sourceLink;
    private String sourceName;

    @ManyToMany(mappedBy = "favorites", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

    public Publication() {
    }

    public Publication(String title, String text, String image, String date, String sourceLink, String sourceName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.date = date;
        this.sourceLink = sourceLink;
        this.sourceName = sourceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
