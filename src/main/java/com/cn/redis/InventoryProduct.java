/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

/**
 * @author hebiao
 * @version $Id:InventoryProduct.java, v0.1 2018/11/12 14:08 hebiao Exp $$
 */
public class InventoryProduct {

    private Integer productId;

    private Long InventoryCnt;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getInventoryCnt() {
        return InventoryCnt;
    }

    public void setInventoryCnt(Long inventoryCnt) {
        InventoryCnt = inventoryCnt;
    }
}
