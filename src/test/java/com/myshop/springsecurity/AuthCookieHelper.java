package com.myshop.springsecurity;

import com.myshop.springconfig.SecurityConfig;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public interface AuthCookieHelper {
    static Cookie authCookie(String id) {
        try {
            return new Cookie(SecurityConfig.AUTHCOOKIENAME, URLEncoder.encode(id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
