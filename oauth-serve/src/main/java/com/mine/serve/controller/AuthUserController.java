package com.mine.serve.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthUserController {

    @GetMapping("/oauth/user")
    public Principal user(Principal principal) {
        return principal;
    }

}