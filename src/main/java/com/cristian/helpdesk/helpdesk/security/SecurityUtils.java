package com.cristian.helpdesk.helpdesk.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static boolean isClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_CLIENTE"));
    }

    public static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMINISTRADOR"));
    }

    public static boolean isTecnico() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_TECNICO"));
    }

    public static String getEmailAuth() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
