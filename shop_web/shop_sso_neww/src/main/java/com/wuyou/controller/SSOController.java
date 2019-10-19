package com.wuyou.controller;

import com.wuyou.entity.Email;
import com.wuyou.entity.ResultData;
import com.wuyou.entity.User;
import com.wuyou.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Forest
 * @Date 2019/10/18
 */
@Controller
@RequestMapping("/sso")
public class SSOController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private IUserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/register")
    public String register(User user, ModelMap map) {
        int result = userService.register(user);
        if (result == -1) {
            map.put("msg", "用户已存在");
            return "register";
        } else if (result > 0) {
            map.put("msg", "恭喜你,注册成功");
            return "login";
        } else {
            map.put("msg", "注册失败,请联系管理员");
            return "register";
        }

    }

    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forgetpwd";
    }

    @RequestMapping("/forgetPassword")
    @ResponseBody
    public ResultData<Map> forgetPassword(String username){
        User user = userService.getByUsername(username);
        if(user==null){
            return new ResultData().setCode("0000").setMsg("用户不存在");
        }else {
            String token = UUID.randomUUID().toString();
            stringRedisTemplate.opsForValue().set(token, username);
            stringRedisTemplate.expire(token, 15, TimeUnit.MINUTES);
            String content = "SuperShop:尊敬" + user.getNickname() + "顾客你好,您可通过点击以下链接来找回您的密码。<a href='http://localhost:16666/sso/toResetPassword?token=" + token + "'>找回密码</a>";
            Email email = new Email().setSubject("SuperShop---找回密码").setTo(user.getEmail()).setContent(content);
            rabbitTemplate.convertAndSend("email_exchange","",email);
            Map<String, String> data = new HashMap<>();
            String emailInfo = user.getEmail().replace(user.getEmail().substring(3, user.getEmail().indexOf("@")), "******");
            String emailUrl = "http://mail."+user.getEmail().substring(user.getEmail().indexOf("@") + 1);
            data.put("emailInfo", emailInfo);
            data.put("emailUrl", emailUrl);
            return new ResultData().setCode("0001").setMsg("邮件发送成功").setData(data);
        }
    }

    @RequestMapping("/toResetPassword")
    public String toResetPassword(String token,ModelMap map){
        String username = stringRedisTemplate.opsForValue().get(token);
        if(username==null){
            map.put("msg","链接已失效");
            return "resetError";
        }
        return "resetPwd";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(String token, String newPassword,ModelMap map) {
        String username = stringRedisTemplate.opsForValue().get(token);
        if(username!=null) {
            User user = userService.getByUsername(username);
            if(user!=null){
                user.setPassword(newPassword);
                int result = userService.updatePasswordByUsername(user);
                if(result>0){
                    stringRedisTemplate.delete(token);
                    map.put("msg", "重置密码成功");
                    return "login";
                }
            }
        }
        map.put("msg","用户不存在,请联系管理员");
        return "resetError";
    }
}
