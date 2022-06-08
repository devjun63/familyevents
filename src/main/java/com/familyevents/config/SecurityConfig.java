package com.familyevents.config;

import com.familyevents.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //EnableWebSecurity -> 직접 스프링 시큐리티 설정을 하겠다는 어노테이션
    // websecurity 설정을 좀 손쉽게 하려면 WebSecurityConfigurerAdapter를 상속
    private final AccountService accountService;
    private final DataSource dataSource;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 원하는 특정 요청들을 authorize 체크를 하지 않도록 걸러낼 수 있음
        http.authorizeRequests()
                .mvcMatchers("/", "/login", "/sign-up", "/check-email-token",
                        "/email-login", "/login-by-email").permitAll()       // mvcMatchers로 해당 링크는 모두 허용한다.
                .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()      // profile 링크는 GET만 허용한다.
                .anyRequest().authenticated();                                          // 그 외 링크는 모두 인증해야 쓸 수 있다.
        http.formLogin()
                .loginPage("/login").permitAll();   // 로그인 페이지 URL에 인증과 무관하게 permitAll

        http.logout()
                .logoutSuccessUrl("/");



        http.rememberMe()
                .userDetailsService(accountService)
                .tokenRepository(tokenRepository());
        //rememberMe() session저장
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    //                .mvcMatchers("/node_modules/**")
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/favicon.ico", "/resources/**", "/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }

}
