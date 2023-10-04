package com.insper.partida.equipe;

import com.insper.partida.common.ExceptionDTO;
import com.insper.partida.equipe.exceptions.TeamAlreadyExistsException;
import com.insper.partida.equipe.exceptions.TeamDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class TeamControllerAdvice {

    @ExceptionHandler(TeamAlreadyExistsException.class) // inversão de controle!
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO teamAlreadyExists(TeamAlreadyExistsException ex) {
        ExceptionDTO error = new ExceptionDTO();
        error.setMessage(ex.getMessage());
        error.setCode(400);
        error.setTime(LocalDateTime.now());

        return error;
    }

    @ExceptionHandler(TeamDoesNotExistException.class) // inversão de controle!
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO teamDoesNotExist(TeamDoesNotExistException ex) {
        ExceptionDTO error = new ExceptionDTO();
        error.setMessage(ex.getMessage());
        error.setCode(404);
        error.setTime(LocalDateTime.now());

        return error;
    }

}
