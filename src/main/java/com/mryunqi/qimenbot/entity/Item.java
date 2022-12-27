package com.mryunqi.qimenbot.entity;

import lombok.Data;

/**
 * @author mryunqi
 * @date 2022/12/19
 */
@Data
public class Item {
    private String category;
    private String itemName;
    private String currency;
    private Long price;
}
