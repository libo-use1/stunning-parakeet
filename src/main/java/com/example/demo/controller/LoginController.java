package com.example.demo.controller;

import com.example.demo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author y0                                                                                   01
 * 用户注册
 */
@Api("单点登录")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;


}
