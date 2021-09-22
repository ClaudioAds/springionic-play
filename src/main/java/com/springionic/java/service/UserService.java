package com.springionic.java.service;

import com.springionic.java.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS authenticated() {
        try {

        return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //retorna o usuário que tiver logado no sistema
        } catch (Exception e) {
            return null;
        }
    }
}
