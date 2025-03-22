package br.com.cloud.rest.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "tb_card")
public class Card {

    public Card() {

    }

    public Card(String number, BigDecimal limit) {
        this.number = number;
        this.limit = limit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String number;

    @Column(name = "available_limit", precision = 13, scale = 2)
    private BigDecimal limit;
}
