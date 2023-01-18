package com.iamdreamcatcher.restaurantChain.model.user;

public enum Permission {
    OWNER("owner"),
    ADMIN("admin"),
    EMPLOYEE("employee"),
    USER("user");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
