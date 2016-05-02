package com.myshop.springsecurity;

import com.myshop.SecurityContextUtil;
import com.myshop.SpringIntTestConfig;
import com.myshop.member.application.BlockMemberService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class MethodAccessTest {
    @Autowired
    private BlockMemberService blockMemberService;

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void no_admin_cant_block_member() throws Exception {
        SecurityContextUtil.setAuthentication("user2", "ROLE_USER");
        try {
            blockMemberService.block("user1");
            fail();
        } catch (AccessDeniedException e) {
        }
    }

    @Test
    public void admin_can_block_member() throws Exception {
        SecurityContextUtil.setAuthentication("admin", "ROLE_ADMIN");
        try {
            blockMemberService.block("user1");
        } catch (AccessDeniedException e) {
            throw e;
        }
    }
}
