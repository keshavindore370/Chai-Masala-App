package com.chaisharma.chaimasala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // TODO: enable CSRF before going to production
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Public pages — no login required
                        .requestMatchers("/", "/login", "/register",
                                "/adrak-chai", "/cart", "/error",
                                "/forgot-password").permitAll()
                        // Static assets
                        .requestMatchers("/css/**", "/js/**",
                                "/images/**", "/webjars/**").permitAll()
                        // Admin pages — only ROLE_ADMIN can access
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Everything else needs login
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("mobileNumber") // matches HTML input name
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }
}
