package org.choongang.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(f -> {
                f.loginPage("/member/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(new LoginSuccessHandler())
                        .failureHandler(new LoginFailureHandler());
    });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });


        http.authorizeHttpRequests( c-> {
           c.requestMatchers("/mypage/**").authenticated()
                   //.requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "MANAGER")
                   .anyRequest().permitAll();

        });

        http.exceptionHandling(c -> {
           c.authenticationEntryPoint((req, res, e) -> {
             String URL = req.getRequestURI();
             if(URL.indexOf("/admin")!= -1){
                 res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
             }else {
                 res.sendRedirect(req.getContextPath() + "/member/login");
             }
           });
        });
        return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
