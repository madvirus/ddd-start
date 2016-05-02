package com.myshop.springsecurity;

import com.myshop.SpringIntTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@WebAppConfiguration
public class WebAccessTest {
    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(filterChainProxy).build();
    }

    @Test
    public void anyUser_Can_Access_Home() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    public void noAuthUser_should_redirect_login_when_personal_page() throws Exception {
        mockMvc.perform(get("/my/orders"))
                .andDo(print())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void authUser_can_access_personal_page() throws Exception {
        mockMvc.perform(get("/my/main").cookie(AuthCookieHelper.authCookie("user1")))
                .andExpect(status().isOk());
    }

    @Test
    public void notAdminUser_cant_access_admin_page() throws Exception {
        mockMvc.perform(get("/admin").cookie(AuthCookieHelper.authCookie("user1")))
                .andExpect(status().isForbidden());
    }
//
//    @Test
//    public void userWithSystemAdminRole_Access_Admin_shouldAccess() throws Exception {
//        mockMvc.perform(get("/admin").cookie(AuthCookieHelper.authCookie("systemadmin")))
//                .andExpect(status().isOk());
//    }

}
