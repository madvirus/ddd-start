package com.myshop.springconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class CookieSecurityContextRepository implements SecurityContextRepository {
    private UserDetailsService userDetailsService;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        SecurityContext sc = SecurityContextHolder.createEmptyContext();
        Cookie cookie = findAuthCookie(requestResponseHolder.getRequest());
        if (cookie != null) {
            String id = getUserId(cookie);
            if (id != null) {
                populateAuthentication(sc, id);
            }
        }
        return sc;
    }

    private void populateAuthentication(SecurityContext sc, String id) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(id);
            sc.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private String getUserId(Cookie cookie) {
        try {
            return URLDecoder.decode(cookie.getValue(), "utf-8");
        } catch (Exception ex) {
            return null;
        }
    }

    private Cookie findAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return null;
        for (Cookie c : cookies) {
            if (c.getName().equals(SecurityConfig.AUTHCOOKIENAME)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
