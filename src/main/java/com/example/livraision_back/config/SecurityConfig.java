package com.example.livraision_back.config;

import com.example.livraision_back.security.JwtAuthenticationFilter;
import com.example.livraision_back.service.AdminAuthenticationProvider;
import com.example.livraision_back.service.ClientAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminAuthenticationProvider adminAuthenticationProvider;
    private final ClientAuthenticationProvider clientAuthenticationProvider;
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(
        @Lazy AdminAuthenticationProvider adminAuthenticationProvider,
        @Lazy ClientAuthenticationProvider clientAuthenticationProvider,
        JwtAuthenticationFilter jwtFilter) {
        this.adminAuthenticationProvider = adminAuthenticationProvider;
        this.clientAuthenticationProvider = clientAuthenticationProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager setup using CompositeAuthenticationProvider
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(new CompositeAuthenticationProvider(
            adminAuthenticationProvider, clientAuthenticationProvider));

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeRequests(auth -> auth
                .requestMatchers("/api/auth/**","/api/uploadPj1/**", "/api/clients/**", "/api/vendeurs/**", "/api/livreurs/**", "/api/commandes/**", "/api/utilisateurs/**", "/swagger-ui/**", "/v3/api-docs/**", "/api/pieces-justificatives-vendeurs/**","/api/notification/**")
                .permitAll()
                .anyRequest().authenticated()
            )
            .cors().and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ⚡ Définit explicitement l'origine autorisée
        config.setAllowedOrigins(List.of("http://localhost:8081"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); // ⚠️ peut rester true si origine explicite

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    // CompositeAuthenticationProvider to combine Admin and Client Authentication Providers
    public static class CompositeAuthenticationProvider implements AuthenticationProvider {

        private final AuthenticationProvider adminAuthenticationProvider;
        private final AuthenticationProvider clientAuthenticationProvider;

        public CompositeAuthenticationProvider(AuthenticationProvider adminAuthenticationProvider,
                                               AuthenticationProvider clientAuthenticationProvider) {
            this.adminAuthenticationProvider = adminAuthenticationProvider;
            this.clientAuthenticationProvider = clientAuthenticationProvider;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            Authentication auth = null;

            // Try Admin Authentication Provider first
            try {
                auth = adminAuthenticationProvider.authenticate(authentication);
            } catch (AuthenticationException e) {
                // Ignore and try the Client Authentication Provider
            }

            // If Admin Authentication failed, try Client Authentication Provider
            if (auth == null) {
                auth = clientAuthenticationProvider.authenticate(authentication);
            }

            return auth;
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return adminAuthenticationProvider.supports(authentication) || clientAuthenticationProvider.supports(authentication);
        }
    }
}
