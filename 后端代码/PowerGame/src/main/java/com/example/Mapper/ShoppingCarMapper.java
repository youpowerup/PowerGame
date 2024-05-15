package com.example.Mapper;

import com.example.pojo.ShoppingCar;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ShoppingCarMapper {
    @Select("select * from shoppingcar where username=#{username}")
    List<ShoppingCar> list(String username);

    @Select("select * from shoppingcar where (username=#{username} AND gameName=#{gameName})")
    ShoppingCar check(@Param("username")String username, @Param("gameName")String gameName);

    @Insert("insert into shoppingcar VALUES (#{username}, #{gameName},#{amount})")
    Integer insert(@Param("username") String username,@Param("gameName") String gameName, @Param("amount") Integer amount);

    @Update("update shoppingcar set amount=#{amount} where (username=#{username} AND gameName=#{gameName})")
    Integer updateShoppingCar(@Param("username") String username,@Param("gameName") String gameName, @Param("amount") Integer amount);
    @Delete("DELETE FROM shoppingcar WHERE username=#{username} AND gameName=#{gameName}")
    Integer deleteShoppingCarByUsernameAndGameName(@Param("username") String username,@Param("gameName") String gameName);
}
