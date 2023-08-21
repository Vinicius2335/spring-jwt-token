package com.viniciusvieira.jwt.domain.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.viniciusvieira.jwt.domain.model.user.PermissionTypes.*;
import static com.viniciusvieira.jwt.domain.model.user.PermissionTypes.MANAGER_CREATE;

@Getter
@RequiredArgsConstructor
public enum RoleTypes {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );

    private final Set<PermissionTypes> permissions;

    // 1- transforma cada permissao em SimpleGrantedAuthority
    // 2 - ajusta o nome da role para ser usado pelo spring security com o ROLE_
    // 3- transfoma essa role em SimpleGrantedAuthority
    // 4 - Retorna tudo isso numa lista de SimpleGrantedAuthority para ser usado no User model
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
