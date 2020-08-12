package com.example.demo.controller;

import com.example.demo.util.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author y001
 * 测试
 */
@Api("测试")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisUtil redisUtil;


    @PostMapping("/client")
    public Map<String,Object> testClient(@RequestParam Map<String,Object> param){
        //ModelAndView mv = new ModelAndView();
        Map<String,Object> resMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        resMap.put("redis",redisUtil);
        resMap.put("userName","张三");
        resMap.put("password","password");
        resMap.put("list",list);

        /*redisUtil.set("test","测试");
        redisUtil.set("userName","aaa");
        redisUtil.set("password","sss");*/
        //mv.addObject("result",resMap);
        //mv.setViewName("test");
        return resMap;
    }

    @RequestMapping("/view")
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView();
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("redis",redisUtil);
        resMap.put("userName","张三");
        resMap.put("password","password");
        /*redisUtil.set("test","测试");
        redisUtil.set("userName","aaa");
        redisUtil.set("password","sss");*/
        mv.addObject("result",resMap);
        mv.setViewName("test");
        return mv;
    }

    @RequestMapping("/testRedis")
    public ModelAndView testRedis(){
        ModelAndView mv = new ModelAndView();
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("redis",redisUtil);
        resMap.put("userName",redisUtil.get("test"));
        resMap.put("password",redisUtil.get("password"));
        if (!("".equals(redisUtil.get("userName"))||null==redisUtil.get("userName"))){
            redisUtil.del("userName");
        }
        resMap.put("test",redisUtil.get("userName"));
        redisUtil.set("resMap",resMap);
        mv.addObject("result",resMap);
        mv.setViewName("testRedis");
        return mv;
    }
}
