package br.com.cloud.rest.api.domain.model;

import jakarta.persistence.Entity;

@Entity(name = "tb_feature")
public class Feature extends BaseItem {

    public Feature() {

    }

    public Feature(String urlIcon, String description) {
        setIcon(urlIcon);
        setDescription(description);
    }
}
