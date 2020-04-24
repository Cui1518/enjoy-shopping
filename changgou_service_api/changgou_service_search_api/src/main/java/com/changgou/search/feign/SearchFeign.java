package com.changgou.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient(name = "search")
public interface SearchFeign {
    @GetMapping("/search/list")
    public String list(@RequestParam Map<String,String> searchMap, Model model);

    @GetMapping("/search")
    @ResponseBody
    public Map search(@RequestParam Map<String,String> searchMap);
}
