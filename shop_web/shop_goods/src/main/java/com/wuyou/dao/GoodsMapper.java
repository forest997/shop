package com.wuyou.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyou.entity.Goods;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/10
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> getGoodsList();
}
