package com.viniciusvieira.jwt.core.security.config;

import com.viniciusvieira.jwt.core.security.filter.JwtAuthenticationFilter;
import com.viniciusvieira.jwt.domain.model.user.PermissionTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.viniciusvieira.jwt.domain.model.user.PermissionTypes.*;
import static com.viniciusvieira.jwt.domain.model.user.RoleTypes.ADMIN;
import static com.viniciusvieira.jwt.domain.model.user.RoleTypes.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                );

        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                // permite qualquer um acessar "/api/v1/auth/**" sem estar autenticado
                                .requestMatchers("/api/v1/auth/**")
                                    .permitAll()

                                // para ter acesso a rota 'management' o usuário deve possuir
                                // a Role ADMIN ou MANAGER
                                // hasAnyRole permite + de uma Role
                                .requestMatchers("/api/v1/management/**")
                                    .hasAnyRole(ADMIN.name(), MANAGER.name())

                                // qualquer metodo do tipo get, só pode ser acessado se o usuário possuir a Permissao
                                // ADMIN_READ, MANAGER_READ
                                // hasAnyAuthority aceita + de 1 authority/permissao
                                .requestMatchers(HttpMethod.GET,"/api/v1/management/**")
                                    .hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())

                                .requestMatchers(HttpMethod.POST,"/api/v1/management/**")
                                    .hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/management/**")
                                    .hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/management/**")
                                    .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                                // setando o acesso de Role e Permissao para a rota de 'admin'
                                // hasAuthority aceita somente 1 authority/permissao
                                // hasRole aceita somente 1 role
                                .requestMatchers("/api/v1/admin/**")
                                    .hasRole(ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/api/v1/admin/**")
                                    .hasAuthority(ADMIN_READ.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**")
                                    .hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**")
                                    .hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**")
                                    .hasAuthority(ADMIN_DELETE.name())

                                // todas as outras rotas tirando "/api/v1/auth/**", o usuário deve estar authenticado
                                .anyRequest()
                                    .authenticated()
                );

        http.sessionManagement(
                session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .logout(
                        logout -> logout
                                .logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        (request, response, authentication) -> SecurityContextHolder.clearContext()
                                )
                );

        return http.build();
    }
}

/*
    Outros Exemplos

    .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
            )
 */