package com.wuyou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Forest
 * @Date 2019/10/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GoodsImage extends BaseEntity{
    private Integer gid;
    private String info;
    private String url;
    private Integer iscover;
}
