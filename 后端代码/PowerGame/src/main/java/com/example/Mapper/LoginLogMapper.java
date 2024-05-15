package com.example.Mapper;

import com.example.pojo.LoginLog;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface LoginLogMapper {
    @Insert("insert into loginlog VALUES (#{username}, #{loginIp},#{loginTime},#{disLoginTime},#{type})")
    Integer insert(@Param("username") String username, @Param("loginIp") String loginIp,
                   @Param("loginTime") Timestamp loginTime,@Param("disLoginTime") Timestamp disLoginTime,
                   @Param("type") Integer type);

    @Update("update loginlog set disLoginTime=#{disLoginTime} where (username=#{username} AND disLoginTime is null)")
    Integer updateDisLoginTime(@Param("username") String username,@Param("disLoginTime") Timestamp disLoginTime);

    @Select("select * from loginlog")
    List<LoginLog> list();
    @Select("select * from loginlog limit 1000")
    List<LoginLog> listLimit();
}
