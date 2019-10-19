package com.wuyou.controller;

import com.google.gson.Gson;
import com.wuyou.entity.Goods;
import com.wuyou.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/14
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;
    @ResponseBody
    @RequestMapping("/insert")
    public boolean insert(@RequestBody Goods goods) {
        return searchService.insert(goods);

    }
    @RequestMapping("/getGoodsByKeyword")
    public String getGoodsByKeyword(String keyword, ModelMap map) {
        List<Goods> goodsList = searchService.getGoodsByKeyword(keyword);
        map.put("goodsList", goodsList);
        return "searchlist";
    }
}
