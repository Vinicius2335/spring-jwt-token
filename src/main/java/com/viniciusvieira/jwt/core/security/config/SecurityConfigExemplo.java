package com.viniciusvieira.jwt.core.security.config;

import com.viniciusvieira.jwt.core.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class SecurityConfigExemplo {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                    .requestMatchers("/api/v1/auth/**")
                        .permitAll() // permitindo todos os acessos

                // permite qualquer um acessar "/api/v1/auth/**" sem estar autenticado
                // caso outro controller for acessado dá forbidden

                // ---- aceita colocar tudo isso de uma vez tbm ----
                // requestMatchers("/api/v1/management/**", "outro endpoint", "outro endPoint")

                //.requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                //.requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                //.requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                //.requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                //.requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                /* .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                 .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                 .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                 .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                 .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/

                // Caso não seja a rota lá de cima
                // precisa estar autenticado

                .anyRequest() // para qualquer requisição
                    .authenticated() // precisa estar autenticado
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
