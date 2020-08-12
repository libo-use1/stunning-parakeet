package com.example.demo.controller;

import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author y001
 * 注册
 */
@Api("注册")
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @ApiOperation("注册")
    @PostMapping("/doRegister")
    public Map<String,Object> doLogin(@RequestParam Map<String,Object> param){
        Map<String,Object> resMap = new HashMap<>();
        try{
            Map<String, Object> login = registerService.userRegister(param);
            resMap.putAll(login);
        }catch (Exception e){
            resMap.put("code","failed");
            resMap.put("message","注册失败");
        }
        return resMap;
    }
}
