package com.example.demo.enums;

public enum RolEnum {
    ADMIN(1L),
    CASHIER(2L),
    SUPPLIER(2L);

    private final Long id;

    RolEnum(Long id) {

        this.id = id;

    }

    public Long getId() {

        return id;

    }
    
}
