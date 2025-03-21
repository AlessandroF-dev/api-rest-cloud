package br.com.cloud.rest.api.domain.dto;

import br.com.cloud.rest.api.domain.model.Account;
import br.com.cloud.rest.api.domain.model.Card;
import br.com.cloud.rest.api.domain.model.Feature;
import br.com.cloud.rest.api.domain.model.News;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;

    private String name;

    private Account account;

    private Card card;

    private List<Feature> features;

    private List<News> news;
}
