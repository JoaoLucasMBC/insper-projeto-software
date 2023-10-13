package com.insper.partida.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Order(1)
public class LoginFilter implements Filter {


    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String token = req.getHeader("token");

        String uri = req.getRequestURI();
        String method = req.getMethod();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReturnUserDTO> responseEntity = restTemplate.
                getForEntity("http://localhost:8081/login/token/" + token, ReturnUserDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            chain.doFilter(request, response);
        } else {
            throw new RuntimeException("User not found");
        }

    }

}