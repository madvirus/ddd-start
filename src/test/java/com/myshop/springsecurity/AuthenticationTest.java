package com.myshop.springsecurity;

import com.myshop.SpringIntTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class AuthenticationTest {
    @Autowired
    private ProviderManager authenticationManager;

    @Test
    public void authentication_success() throws Exception {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("user1", "1234"));
        assertThat(auth.isAuthenticated(), equalTo(true));
    }

    @Test(expected = BadCredentialsException.class)
    public void authentication_fail() throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("user1", "badpw"));
    }
}
