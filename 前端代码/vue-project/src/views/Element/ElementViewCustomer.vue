<template>
  <div>
    <div class="background">
      <div style="background-color: #3e3e3e;width: 100%;height: 40px;margin: 0 auto;display: flex;">
        <div style="display: flex;flex: 1;">
          <div class="active"></div>
          <div class="active"></div>
          <div class="active"></div>
          <div class="active" @click="historyOrder">历史订单</div>
        </div>
        <div style="width: 258px;text-align: right;display: flex;">
          <div class="active">你好：{{ username }}</div>
          <div class="active" @click="shoppingCar">购物车</div>
          <div class="active" @click="disLogin">退出登录</div>
        </div>
      </div>

      <!-- <div class="index"> 
          <div>   
              <el-carousel :interval="4000" type="card" height="220px">
                  <el-carousel-item v-for="item in imgList" :key="item.id">
                      <img style="object-fit: scale-down;width:100%;height:100%;" :src="item.idView" class="image">
                  </el-carousel-item>
              </el-carousel>
          </div>
      </div> -->

      <div class="table">
        <div style="margin-bottom:20px;">
          <el-card>
            <div slot="header" class="totalTitle">欢迎来到商城，看看有哪些你感兴趣的东西</div>
          </el-card>
        </div>
        <div  style="margin-bottom:10px;">
          <el-card>
            <div slot="header" class="subTitle">为你推荐</div>
          </el-card>
        </div>
        <div class="card-container">
          <el-card v-for="(game, index) in commendGame" :key="index" class="game-card">
            <template #header>
              <b>{{ game.gameName }}</b>
            </template>
            <img src="./image/head.jpg" fit="cover" class="card-image"/>
            <p>简介：{{ game.gameDetail }}</p>
            <!-- <p>价格：{{ game.price }}</p>
            <p>库存：{{ game.stock }}</p>
            <p>类型：{{ game.type }}</p>
            <p>作者：{{ game.ownerName }}</p> -->
            <div class="button-container">
              <el-button @click="showGameDetail(game)">详情</el-button>
            </div>
          </el-card>
        </div>
        <div style="margin-top:20px;margin-bottom:10px;">
          <el-card>
            <div slot="header" class="subTitle">全部游戏</div>
          </el-card>
        </div>



        <div class="card-container">
          <el-card v-for="(game, index) in gameData" :key="index" class="game-card">
            <template #header>
              <b>{{ game.gameName }}</b>
            </template>
            <img src="./image/head.jpg" fit="cover" class="card-image"/>
            <p>简介：{{ game.gameDetail }}</p>
            <!-- <p>价格：{{ game.price }}</p>
            <p>库存：{{ game.stock }}</p>
            <p>类型：{{ game.type }}</p>
            <p>作者：{{ game.ownerName }}</p> -->
            <div class="button-container">
              <el-button @click="showGameDetail(game)">详情</el-button>
            </div>
          </el-card>
        </div>
      </div>



      <!-- 游戏总表 -->
      <!-- <div>
        <el-table :data="gameData" style="width: 100%" >
          <el-table-column fixed prop="gameName" label="游戏名称"></el-table-column>
          <el-table-column fixed prop="gameDetail" label="游戏详情"></el-table-column>
          <el-table-column fixed prop="price" label="价格"></el-table-column>
          <el-table-column fixed prop="stock" label="库存"></el-table-column>
          <el-table-column fixed prop="type" label="类型"></el-table-column>
          <el-table-column fixed prop="ownerName" label="所有者"></el-table-column>
          <el-table-column fixed prop="img" label="游戏封面"></el-table-column>
        </el-table>
      </div> -->






      <!-- 底部 -->
      <div style="font-size: 14px;">
        <div style="width: 60%;margin: 0 auto;margin-bottom: 30px;">
          <div style="display: flex;">
            <div style="flex: 1;">
              <div class="foot-title">帮助中心</div>
              <div class="foot-item">账户管理</div>
              <div class="foot-item">购物指南</div>
              <div class="foot-item">订单操作</div>
            </div>
            <div style="flex: 1;">
              <div class="foot-title">服务支持</div>
              <div class="foot-item">账户管理</div>
              <div class="foot-item">购物指南</div>
              <div class="foot-item">订单操作</div>
            </div>
            <div style="flex: 1;">
              <div class="foot-title">线下门店</div>
              <div class="foot-item">账户管理</div>
              <div class="foot-item">购物指南</div>
              <div class="foot-item">订单操作</div>
            </div>
            <div style="flex: 1;">
              <div class="foot-title">关于商城</div>
              <div class="foot-item">账户管理</div>
              <div class="foot-item">购物指南</div>
              <div class="foot-item">订单操作</div>
            </div>
            <div style="flex: 1;">
              <div class="foot-title">关于我们</div>
              <div class="foot-item">账户管理</div>
              <div class="foot-item">购物指南</div>
              <div class="foot-item">订单操作</div>
            </div>
    
          </div>
        </div>
        <div style="background-color: #3e3e3e;height: 40px;text-align: center;line-height: 40px;color: #afafaf;">
          申明：本网页仅供参考，请勿付款
        </div>
      </div>
    </div>

  </div>
</template>








<script>
import axios from 'axios';

  export default {
    data() {
      return {
        username:sessionStorage.getItem("username"),
        gameData : [],
        commendGame:[]
      };
    },
    created(){
      axios.get('/customer').then(res => {console.log('res:',res),this.gameData=res.data.data})
      if(this.username !== 'stranger'){
        axios.post('/commend', {username:this.username},
            {headers:{'Content-Type': 'application/json'}}).then(res => {console.log('res:',res),this.commendGame=res.data.data});
      }
    },
    watch: {
      $route(to, from) {
        if (from.path === '/customer' && to.path === '/login'){
            window.location.reload(); //监测到路由发生跳转时刷新一次页面
        }
      }
    },
    methods: {
      disLogin(){
        this.$router.push('/disLogin');
      },
      showGameDetail(game){
        axios.post('/OnView', {username:this.username,type:game.type},
            {headers:{'Content-Type': 'application/json'}});
        this.$router.push({ path: '/gameDetail', query: { game: JSON.stringify(game) } });
      },
      shoppingCar()
      {
        if(this.username=='stranger')
        {
          alert('请先登陆！')
        }
        else{
          this.$router.push('/shoppingCar')
          // location.reload();
        }
      },
      historyOrder()
      {
        if(this.username=='stranger')
        {
          alert('请先登陆！')
        }
        else{
          this.$router.push('/historyOrder')
          // location.reload();
        }
      }
    }
  }
</script>
































<style>
.table{
    width: 75%;
    text-align: center;
    margin: 20px auto;
}
.background {
  background:url("./image/bg4.jpeg");
  width:100%;
  height:100%;
  background-size:100% 100%;
  /* position:fixed; */
  background-attachment: fixed;
}
.active{
    line-height: 40px;
    color: rgb(128, 128, 128);
    margin-right: 20px;
    font-size: 12px;
  }
  .active:hover{
    cursor: pointer;
    color: #FFFFFF;
  }
  .shop-car{
    text-align: center;
    cursor: pointer;
    width: 100px;
    line-height: 40px;
    height: 40px;
    display: inline-block;
    background-color: #ffffff;
  }
  .shop-car:hover{
    background-color: #00FFFF;
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .foot-title{
    height: 40px;
    line-height: 40px;
  }
  .foot-item{
    height: 25px;
    line-height: 25px;
    color: rgb(128,128, 128);
  }
  .foot-item:hover{
    cursor: pointer;
    color: #e37a2f;
  }



  /* 卡片样式 */
  .card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  }

  .game-card {
    width: calc(25% - 20px); /* 设置卡片宽度为四分之一，并减去外边距 */
    margin-bottom: 20px; /* 设置卡片之间的垂直间距 */
  }

  .button-container {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    height: 100%; /* 将容器高度设置为卡片的高度 */
  }
  



  /* 走马灯css */
  .index{
    background: #ffffff;
    /* //c8cfd8; */
    width: 80%;
    text-align: center;
    margin-left: 142px;
    width: 80.6%;
    
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.5;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  /* 总标题 */
  .totalTitle {
  font-size: 40px;
  font-weight: bold;
  text-align: center; /* 让标题居中显示 */
  color: black(43, 43, 43, 0.518); /* 设置标题字体颜色为黄色 */
  }

  /* 副标题 */
  .subTitle {
  font-size: 24px;
  font-weight: bold;
  /* text-align: center; 让标题居中显示 */
  color: rgba(255, 0, 0, 0.419); /* 设置标题字体颜色为黄色 */
  }
  
</style>