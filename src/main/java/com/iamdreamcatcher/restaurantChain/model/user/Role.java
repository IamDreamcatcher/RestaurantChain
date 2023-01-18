package com.iamdreamcatcher.restaurantChain.model.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    OWNER(Set.of(Permission.OWN, Permission.MANAGE, Permission.USE, Permission.WORK)),
    ADMIN(Set.of(Permission.MANAGE, Permission.USE, Permission.WORK)),
    EMPLOYEE(Set.of(Permission.USE, Permission.WORK)),
    USER(Set.of(Permission.USE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    public Set<Permission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
