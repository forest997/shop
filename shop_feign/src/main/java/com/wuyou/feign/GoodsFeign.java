package com.wuyou.feign;

import com.wuyou.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/10
 */
@FeignClient("web-goods")
public interface GoodsFeign {
    @RequestMapping("/goods/list")
    public List<Goods> list();

    @RequestMapping("/goods/insert")
    boolean insert(@RequestBody Goods goods);
}
