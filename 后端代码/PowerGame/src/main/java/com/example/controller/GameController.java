package com.example.controller;

import com.example.Mapper.DatabaseLogMapper;
import com.example.Mapper.GameMapper;
import com.example.pojo.DatabaseLog;
import com.example.pojo.Game;
import com.example.pojo.Result;
import com.example.pojo.ShoppingCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class GameController {
    @Autowired
    GameMapper gameMapper;
    @Autowired
    DatabaseLogMapper databaseLogMapper;
    @GetMapping("/customer")
    public Result allGame()
    {
        List<Game> list = gameMapper.listOnSale();
        return Result.success(list);
    }

    @PostMapping("/manager")
    public Result getGameByOwnerName(@RequestBody Game game)
    {
        List<Game> back = gameMapper.getGameByOwnerName(game.getOwnerName());
        return Result.success(back);
    }

    @PostMapping("/managerOnSaleGame")
    public Result managerDeleteGame(@RequestBody Game game)
    {
        Game back = gameMapper.getSingleGame(game.getGameName());
        if(back==null)
        {
            return Result.error("库中没有此游戏，请刷新页面检查！");
        }
        else {
            gameMapper.updateIsOnSale(back.getGameName(), 1);
            return Result.success();
        }
    }

    @PostMapping("/managerOffSaleGame")
    public Result managerOffSaleGame(@RequestBody Game game)
    {
        Game back = gameMapper.getSingleGame(game.getGameName());
        if(back==null)
        {
            return Result.error("库中没有此游戏，请刷新页面检查！");
        }
        else {
            gameMapper.updateIsOnSale(back.getGameName(), 0);
            databaseLogMapper.insert(game.getOwnerName(),"update","game",new Timestamp(System.currentTimeMillis()));
            return Result.success();
        }
    }

    @PostMapping("/managerSetGameDetail")
    public Result managerSetGameName(@RequestBody Game game)
    {
        Game back = gameMapper.getSingleGame(game.getGameName());
        if(back==null)
        {
            return Result.error("库中没有此游戏，请刷新页面检查！");
        }
        else {
            gameMapper.update(game.getGameName(),game.getGameDetail(),game.getPrice(),game.getStock(),game.getType(),game.getOwnerName(),game.getImg());
            databaseLogMapper.insert(game.getOwnerName(),"update","game",new Timestamp(System.currentTimeMillis()));
            return Result.success();
        }
    }

    @PostMapping("/managerAddNewGame")
    public Result managerAddNewGame(@RequestBody Game game)
    {
        Game back = gameMapper.getSingleGame(game.getGameName());
        if(back!=null)
        {
            return Result.error("已经存在此游戏！");
        }
        else{
            String gameName = game.getGameName();
            String gameDetail = game.getGameDetail();
            Double price = game.getPrice();
            Integer stock = game.getStock();
            String type  = game.getType();
            String ownerName = game.getOwnerName();
            gameMapper.insertNewGame(gameName,gameDetail,price,stock,type,ownerName,null,0);
            databaseLogMapper.insert(game.getOwnerName(),"insert","game",new Timestamp(System.currentTimeMillis()));
            return Result.success();
        }
    }
}
