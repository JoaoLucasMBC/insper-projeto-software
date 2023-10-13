package com.insper.partida.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReturnUserDTO {
    private String email;
    private String password;
    private List<String> roles;

}
