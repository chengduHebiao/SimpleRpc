/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.redis;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author hebiao
 * @version $Id:InventoryUpdateDBRequest.java, v0.1 2018/11/12 14:10 hebiao Exp $$
 */
public class InventoryUpdateDBRequest implements Request {

    private InventoryProductBiz inventoryProductBiz;

    private InventoryProduct inventoryProduct;


    public InventoryUpdateDBRequest(InventoryProduct inventoryProduct, InventoryProductBiz inventoryProductBiz) {

        this.inventoryProduct = inventoryProduct;

        this.inventoryProductBiz = inventoryProductBiz;

    }

    @Override
    @Transactional
    public void process() {

        inventoryProductBiz.removeInventoryProductCache(inventoryProduct.getProductId());

        inventoryProductBiz.updateInventoryProduct(inventoryProduct);

    }

    @Override
    public Integer getProductId() {

        return inventoryProduct.getProductId();

    }

    @Override

    public boolean isForceFefresh() {

        return false;

    }


}
