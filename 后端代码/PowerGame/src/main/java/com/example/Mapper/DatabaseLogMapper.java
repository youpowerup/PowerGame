package com.example.Mapper;

import com.example.pojo.DatabaseLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface DatabaseLogMapper {
    @Select("select * from databaselog")
    List<DatabaseLog> list();
    @Select("select * from databaselog limit 1000")
    List<DatabaseLog> listLimit();
    @Select("select * from databaselog where username=#{username}")
    List<DatabaseLog> listByUsername();
    @Insert("insert into databaselog VALUES (#{username},#{Act},#{Table},#{Time})")
    Integer insert(@Param("username") String username, @Param("Act") String Act, @Param("Table") String Table,@Param("Time") Timestamp Time);
}
