package ru.shop.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtRequest {

    private String login;
    private String password;

}