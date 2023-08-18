package com.viniciusvieira.jwt.domain.repository;

import com.viniciusvieira.jwt.domain.model.token.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenModelRepository extends JpaRepository<TokenModel, UUID> {

    @Query("""
            SELECT t from TokenModel t inner join User u ON t.user.id = u.id
            WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)
            """)
    List<TokenModel> findAllValidTokensByUser(UUID userId);

    Optional<TokenModel> findByToken(String token);
}