package com.example.demo.service;

import com.example.demo.common.CommonUtils;
import com.example.demo.common.GetOuterId;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author y001
 * 用户注册
 */
@Service
public class RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GetOuterId getOuterId;
    /**
     * 用户注册
     * @param param
     * @return
     */
    @Transactional(rollbackFor= Exception.class)
    public Map<String,Object> userRegister(Map<String,Object> param){
        Map<String,Object> resMap = new HashMap<>();
        String username = String.valueOf(param.get("username"));
        //判断登录账号为手机号还是用户名
        if (CommonUtils.isMobileNO(username)){
            param.put("telephone",username);
        }else {
            param.put("loginName",username);
        }
        //判断用户是否存在
        Map<String, Object> userLogin = userMapper.getUserLogin(param);
        if (CommonUtils.isEmpty(userLogin)){
            //密码使用MD5加密
            String password = MD5Utils.getMD5Code(String.valueOf(param.get("password")));
            param.put("password",password);
            //生成外部id
            param.put("outerId",getOuterId.generateOrderId());
            int register = userMapper.register(param);
            if (register<1){
                throw new RuntimeException();
            }
            resMap.put("code","success");
            resMap.put("message","注册成功");
        }else {
            resMap.put("code","filed");
            resMap.put("message","用户已存在");
        }
        return resMap;
    }
}
