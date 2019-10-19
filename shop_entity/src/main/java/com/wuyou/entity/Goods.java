package com.wuyou.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Forest
 * @Date 2019/10/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseEntity{
    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;
    @TableField(exist = false)
    private String coverImg;
    @TableField(exist = false)
    private List<String> otherImg;
    @TableField(exist = false)
    private List<GoodsImage> goodsImageList;
}
