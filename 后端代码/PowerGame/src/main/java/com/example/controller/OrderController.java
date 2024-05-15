package com.example.controller;

import com.example.Mapper.*;
import com.example.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ShoppingCarMapper shoppingCarMapper;
    @Autowired
    GameMapper gameMapper;
    @Autowired
    DatabaseLogMapper databaseLogMapper;
    @Autowired
    GameProfileMapper gameProfileMapper;

    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody Order order)
    {
        List<ShoppingCar> backShoppingCarList = shoppingCarMapper.list(order.getUsername());
        databaseLogMapper.insert(order.getUsername(),"select","order",new Timestamp(System.currentTimeMillis()));
        if(backShoppingCarList.isEmpty())
        {
            return Result.error("购物车为空！");
        }
        for(ShoppingCar item : backShoppingCarList)
        {
            Game backGame = gameMapper.getSingleGame(item.getGameName());
            databaseLogMapper.insert(order.getUsername(),"select","game",new Timestamp(System.currentTimeMillis()));
            if(backGame.getStock()<item.getAmount())
            {
                return Result.error("《"+backGame.getGameName()+"》的库存只有"+backGame.getStock().toString()+"件！");
            }
            if(backGame.getIsOnSale()==0)
            {
                return Result.error("《"+backGame.getGameName()+"》已经下架！");
            }
        }
        for(ShoppingCar item : backShoppingCarList)
        {
            //修改game表中的库存信息
            Game backGame = gameMapper.getSingleGame(item.getGameName());
            databaseLogMapper.insert(order.getUsername(),"select","game",new Timestamp(System.currentTimeMillis()));
            backGame.setStock(backGame.getStock()- item.getAmount());
            gameMapper.update(backGame.getGameName(),backGame.getGameDetail(),backGame.getPrice(),
                    backGame.getStock(),backGame.getType(),backGame.getOwnerName(),backGame.getImg());
            databaseLogMapper.insert(order.getUsername(),"update","game",new Timestamp(System.currentTimeMillis()));
            //在shoppingcar表中删除相关购物车条目
            shoppingCarMapper.deleteShoppingCarByUsernameAndGameName(item.getUsername(),item.getGameName());
            databaseLogMapper.insert(order.getUsername(),"delete","shoppingcar",new Timestamp(System.currentTimeMillis()));
            //在order表中新增操作条目
            orderMapper.insert(item.getUsername(), item.getGameName(), backGame.getPrice(),
                    item.getAmount(), (backGame.getPrice()* item.getAmount()), new Timestamp(System.currentTimeMillis()), backGame.getOwnerName());
            databaseLogMapper.insert(order.getUsername(),"insert","order",new Timestamp(System.currentTimeMillis()));
        }
        return Result.success();
    }

    @PostMapping("/showHistoryOrder")
    public Result showHistoryOrder(@RequestBody Order order)
    {
        List<Order> back = orderMapper.listByUsername(order.getUsername());
        databaseLogMapper.insert(order.getUsername(),"select","order",new Timestamp(System.currentTimeMillis()));
        return Result.success(back);
    }

    @PostMapping("/managerHistoryOrder")
    public Result getManagerHistoryOrder(@RequestBody Order order)
    {
        List<Order> back = orderMapper.listByOwnerName(order.getOwnerName());
        databaseLogMapper.insert(order.getOwnerName(),"select","order",new Timestamp(System.currentTimeMillis()));
        return Result.success(back);
    }

    @PostMapping("/managerSales")
    public Result managerSales(@RequestBody User user)
    {
        List<Game> backGame = gameMapper.getGameByOwnerName(user.getName());
        List<Sales> list = new ArrayList<>();
//        Map<String,Integer> amountMap = new HashMap<>();
//        Map<String,Double> sumPriceMap = new HashMap<>();
        for(Game game : backGame)
        {
            List<Order> backOrder = orderMapper.listByGameName(game.getGameName());
            Integer amount = 0;
            Double sumPrice = 0.0;
            for(Order order : backOrder)
            {
                amount=amount+order.getAmount();
                sumPrice=sumPrice+order.getSumPrice();
            }
//            amountMap.put(game.getGameName(),amount);
//            sumPriceMap.put(game.getGameName(),sumPrice);
            Sales sales = new Sales(game.getGameName(), amount,sumPrice,game.getType());
            list.add(sales);
        }
        return Result.success(list);
    }

    @GetMapping("/allSales")
    public Result allSales()
    {
        List<Sales> list = new ArrayList<>();
        List<String> typeList = gameMapper.listOfType();
        for(String type : typeList)
        {
            List<Game> backGame = gameMapper.getGameByType(type);
//        Map<String,Integer> amountMap = new HashMap<>();
//        Map<String,Double> sumPriceMap = new HashMap<>();
            for(Game game : backGame)
            {
                List<Order> backOrder = orderMapper.listByGameName(game.getGameName());
                Integer amount = 0;
                Double sumPrice = 0.0;
                for(Order order : backOrder)
                {
                    amount=amount+order.getAmount();
                    sumPrice=sumPrice+order.getSumPrice();
                }
//            amountMap.put(game.getGameName(),amount);
//            sumPriceMap.put(game.getGameName(),sumPrice);
                Sales sales = new Sales(game.getGameName(),amount,sumPrice,type);
                list.add(sales);
            }
        }
        gameProfileMapper.clear();
        for(Sales item : list)
        {
            gameProfileMapper.insert(item.getGameName(), item.getType(), item.getAmount(), item.getSumPrice());
        }
        return Result.success(list);
    }
}
