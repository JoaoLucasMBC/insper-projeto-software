package com.insper.partida.equipe.exceptions;

public class TeamDoesNotExistException extends RuntimeException{

    public TeamDoesNotExistException() {
        super("Time não existe");
    }

}
