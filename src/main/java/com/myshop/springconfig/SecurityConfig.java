package com.myshop.springconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    public static final String AUTHCOOKIENAME = "AUTH";

    @Autowired
    private DataSource dataSource;

    // 글로벌 설정
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select member_id, password, 'true' from member where member_id = ?")
                .authoritiesByUsernameQuery("select member_id, authority from member_authorities where member_id = ?")
                .passwordEncoder(new PlaintextPasswordEncoder())
        ;
    }

    @Configuration
    public static class WebWebSecurity extends WebSecurityConfigurerAdapter {
        @Autowired
        private SecurityContextRepository securityContextRepository;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/vendor/**");
            web.ignoring().antMatchers("/api/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.securityContext().securityContextRepository(securityContextRepository);
            http.requestCache().requestCache(new NullRequestCache());

            http
                    .authorizeRequests()
                    .antMatchers("/", "/home", "/categories/**", "/products/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin() // login
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(new CustomAuthSuccessHandler())
                    .and()
                    .logout() // /login?logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/loggedOut")
                    .deleteCookies(SecurityConfig.AUTHCOOKIENAME)
                    .permitAll()
                    .and()
                    .csrf().disable()
            ;
        }

        @Bean(name = "userDetailsService")
        @Override
        public UserDetailsService userDetailsServiceBean() throws Exception {
            return super.userDetailsServiceBean();
        }
    }


}
