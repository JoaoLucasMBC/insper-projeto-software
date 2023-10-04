package com.insper.partida.game.exceptions;

public class GameDoesNotExistException extends RuntimeException{

    public GameDoesNotExistException() {
        super("O jogo não existe");
    }

}
