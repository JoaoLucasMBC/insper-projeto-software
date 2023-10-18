package com.insper.user.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.insper.user.user.dto.ReturnUserDTO;
import com.insper.user.user.dto.SaveUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<ReturnUserDTO> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/teste")
    public String oi() {
        return "Oi!";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ReturnUserDTO saveUser(Principal principal, @RequestBody SaveUserDTO saveUser) {
        System.out.println("Principal: " + principal.getName());
        return userService.saveUser(saveUser);
    }

    // @DeleteMapping  (/userId) -> Desasbilita o usuário - Apenas ADMIN
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
    // @GetMapping (/userId) -> Todos
    // @PutMapping (/userId) -> muda o password -> User só pode mudar o próprio passowrd


}
