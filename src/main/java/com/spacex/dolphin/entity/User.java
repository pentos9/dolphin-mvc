package com.spacex.dolphin.entity;

import com.spacex.dolphin.annotation.Component;

@Component
public class User {
    private Long restaurantId;
    private String name;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
