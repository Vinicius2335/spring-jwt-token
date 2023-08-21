package com.viniciusvieira.jwt.domain.model.token;

import com.viniciusvieira.jwt.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenTypes tokenTypes;

    @Column(columnDefinition = "boolean")
    private boolean expired;
    @Column(columnDefinition = "boolean")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
