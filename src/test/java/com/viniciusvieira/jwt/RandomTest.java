package com.viniciusvieira.jwt;

import org.junit.jupiter.api.Test;
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
}
