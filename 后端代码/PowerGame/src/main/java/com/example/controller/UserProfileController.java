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
public class UserProfileController {
    @Autowired
    UserProfileMapper userProfileMapper;
    @Autowired
    DatabaseLogMapper databaseLogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ViewLogMapper viewLogMapper;
    @Autowired
    GameMapper gameMapper;
    @Autowired
    GameProfileMapper gameProfileMapper;

    @GetMapping("/getCommendGame")
    public Result getCommendGame()
    {
        List<UserProfile> back = new ArrayList<>();
        back.add(userProfileMapper.getUserProfileByUsername("admin"));
        return Result.success(back);
//        UserProfile admin = userProfileMapper.getUserProfileByUsername("admin");
//        UserProfile back = new UserProfile();
//        if(admin.getMostLove().equals("all")||admin.getMostLove().equals(""))
//        {
//            back.setMostLove("all");
//        }
//        else{
//            back.setMostLove(admin.getMostLove());
//        }
//        if(admin.getSecondLove().equals("all")||admin.getSecondLove().equals(""))
//        {
//            back.setSecondLove("all");
//        }
//        else{
//            back.setSecondLove(admin.getSecondLove());
//        }
//        return Result.success(back);
    }
    @PostMapping("/setCommendGame")
    public Result setCommendGame(@RequestBody UserProfile userProfile)
    {
        Game back = gameMapper.getSingleGame(userProfile.getMostLove());
        if(back==null&&(!userProfile.getMostLove().equals("all"))&&(!userProfile.getMostLove().equals("")))
        {
            return Result.error("游戏《"+userProfile.getMostLove()+"》不存在！");
        }
        back = gameMapper.getSingleGame(userProfile.getSecondLove());
        if(back==null&&(!userProfile.getMostLove().equals("all"))&&(!userProfile.getMostLove().equals("")))
        {
            return Result.error("游戏《"+userProfile.getSecondLove()+"》不存在！");
        }
        userProfileMapper.updateCommendGame("admin",userProfile.getMostLove(),userProfile.getSecondLove());
        return Result.success();
    }
    @GetMapping("/setUserProfile")
    public Result setCommend()
    {
        UserProfile admin = userProfileMapper.getUserProfileByUsername("admin");
        userProfileMapper.clear();
        userProfileMapper.insert(admin.getUsername(), admin.getAmount(),admin.getSumPrice(), admin.getMostLove(), admin.getSecondLove());
        databaseLogMapper.insert("admin","delete","userprofile",new Timestamp(System.currentTimeMillis()));
        List<User> userList = userMapper.listByType(1);
        List<String> gameTypeList=gameMapper.listOfType();

        for(User user : userList)
        {
            Integer amount=0;
            Double sumPrice = 0.0;
            String mostLove=null;
            String secondLove=null;
            Map<String,Integer> gameTypeMap=new HashMap<>();
            for(String type : gameTypeList)
            {
                gameTypeMap.put(type,0);
            }
            List<Order> orderList = orderMapper.listByUsername(user.getName());
            for(Order order : orderList)
            {
                amount=amount+order.getAmount();
                sumPrice=sumPrice+order.getSumPrice();
                String gameType = gameMapper.getTypeByGameName(order.getGameName());
                Integer temp = gameTypeMap.get(gameType)+order.getAmount()*100;
                gameTypeMap.put(gameType,temp);
            }
            List<ViewLog> viewLogList = viewLogMapper.selectByUsernameNotNull(user.getName());
            if(!viewLogList.isEmpty())
            {
                for(ViewLog viewLog : viewLogList)
                {
                    Integer temp = gameTypeMap.get(viewLog.getType())+viewLog.getViewTime();
                    gameTypeMap.put(viewLog.getType(),temp);
                }
            }
            Integer max=0;
            Integer second=0;
            for(String type : gameTypeList)
            {
                if(gameTypeMap.get(type)>max)
                {
                    max=gameTypeMap.get(type);
                    mostLove=type;
                }
                else
                {
                    if(gameTypeMap.get(type)>second)
                    {
                        second=gameTypeMap.get(type);
                        secondLove=type;
                    }
                }
            }
            if(mostLove==null){mostLove="all";}
            if(secondLove==null){secondLove="all";}
            userProfileMapper.insert(user.getName(),amount,sumPrice,mostLove,secondLove);
        }
        return Result.success();
    }
    @PostMapping("/commend")
    public Result commend(@RequestBody UserProfile userProfile)
    {
        List<Game> list = new ArrayList<>();
        String mostLoveType = userProfileMapper.getUserProfileByUsername(userProfile.getUsername()).getMostLove();
        String secondLoveType = userProfileMapper.getUserProfileByUsername(userProfile.getUsername()).getSecondLove();
        Map<String,String> ownGameMap = new HashMap<>();
        List<Order> ownGameList = orderMapper.listByUsername(userProfile.getUsername());
        for(Order item : ownGameList)
        {
            if (ownGameMap.containsKey(item.getGameName()))
            {
                continue;
            }
            else {
                ownGameMap.put(item.getGameName(),item.getOwnerName());
            }
        }
        if(mostLoveType.equals("all"))
        {
            if(userProfileMapper.getUserProfileByUsername("admin").getMostLove().equals("all")||userProfileMapper.getUserProfileByUsername("admin").getMostLove().equals(""))
            {
                List<GameProfile> gameProfileList = gameProfileMapper.getOrderList();
                for(GameProfile item : gameProfileList)
                {
                    if(ownGameMap.containsKey(item.getGameName()))
                    {
                        continue;
                    }
                    else {
                        list.add(gameMapper.getSingleGame(item.getGameName()));
                        break;
                    }
                }
                if(list.size()==0)
                {
                    list.add(new Game("公子大气","真没想到还有人把这的游戏买完了,这系统俩彩蛋给你找到一个",
                            999.9,0,"大彩蛋","谢志程",null,0));
                }
            }
            else {
                list.add(gameMapper.getSingleGame(userProfileMapper.getUserProfileByUsername("admin").getMostLove()));
            }
        }
        else{
            List<GameProfile> gameProfileList = gameProfileMapper.getOrderListByType(mostLoveType);
            for(GameProfile item : gameProfileList)
            {
                if(ownGameMap.containsKey(item.getGameName()))
                {
                    continue;
                }
                else {
                    list.add(gameMapper.getSingleGame(item.getGameName()));
                    break;
                }
            }
            if(list.size()==0)
            {
                list.add(new Game("好一个土豪！",mostLoveType+"游戏给你买完了,这系统俩彩蛋给你找到一个",
                        999.9,0,"小彩蛋","谢志程",null,0));
            }
        }
        if(secondLoveType.equals("all"))
        {
            if(userProfileMapper.getUserProfileByUsername("admin").getSecondLove().equals("all")||userProfileMapper.getUserProfileByUsername("admin").getSecondLove().equals(""))
            {
                List<GameProfile> gameProfileList = gameProfileMapper.getOrderList();
                for(GameProfile item : gameProfileList)
                {
                    if(ownGameMap.containsKey(item.getGameName()))
                    {
                        continue;
                    }
                    else {
                        list.add(gameMapper.getSingleGame(item.getGameName()));
                        break;
                    }
                }
                if(list.size()==1)
                {
                    list.add(new Game("公子大气","真没想到还有人把这的游戏买完了,这系统俩彩蛋给你找到一个",
                            999.9,0,"大彩蛋","谢志程",null,0));
                }
            }
            else {
                list.add(gameMapper.getSingleGame(userProfileMapper.getUserProfileByUsername("admin").getSecondLove()));
            }
        }
        else{
            List<GameProfile> gameProfileList = gameProfileMapper.getOrderListByType(secondLoveType);
            for(GameProfile item : gameProfileList)
            {
                if(ownGameMap.containsKey(item.getGameName()))
                {
                    continue;
                }
                else {
                    list.add(gameMapper.getSingleGame(item.getGameName()));
                    break;
                }
            }
            if(list.size()==1)
            {
                list.add(new Game("好一个土豪！",secondLoveType+"游戏给你买完了,这系统俩彩蛋给你找到一个",
                        999.9,0,"小彩蛋","谢志程",null,0));
            }
        }
        return Result.success(list);
    }
    @GetMapping("/saleForecast")
    public Result saleForecast()
    {
        List<UserProfile> list = new ArrayList<>();
        List<GameProfile> backGameList = gameProfileMapper.getOrderList();
        list.add(new UserProfile("admin",0,0.0,
                backGameList.get(0).getGameName(),backGameList.get(1).getGameName()));
        return Result.success(list);
    }
    @GetMapping("/databaseLog")
    public Result databaseLog()
    {
        return Result.success(databaseLogMapper.listLimit());
    }
}
