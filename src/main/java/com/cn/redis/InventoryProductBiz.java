/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

/**
 *
 *
 * @author hebiao
 * @version $Id:InventoryProductBiz.java, v0.1 2018/11/12 14:13 hebiao Exp $$ 
 */
public class InventoryProductBiz {
    private InventoryProduct inventoryProductCache;

    public void removeInventoryProductCache(Integer productId) {
    }

    public void updateInventoryProduct(InventoryProduct inventoryProduct) {
    }

    public InventoryProduct loadInventoryProductByProductId(Integer productId) {
        return new InventoryProduct();
    }

    public void setInventoryProductCache(InventoryProduct inventoryProductCache) {
        this.inventoryProductCache = inventoryProductCache;
    }
}
