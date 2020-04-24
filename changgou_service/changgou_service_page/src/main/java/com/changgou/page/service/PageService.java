package com.changgou.page.service;

import org.springframework.stereotype.Service;


public interface PageService {
    //生成静态化页面
    void generateHtml(String spuId);
}
