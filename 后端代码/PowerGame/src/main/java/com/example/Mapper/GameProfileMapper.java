package com.example.Mapper;

import com.example.pojo.GameProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GameProfileMapper {
    @Select("select * from gamefrofile")
    List<GameProfile> list();
    @Select("SELECT * FROM gameprofile ORDER BY amount DESC")
    List<GameProfile> getOrderList();
    @Select("SELECT * FROM gameprofile WHERE type = #{type} ORDER BY amount DESC")
    List<GameProfile> getOrderListByType(@Param("type") String type);
    @Insert("insert into gameprofile values (#{gameName},#{type},#{amount}, #{sumPrice})")
    Integer insert(@Param("gameName") String gameName,@Param("type") String type, @Param("amount") Integer amount, @Param("sumPrice") Double sumPrice);
    @Delete("TRUNCATE TABLE gameprofile")
    Integer clear();
}
