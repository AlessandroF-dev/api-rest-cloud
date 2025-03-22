package br.com.cloud.rest.api.domain.model;

import jakarta.persistence.Entity;

@Entity(name = "tb_news")
public class News extends BaseItem {

    public News() {

    }

    public News(String urlIcon, String description) {
        setIcon(urlIcon);
        setDescription(description);
    }

}
