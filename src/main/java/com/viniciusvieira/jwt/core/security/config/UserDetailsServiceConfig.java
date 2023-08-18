package com.viniciusvieira.jwt.core.security.config;

import com.viniciusvieira.jwt.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig {
    private final UserRepository userRepository;

    // Estamos Reescrevendo o loadUserByUsername para ser usado em  JpaAuthenticationFilter.
    // Obs: nessa aplicação o username para login/autenticação do usuário é o email.
    //@Bean
    //public UserDetailsService userDetailsService(){
    //    return new UserDetailsService() {
    //        @Override
    //        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //            return null;
    //        }
    //    }
    //}

    // Lambada para deixar o código mais limpo.
    @Bean
    public UserDetailsService userDetailsService(){
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found..."));
    }

}
