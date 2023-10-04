package com.insper.partida.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insper.partida.common.ExceptionDTO;
import com.insper.partida.game.exceptions.GameDoesNotExistException;
import com.insper.partida.game.exceptions.GameTeamsEqualException;

import java.time.LocalDateTime;


@ControllerAdvice
public class GameControllerAdvice {
 
    @ExceptionHandler(GameDoesNotExistException.class) // inversão de controle!
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO teamAlreadyExists(GameDoesNotExistException ex) {
        ExceptionDTO error = new ExceptionDTO();
        error.setMessage(ex.getMessage());
        error.setCode(404);
        error.setTime(LocalDateTime.now());

        return error;
    }

    @ExceptionHandler(GameTeamsEqualException.class) // inversão de controle!
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO teamDoesNotExist(GameTeamsEqualException ex) {
        ExceptionDTO error = new ExceptionDTO();
        error.setMessage(ex.getMessage());
        error.setCode(400);
        error.setTime(LocalDateTime.now());

        return error;
    }

}