package com.spacex.dolphin.controller;

import com.spacex.dolphin.annotation.Controller;
import com.spacex.dolphin.annotation.RequestMapping;

import java.util.Random;

@Controller
public class UserController {

    @RequestMapping(value = "user/get")
    public String getUser(Long userId) {
        return String.valueOf(new Random().nextInt(100));
    }
}
