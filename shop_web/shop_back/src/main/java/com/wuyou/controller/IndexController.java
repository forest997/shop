package com.wuyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Forest
 * @Date 2019/10/9
 */
@Controller
public class IndexController {
    @RequestMapping("/tohome")
    public String toHome(){
        return "home";
    }
}
