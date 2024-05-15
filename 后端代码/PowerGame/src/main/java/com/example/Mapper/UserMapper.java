package com.example.Mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//用户模块的持久层接口
@Mapper
public interface UserMapper {

    @Insert("insert into user VALUES (#{phone}, #{username}, #{password},#{type})")
    Integer insert(@Param("phone") String phone, @Param("username") String username,@Param("password") String password,@Param("type") Integer type);

    @Select("select * from user where username = #{username}")
    User selectByName(String username);

    @Select("select * from user where type = #{type}")
    List<User> listByType(@Param("type") Integer type);
    @Update("update user set password=#{password} where username=#{username}")
    Integer resetPassword(@Param("username") String username,@Param("password") String password);

}
