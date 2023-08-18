package com.viniciusvieira.jwt.domain.repository;

import com.viniciusvieira.jwt.domain.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("""
            SELECT t from Token t inner join User u ON t.user.id = u.id
            WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findAllValidTokensByUser(UUID userId);

    Optional<Token> findByName(String name);
}