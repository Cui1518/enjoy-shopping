package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CategoryMapper extends Mapper<Category> {


    /**
     * $  不能防止sql注入 直接sql拼接 底层是 statement
     * #   预编译  能够防止sql注入
     * @Param("categoryName")  一个参数的时候只有参数名称对应计策  但是出现多个参数的时候，必须添加param
     * @param categoryName
     * @return
     */
    @Select("select * from tb_brand where id in (select brand_id from tb_category_brand where category_id in (select id from tb_category where `name` = ${categoryName}))")
    List<Map> findBrandListByCateGoryName(@Param("categoryName") String categoryName);

}
