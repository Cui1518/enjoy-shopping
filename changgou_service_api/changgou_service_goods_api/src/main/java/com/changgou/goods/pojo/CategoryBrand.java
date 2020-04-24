package com.changgou.goods.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author cxl
 * @date 2020-03-26 19:40
 */
@Table(name="tb_category_brand")
public class CategoryBrand implements Serializable {
    //分类id
    @Id
    private Integer categoryId;
    //品牌id
    @Id
    private Integer brandId;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
