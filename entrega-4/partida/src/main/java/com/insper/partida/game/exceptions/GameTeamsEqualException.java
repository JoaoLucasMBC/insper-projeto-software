package com.insper.partida.game.exceptions;

public class GameTeamsEqualException extends RuntimeException{
    
    public GameTeamsEqualException() {
        super("O time mandante não pode ser igual ao time visitante");
    }

}
