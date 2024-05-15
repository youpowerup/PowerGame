package com.example.Mapper;

import com.example.pojo.Game;
import com.example.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

public interface OrderMapper {

    @Insert("insert into `order` VALUES (#{username}, #{gameName},#{amount},#{unitPrice},#{sumPrice},#{orderTime},#{ownerName})")
    Integer insert(@Param("username") String username, @Param("gameName") String gameName,
                   @Param("unitPrice") Double unitPrice, @Param("amount") Integer amount,
                   @Param("sumPrice") Double sumPrice, @Param("orderTime") Timestamp orderTime,
                   @Param("ownerName") String ownerName);

    @Select("select * from `order` where username=#{username}")
    List<Order> listByUsername(@Param("username") String username);

    @Select("select * from `order` where ownerName=#{ownerName}")
    List<Order> listByOwnerName(@Param("ownerName") String ownerName);

    @Select("select * from `order` where gameName=#{gameName}")
    List<Order> listByGameName(@Param("gameName") String gameName);
}
