package com.example.Mapper;

import com.example.pojo.UserProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserProfileMapper {
    @Select("select * from userprofile")
    List<UserProfile> list();
    @Select("select * from userprofile where username=#{username}")
    UserProfile getUserProfileByUsername(@Param("username") String username);
    @Delete("TRUNCATE TABLE userprofile")
    Integer clear();
    @Insert("insert into userprofile values (#{username},#{amount}, #{sumPrice},#{mostLove},#{secondLove})")
    Integer insert(@Param("username") String username, @Param("amount") Integer amount,
                   @Param("sumPrice") Double sumPrice,@Param("mostLove") String mostLove, @Param("secondLove") String secondLove);
    @Update("update userprofile set mostLove=#{mostLove},secondLove=#{secondLove} where username=#{username}")
    Integer updateCommendGame(@Param("username") String username,@Param("mostLove") String mostLove,@Param("secondLove") String secondLove);
}
