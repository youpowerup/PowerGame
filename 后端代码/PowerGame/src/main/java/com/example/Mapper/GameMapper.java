package com.example.Mapper;

import com.example.pojo.Game;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.List;

public interface GameMapper {
    @Select("select * from game")
    List<Game> list();
    @Select("select * from game where isOnSale=1")
    List<Game> listOnSale();

    @Select("select * from game where type=#{type}")
    List<Game> getGameByType(String type);
    @Select("select type from game where gameName=#{gameName}")
    String getTypeByGameName(String gameName);

    @Select("SELECT DISTINCT type FROM game")
    List<String> listOfType();
    @Select("select * from game where gameName=#{gameName}")
    Game getSingleGame(String gameName);
    @Select("select * from game where ownerName=#{ownerName}")
    List<Game> getGameByOwnerName(String ownerName);

    @Update("update game set gameDetail=#{gameDetail},price=#{price},stock=#{stock},type=#{type},ownerName=#{ownerName},img=#{img}" +
            " where gameName=#{gameName}")
    Integer update(@Param("gameName") String gameName,@Param("gameDetail") String gameDetail,@Param("price") Double price,
                   @Param("stock") Integer stock, @Param("type") String type,@Param("ownerName") String ownerName,@Param("img") Blob img);

    @Update("update game set isOnSale=#{isOnSale} where gameName=#{gameName}")
    Integer updateIsOnSale(@Param("gameName") String gameName,@Param("isOnSale") Integer isOnSale);

    @Insert("INSERT INTO game (gameName, gameDetail, price, stock, type, ownerName, img, isOnSale) " +
            "VALUES " +
            "(#{gameName},#{gameDetail},#{price},#{stock},#{type},#{ownerName},#{img},#{isOnSale})")
    Integer insertNewGame(@Param("gameName") String gameName,@Param("gameDetail") String gameDetail,@Param("price") Double price,
                          @Param("stock") Integer stock, @Param("type") String type,@Param("ownerName") String ownerName,
                          @Param("img") Blob img, @Param("isOnSale") Integer isOnSale);
}
