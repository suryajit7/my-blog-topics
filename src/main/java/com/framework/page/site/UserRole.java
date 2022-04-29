package com.framework.page.site;

public enum UserRole {

    ADMIN("Admin"), ESS("ESS");

    private final String stringValue;

    UserRole(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStrValue() {
        return this.stringValue;
    }
}
