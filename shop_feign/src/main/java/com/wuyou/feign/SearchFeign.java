package com.wuyou.feign;

import com.wuyou.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Forest
 * @Date 2019/10/14
 */
@FeignClient("web-search")
public interface SearchFeign {
    @RequestMapping("/search/insert")
    boolean insert(@RequestBody Goods goods);

}
