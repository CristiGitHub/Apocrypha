package com.Apocrypha.Apocrypha.utils;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class Helper {

//    public static User getUser(OAuth2Authentication auth) {
//        Authentication userAuth = auth.getUserAuthentication();
//        return userAuth == null ? null : (User) userAuth.getPrincipal();
//    }

    public static String getKeycloakUser(Authentication authentication) {
        return ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getPreferredUsername();
    }
}
