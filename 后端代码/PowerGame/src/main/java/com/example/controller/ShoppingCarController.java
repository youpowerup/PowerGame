package com.example.controller;

import com.example.Mapper.DatabaseLogMapper;
import com.example.Mapper.GameMapper;
import com.example.Mapper.ShoppingCarMapper;
import com.example.pojo.Game;
import com.example.pojo.Result;
import com.example.pojo.ShoppingCar;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class ShoppingCarController {
    @Autowired
    ShoppingCarMapper shoppingCarMapper;
    @Autowired
    GameMapper gameMapper;
    @Autowired
    DatabaseLogMapper databaseLogMapper;

    @PostMapping("/shoppingCar")
    public Result addToShoppingCar(@RequestBody ShoppingCar shoppingCar)
    {
        if(shoppingCar.getUsername().equals("stranger"))
        {
            return Result.error("请先登陆！");
        }
        Game gameCheck = gameMapper.getSingleGame(shoppingCar.getGameName());
        databaseLogMapper.insert(shoppingCar.getUsername(),"select","game",new Timestamp(System.currentTimeMillis()));
        if(gameCheck.getStock()==0)
        {
            return Result.error("此商品已经售罄！");
        }
        else {
            ShoppingCar back = shoppingCarMapper.check(shoppingCar.getUsername(),shoppingCar.getGameName());
            databaseLogMapper.insert(shoppingCar.getUsername(),"select","shoppingcar",new Timestamp(System.currentTimeMillis()));
            if(back==null)
            {
                shoppingCarMapper.insert(shoppingCar.getUsername(),shoppingCar.getGameName(),1);
                databaseLogMapper.insert(shoppingCar.getUsername(),"insert","shoppingcar",new Timestamp(System.currentTimeMillis()));
                return Result.success();
            }
            else {
                Integer amount = back.getAmount()+1;
                shoppingCarMapper.updateShoppingCar(shoppingCar.getUsername(),shoppingCar.getGameName(), amount);
                databaseLogMapper.insert(shoppingCar.getUsername(),"update","shoppingcar",new Timestamp(System.currentTimeMillis()));
                return Result.success();
            }
        }
    }
    @PostMapping("/deleteShoppingCarItem")
    public Result deleteShoppingCarItem(@RequestBody ShoppingCar shoppingCar)
    {
        ShoppingCar back = shoppingCarMapper.check(shoppingCar.getUsername(),shoppingCar.getGameName());
        databaseLogMapper.insert(shoppingCar.getUsername(),"select","shoppingcar",new Timestamp(System.currentTimeMillis()));
        if(back==null)
        {
            return Result.error("购物车中没有此条数据，请刷新页面检查！");
        }
        else{
            shoppingCarMapper.deleteShoppingCarByUsernameAndGameName(shoppingCar.getUsername(),shoppingCar.getGameName());
            databaseLogMapper.insert(shoppingCar.getUsername(),"delete","shoppingcar",new Timestamp(System.currentTimeMillis()));
            return Result.success();
        }
    }

    @PostMapping("/showShoppingCarByName")
    public Result showShoppingCarByName(@RequestBody ShoppingCar shoppingCar)
    {
        if(shoppingCar.getUsername().equals("stranger"))
        {
            return Result.error("请先登陆");
        }
        List<ShoppingCar> backList = shoppingCarMapper.list(shoppingCar.getUsername());
        databaseLogMapper.insert(shoppingCar.getUsername(),"select","shoppingcar",new Timestamp(System.currentTimeMillis()));
        if(backList.isEmpty())
        {
            return Result.error("购物车空空如也");
        }
        else{
            for(ShoppingCar item : backList)
            {
                Double unitPrice = gameMapper.getSingleGame(item.getGameName()).getPrice();
                Double sumPrice = unitPrice*(item.getAmount());
                item.setUnitPrice(unitPrice);
                item.setSumPrice(sumPrice);
            }
            return Result.success(backList);
        }
    }
    @PostMapping("/decrementShoppingCar")
    public Result decrementShoppingCar(@RequestBody ShoppingCar shoppingCar)
    {
        ShoppingCar back = shoppingCarMapper.check(shoppingCar.getUsername(),shoppingCar.getGameName());
        databaseLogMapper.insert(shoppingCar.getUsername(),"select","shoppingcar",new Timestamp(System.currentTimeMillis()));
        if(back==null)
        {
            return Result.error("购物车中没有此条数据，请刷新页面检查！");
        }
        else {
            if(back.getAmount()==1)
            {
                shoppingCarMapper.deleteShoppingCarByUsernameAndGameName(back.getUsername(), back.getGameName());
                databaseLogMapper.insert(shoppingCar.getUsername(),"delete","shoppingcar",new Timestamp(System.currentTimeMillis()));
                return Result.success();
            }
            else {
                Integer amount = back.getAmount()-1;
                shoppingCarMapper.updateShoppingCar(back.getUsername(), back.getGameName(), amount);
                databaseLogMapper.insert(shoppingCar.getUsername(),"update","shoppingcar",new Timestamp(System.currentTimeMillis()));
                return Result.success();
            }
        }
    }
}
