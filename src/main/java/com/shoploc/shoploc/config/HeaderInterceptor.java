package com.shoploc.shoploc.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            // Token présent, autorisez la requête à continuer vers les méthodes du contrôleur
            return true;
        } else {
            // Token absent, rejetez la requête avec une réponse HTTP 401 Unauthorized
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Token is missing");
            return false;
        }
    }
}
