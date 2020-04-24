package com.changgou.user.feign;

import com.changgou.entity.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")
public interface UserFegin {
    @GetMapping("/user/load/{username}")
    public User findUserInfo(@PathVariable("username")String username);

    @GetMapping("user/points/add")
    public Result addPoints(@RequestParam("point") Integer point);
}
