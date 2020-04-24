package com.changgou.seckill.task;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author cxl
 * @date 2020-04-19 11:07
 */
@Component
public class SeckillGoodsPushTask {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String SECKILL_GOODS_KEY="seckill_goods_";

    public static final String SECKILL_GOODS_STOCK_COUNT_KEY="seckill_goods_stock_count_";

    /**
     * 定时将秒杀商品存入redis
     * 暂定为30秒一次，正常业务为每天凌晨触发
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void loadSecKillGoodsToRedis(){
        /**
         * 1.查询所有符合条件的秒杀商品
         *     1) 获取时间段集合并循环遍历出每一个时间段
         *     2) 获取每一个时间段名称,用于后续redis中key的设置
         *     3) 状态必须为审核通过 status=1
         *     4) 商品库存个数>0
         *     5) 秒杀商品开始时间>=当前时间段
         *     6) 秒杀商品结束<当前时间段+2小时
         *     7) 排除之前已经加载到Redis缓存中的商品数据
         *     8) 执行查询获取对应的结果集
         * 2.将秒杀商品存入缓存
         */

        List<Date> dateMenus = DateUtil.getDateMenus();//获取当前时间以及后面的5个时间段(每两个小时为一个时间段)
        for (Date dateMenu : dateMenus) {
            //格式化时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String redisExtName  = DateUtil.date2Str(dateMenu);//时间转成yyyyMMddHH

            Example example=new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status","1");
            criteria.andGreaterThan("stockCount","0");//该字段属性值大于0
            criteria.andGreaterThanOrEqualTo("startTime",simpleDateFormat.format(dateMenu));//秒杀商品开始时间>=当前时间段
            criteria.andLessThan("endTime",simpleDateFormat.format(DateUtil.addDateHour(dateMenu,2)));
            //hash内部结构 key:SECKILL_GOODS_KEY + redisExtName   field:id    value商品对象
            Set keys = redisTemplate.boundHashOps(SECKILL_GOODS_KEY + redisExtName).keys();
            if (keys!=null && keys.size()>0){
                criteria.andNotIn("id",keys);//该字段中没有keys属性值,即商品未加载到redis中
            }

            List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);

            //添加到缓存中
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                redisTemplate.opsForHash().put(SECKILL_GOODS_KEY + redisExtName,seckillGoods.getId(),seckillGoods);

                //加载秒杀商品的库存  (预扣减缓存中的库存再异步扣减mysql数据)
                redisTemplate.opsForValue().set(SECKILL_GOODS_STOCK_COUNT_KEY+seckillGoods.getId(),seckillGoods.getStockCount());

            }
        }

    }
}
