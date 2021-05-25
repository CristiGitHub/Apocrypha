package com.Apocrypha.Apocrypha.utils;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;

public class Helper {

//    public static User getUser(OAuth2Authentication auth) {
//        Authentication userAuth = auth.getUserAuthentication();
//        return userAuth == null ? null : (User) userAuth.getPrincipal();
//    }

    public static String getKeycloakUser(Authentication authentication) {
        return ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getPreferredUsername();
    }
}
