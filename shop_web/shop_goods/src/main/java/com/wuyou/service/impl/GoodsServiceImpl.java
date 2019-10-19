package com.wuyou.service.impl;

import com.wuyou.dao.GoodsImageMapper;
import com.wuyou.dao.GoodsMapper;
import com.wuyou.entity.Goods;
import com.wuyou.entity.GoodsImage;
import com.wuyou.feign.ItemFeign;
import com.wuyou.feign.SearchFeign;
import com.wuyou.service.IGoodsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/10
 */
@Service

public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Autowired
    private SearchFeign searchFeign;

    @Autowired
    private ItemFeign itemFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }
    @Transactional
    @Override
    public int insert(Goods goods) {
        goodsMapper.insert(goods);
        GoodsImage coverImg = new GoodsImage().setGid(goods.getId()).setIscover(1).setUrl(goods.getCoverImg());
        goodsImageMapper.insert(coverImg);
        for (String url : goods.getOtherImg()) {
            GoodsImage otherImg = new GoodsImage().setGid(goods.getId()).setIscover(0).setUrl(goods.getCoverImg());
            goodsImageMapper.insert(otherImg);
        }

        rabbitTemplate.convertAndSend("goods_exchange","",goods);
//        if(!searchFeign.insert(goods)){
//            throw new RuntimeException("索引库添加商品信息失败");
//        }
//        if(!itemFeign.createHtml(goods)){
//            throw new RuntimeException("创建商品页面失败");
//        }
        return 1;
    }
}
