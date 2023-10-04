package com.insper.partida.equipe;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("team")
public class Team {

    @Id
    private String id;

    private String identifier;

    private String name;

    private String stadium;

}
