package com.overgaauw.chat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers(
                        "/secured/**/**",
                        "/secured/success",
                        "/secured/socket",
                        "/secured/success").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Leonardo").password(passwordEncoder().encode("p1zza")).roles("USER")
                .and()
                .withUser("Michelangelo").password(passwordEncoder().encode("p1zza")).roles("USER")
                .and()
                .withUser("Donatello").password(passwordEncoder().encode("p1zza")).roles("USER")
                .and()
                .withUser("Raphael").password(passwordEncoder().encode("p1zza")).roles("USER")
                .and()
                .withUser("Splinter").password(passwordEncoder().encode("p1zza")).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
