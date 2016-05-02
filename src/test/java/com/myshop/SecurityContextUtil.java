package com.myshop;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class SecurityContextUtil {
    public static void setAuthentication(String memberId, String ... authorities) {
        User principal = new User(memberId, "",
                Arrays.stream(authorities).map(auth -> new SimpleGrantedAuthority(auth)).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities()));
    }
}
