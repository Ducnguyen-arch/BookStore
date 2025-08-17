package com.duc.bookstore.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
//bảo vệ logic nghiệp vụ service/controller : @PreAuthorize và @PostAuthorize
@EnableMethodSecurity(prePostEnabled = true) // check quyền trước/sau khi run method
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenSuccessHandler customAuthenSuccessHandler(){
        return new CustomAuthenSuccessHandler();
    }

    @Autowired
    private UserDetailsService userDetailsService;


//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider authenProvider = new DaoAuthenticationProvider();
//        authenProvider.setUserDetailsService(userDetailsService);
//        authenProvider.setPasswordEncoder(passwordEncoder());
//        return authenProvider;
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.
                authorizeHttpRequests(authManagerRqMatcherRegistry -> authManagerRqMatcherRegistry
                  .requestMatchers("/", "/home","/register", "/css/**","/js/**","/images/**").permitAll()
                  .requestMatchers("/h2-console/**").permitAll()
                  .requestMatchers("/admin/**").hasRole("ADMIN")
                  .requestMatchers("/books/add", "/books/edit/**","/books/delete/**").hasAnyRole("ADMIN", "OPERATOR")
                  .requestMatchers("/books/**").hasAnyRole("ADMIN", "OPERATOR", "USER")
                  .anyRequest().authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login").successHandler(customAuthenSuccessHandler()).failureUrl("/login?error=true")
                .permitAll()
                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("")
                .permitAll()
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedPage("/access-denied")
                );

        return httpSecurity.build();
    }
}
