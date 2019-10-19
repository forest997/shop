package com.wuyou.controller;

import com.wuyou.entity.Goods;
import com.wuyou.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/10
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;


    @RequestMapping("/list")
    @ResponseBody
    public List<Goods> list() {
        System.out.println("web-goods:/goods/list");
        return goodsService.getGoodsList();
    }

    @ResponseBody
    @RequestMapping("/insert")
    public boolean insert(@RequestBody Goods goods) {
        System.out.println("web-goods Get web-back : " + goods);
        return goodsService.insert(goods) > 0;
    }

}
