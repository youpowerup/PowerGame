package com.example.controller;

import com.example.Mapper.DatabaseLogMapper;
import com.example.Mapper.UserMapper;
import com.example.pojo.Result;
import com.example.pojo.User;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    DatabaseLogMapper databaseLogMapper;

    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        User back = userMapper.selectByName(user.getName());
        databaseLogMapper.insert(user.getName(),"select","user",new Timestamp(System.currentTimeMillis()));
        if(back!=null)
        {
            return Result.error("用户已存在！");
        }
        userMapper.insert("用户没有留下电话",user.getName(), user.getPassword(), 1);
        databaseLogMapper.insert(user.getName(),"insert","user",new Timestamp(System.currentTimeMillis()));
        return Result.success();
    }
    @PostMapping("/managerRegister")
    public  Result managerRegister(@RequestBody User user)
    {
        User back = userMapper.selectByName(user.getName());
        databaseLogMapper.insert(user.getName(),"select","user",new Timestamp(System.currentTimeMillis()));
        if(back!=null)
        {
            return Result.error("用户已存在！");
        }
        userMapper.insert("用户没有留下电话",user.getName(), user.getPassword(), 2);
        databaseLogMapper.insert(user.getName(),"insert","user",new Timestamp(System.currentTimeMillis()));
        return Result.success();
    }
    @PostMapping("/resetPassword")
    private Result resetPassword(@RequestBody User user)
    {
        User back = userMapper.selectByName(user.getName());
        databaseLogMapper.insert(user.getName(),"select","user",new Timestamp(System.currentTimeMillis()));
        if(back==null)
        {
            return Result.error("用户不存在！");
        }
        userMapper.resetPassword(user.getName(), user.getPassword());
        databaseLogMapper.insert(user.getName(),"update","user",new Timestamp(System.currentTimeMillis()));
        return Result.success();
    }
}
