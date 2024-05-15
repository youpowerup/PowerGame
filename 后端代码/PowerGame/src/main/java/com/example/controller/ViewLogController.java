package com.example.controller;

import com.example.Mapper.ViewLogMapper;
import com.example.pojo.Result;
import com.example.pojo.ViewLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class ViewLogController {
    @Autowired
    ViewLogMapper viewLogMapper;
    @PostMapping("/OnView")
    public Result onView(@RequestBody ViewLog viewLog)
    {
        viewLogMapper.insertNewViewLog(viewLog.getUsername(), viewLog.getType(), new Timestamp(System.currentTimeMillis()),null,0);
        return Result.success();
    }

    @PostMapping("/OffView")
    public Result OffView(@RequestBody ViewLog viewLog)
    {
        List<ViewLog> back = viewLogMapper.selectByUsername(viewLog.getUsername());
        if(back.isEmpty())
        {
            return Result.success();
        }

        Timestamp begin =new Timestamp(0);
        Timestamp end=new Timestamp(System.currentTimeMillis());
        for(ViewLog item : back)
        {
            Integer viewTime = (int)(end.getTime()-item.getBeginViewTime().getTime())/1000;
            viewLogMapper.update(item.getUsername(), end,viewTime);
        }
        return Result.success();
    }
}
