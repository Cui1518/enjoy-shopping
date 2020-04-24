package com.changgou.seckill.service;

/**
 * @author cxl
 * @date 2020-04-19 20:07
 */

public interface SecKillOrderService {

    /**
     * 秒杀下单
     * @param id
     * @param time
     * @param username
     * @return
     */
    boolean add(Long id,String time,String username);
}
