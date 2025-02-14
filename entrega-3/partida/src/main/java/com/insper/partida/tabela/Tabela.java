package com.insper.partida.tabela;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("tabela")
public class Tabela {

    @Id
    private String id;
    private String identifier;
    private String nome;
    private Integer pontos;
    private Integer golsPro;
    private Integer golsContra;
    private Integer vitorias;
    private Integer derrotas;
    private Integer empates;
    private Integer jogos;

}

