package com.iamdreamcatcher.restaurantChain.model.user;

public enum Permission {
    OWN("own"),
    MANAGE("manage"),
    WORK("work"),
    USE("use");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
