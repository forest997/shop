package com.wuyou.feign;

import com.wuyou.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Forest
 * @Date 2019/10/15
 */
@FeignClient("web-item")
public interface ItemFeign {
    @RequestMapping("/item/createHtml")
    boolean createHtml(@RequestBody Goods goods);
}
