package com.example.web_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.example.web_project.config.handler.LoginAuthFailureHandler;
import com.example.web_project.config.handler.LoginAuthSuccessHandler;
import com.example.web_project.config.handler.LogoutAuthSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public BCryptPasswordEncoder eBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Autowired
    // private LoginAuthSuccessHandler loginAuthSuccessHandler;
    // @Autowired
    // private LoginAuthFailureHandler loginAuthFailureHandler;
    // @Autowired
    // private LogoutAuthSuccessHandler logoutAuthSuccessHandler;

    @Bean
    public AuthenticationSuccessHandler loginAuthSuccessHandler1() {
        return new LoginAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler loginAuthFailureHandler1() {
        return new LoginAuthFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutAuthSuccessHandler1() {
        return new LogoutAuthSuccessHandler();
    }

    @Bean
    public SecurityFilterChain finteFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf(AbstractHttpConfigurer::disable);

        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/**")
                    .authenticated()
                .requestMatchers("/manager/**")
                    .hasAnyAuthority("MANAGER", "ADMIN")
                .requestMatchers("/admin/**")
                    .hasAnyAuthority("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/v1/web/loginPage")
                .loginProcessingUrl("/v1/web/login")
                .successHandler(loginAuthSuccessHandler1())
                .failureHandler(loginAuthFailureHandler1())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutAuthSuccessHandler1())
                .permitAll()
            );
        
        return http.build();
    }
    //이미지 저장 경로 구현 중 
    // public void  addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry.addResourceHandler("/attach/images/**") // --1
    //             .addResourceLocations("src/main/resources/static/files/"); //--2
    //}
}
