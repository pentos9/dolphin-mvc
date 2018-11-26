package com.spacex.dolphin.service.impl;

import com.spacex.dolphin.annotation.Service;
import com.spacex.dolphin.service.UserService;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String search(String name) {
        return "User-" + new Random().nextInt(1000);
    }
}
