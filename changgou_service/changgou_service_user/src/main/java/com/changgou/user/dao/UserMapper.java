package com.changgou.user.dao;

import com.changgou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    /**
     * 增加用户积分
     *
     */
    @Update("UPDATE tb_user set points=points+#{point} where username=#{username}")
    public int addUserPoints(@Param("username") String username, @Param("point") Integer point);

    @Update("UPDATE tb_user set password=#{hashpw} where username=#{username}")
    public void updatePassword(@Param("username") String username,@Param("hashpw")String hashpw);

}
