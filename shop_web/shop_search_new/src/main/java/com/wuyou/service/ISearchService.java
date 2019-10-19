package com.wuyou.service;

import com.wuyou.entity.Goods;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/15
 */
public interface ISearchService {
    boolean insert(Goods goods);

    List<Goods> getGoodsByKeyword(String keyword);
}
