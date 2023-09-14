package com.insper.partida.game;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "game")
public class Game {

    @Id
    private String id;

    private String identifier;

    private Integer scoreHome;

    private Integer scoreAway;

    private String home;

    private String away;

    private String stadium;

    private Integer attendance;

    private LocalDateTime gameDate;

    private String status;

}
