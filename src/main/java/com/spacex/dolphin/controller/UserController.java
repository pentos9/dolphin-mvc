package com.spacex.dolphin.controller;

import com.spacex.dolphin.annotation.Autowired;
import com.spacex.dolphin.annotation.Controller;
import com.spacex.dolphin.annotation.RequestMapping;
import com.spacex.dolphin.annotation.RequestParam;
import com.spacex.dolphin.service.UserService;
import com.spacex.dolphin.util.ServletPrintUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/get")
    public void getUser(@RequestParam Long userId, HttpServletRequest request, HttpServletResponse response) {
        String result = String.valueOf(new Random().nextInt(100)) + "-User-" + userId;
        ServletPrintUtil.print(response, result);
    }
}
