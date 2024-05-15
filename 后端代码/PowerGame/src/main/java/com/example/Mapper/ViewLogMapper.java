package com.example.Mapper;

import com.example.pojo.ViewLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

public interface ViewLogMapper {
    @Insert("insert into viewlog VALUES (#{username},#{type},#{beginViewTime},#{endViewTime},#{viewTime})")
    Integer insertNewViewLog(@Param("username") String username, @Param("type") String type, @Param("beginViewTime") Timestamp beginViewTime,
                             @Param("endViewTime") Timestamp endViewTime,@Param("viewTime") Integer viewTime);

    @Update("update viewlog set endViewTime=#{endViewTime},viewTime=#{viewTime} where (username=#{username} AND endViewTime is null)")
    Integer update(@Param("username") String username, @Param("endViewTime") Timestamp endViewTime,@Param("viewTime") Integer viewTime);

    @Select("select * from viewlog where username=#{username} AND endViewTime is null")
    List<ViewLog> selectByUsername(@Param("username") String username);

    @Select("select * from viewlog where username=#{username} AND endViewTime is not null")
    List<ViewLog> selectByUsernameNotNull(@Param("username") String username);
}
