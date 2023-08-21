package com.viniciusvieira.jwt;

import com.viniciusvieira.jwt.domain.model.user.RoleTypes;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomTest {

    @Test
    void testeBooelan(){
        boolean isExpired = true;
        boolean isRegoked = true;

        assertDoesNotThrow(() -> {
            boolean invalid = isExpired && isRegoked;
            boolean valid = !isExpired && !isRegoked;
            boolean invalid2 = isExpired && !isRegoked;

            System.out.println(invalid2);
            System.out.println(valid);
            System.out.println(invalid2);
        });
    }

    @Test
    void authorities(){
        assertDoesNotThrow(()  -> {
            List<SimpleGrantedAuthority> authorities = RoleTypes.ADMIN.getAuthorities();
            authorities.forEach(System.out::println);
        });
    }
}
