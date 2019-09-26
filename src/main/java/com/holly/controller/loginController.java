package com.holly.controller;


import com.holly.constant.MessageConstant;
import com.holly.constant.RedisMessageConstant;
import com.holly.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RequestMapping("/login")
@RestController
public class loginController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map){
        System.out.println(map);
        //获取前端给出的验证码和手机号进行判断
        String validateCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        String s = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);

        if(validateCode!=null&&s!=null&&validateCode.equals(s)){
            return new Result(true, "验证成功");
        }else {
            return new Result(false,"验证码错误");
        }

    }

}
