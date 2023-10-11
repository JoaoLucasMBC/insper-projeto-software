package com.insper.partida.equipe.exceptions;

public class TeamAlreadyExistsException extends RuntimeException {

    public TeamAlreadyExistsException() {
        super("Time já existe");
    }

}
