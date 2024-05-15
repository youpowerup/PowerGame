package com.example.controller;

import com.example.Mapper.DatabaseLogMapper;
import com.example.Mapper.LoginLogMapper;
import com.example.Mapper.UserMapper;
import com.example.pojo.Result;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    DatabaseLogMapper databaseLogMapper;
    @Autowired
    private LoginLogMapper loginLogMapper;

    @GetMapping("/allUser")
    public Result allUser()
    {
        List<User> userList=userMapper.listByType(1);
        return Result.success(userList);
    }
    @GetMapping("/allManager")
    public Result allManager()
    {
        List<User> userList=userMapper.listByType(2);
        return Result.success(userList);
    }

    //登录校验
    @PostMapping("/login")
    public Result login(@RequestBody User user)
    {
        User back = userMapper.selectByName(user.getName());
        databaseLogMapper.insert("admin","select","user",new Timestamp(System.currentTimeMillis()));
        if(back==null)
        {
            return Result.error("用户未注册！");
        }
        if(!back.getPassword().equals(user.getPassword()))
        {
            return Result.error("密码错误！");
        }
        if(back.getType()==0)
        {
            return Result.success();
        }
        if(back.getType()==1)
        {
            loginLogMapper.insert(user.getName(), request.getRemoteAddr(),new Timestamp(System.currentTimeMillis()),null,1);
            databaseLogMapper.insert("admin","insert","loginlog",new Timestamp(System.currentTimeMillis()));
            return Result.success();
        }
        if(back.getType()==2)
        {
            loginLogMapper.insert(user.getName(), request.getRemoteAddr(),new Timestamp(System.currentTimeMillis()),null,2);
            databaseLogMapper.insert("admin","insert","loginlog",new Timestamp(System.currentTimeMillis()));
            return Result.success(2,null);
        }
        if(back.getType()==3)
        {
            loginLogMapper.insert(user.getName(), request.getRemoteAddr(),new Timestamp(System.currentTimeMillis()),null,3);
            databaseLogMapper.insert("admin","insert","loginlog",new Timestamp(System.currentTimeMillis()));
            return Result.success(3,null);
        }
        return Result.error("服务器错误！");
    }

    @PostMapping("/disLogin")
    public Result disLogin(@RequestBody User user)
    {
        loginLogMapper.updateDisLoginTime(user.getName(), new Timestamp(System.currentTimeMillis()));
        databaseLogMapper.insert("admin","update","loginlog",new Timestamp(System.currentTimeMillis()));
        return Result.success();
    }
    @GetMapping("/loginLog")
    public Result loginLog()
    {
        return Result.success(loginLogMapper.listLimit());
    }


}
