package com.wuyou.service;

import com.wuyou.entity.Goods;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/10
 */
public interface IGoodsService {
    List<Goods> getGoodsList();

    int insert(Goods goods);
}
