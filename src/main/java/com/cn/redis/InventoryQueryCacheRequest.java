/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

/**
 * @author hebiao
 * @version $Id:InventoryQueryCacheRequest.java, v0.1 2018/11/12 14:14 hebiao Exp $$
 */
public class InventoryQueryCacheRequest implements Request {

    private InventoryProductBiz inventoryProductBiz;

    private Integer productId;

    private boolean isForceFefresh;


    public InventoryQueryCacheRequest(Integer productId, InventoryProductBiz inventoryProductBiz,
            boolean isForceFefresh) {

        this.productId = productId;

        this.inventoryProductBiz = inventoryProductBiz;

        this.isForceFefresh = isForceFefresh;

    }

    @Override

    public void process() {

        InventoryProduct inventoryProduct = inventoryProductBiz.loadInventoryProductByProductId(productId);

        inventoryProductBiz.setInventoryProductCache(inventoryProduct);

    }

    @Override

    public Integer getProductId() {


        return productId;

    }

    public boolean isForceFefresh() {

        return isForceFefresh;

    }

    public void setForceFefresh(boolean isForceFefresh) {

        this.isForceFefresh = isForceFefresh;

    }
}
