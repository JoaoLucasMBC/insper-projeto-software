package com.insper.user.user;

import com.insper.user.user.dto.LoginDTO;
import com.insper.user.user.dto.ReturnUserDTO;
import com.insper.user.user.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.RandomStringUtils;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        ReturnUserDTO user = userService.validateUser(loginDTO.getEmail(), loginDTO.getPassword());
        String token = RandomStringUtils.random(20, true, true);

        loginService.put(token, user);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setEmail(user.getEmail());
        tokenDTO.setToken(token);
        tokenDTO.setRoles(user.getRoles());
        return tokenDTO;
    }

    @GetMapping("/token/{token}")
    public ReturnUserDTO validaToken(@PathVariable String token) {
        ReturnUserDTO user = loginService.get(token);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
