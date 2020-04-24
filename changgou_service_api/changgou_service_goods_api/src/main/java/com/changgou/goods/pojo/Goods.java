package com.changgou.goods.pojo;

import java.util.List;

/**
 * @author cxl
 * @date 2020-03-26 15:03
 */

public class Goods {
    //spu
    private Spu spu;
    //sku集合
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
