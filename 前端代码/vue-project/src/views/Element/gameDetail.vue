<template>
    <div class="background">
        <div>
            <div class="card-container">
                <template>
                    <el-card style="margin-top: 100px; margin-bottom: 100px;margin-left:650px">
                        <template #header><b style="font-size: 24px;">{{ gameData.gameName }}</b></template>
                        <img src="./image/head.jpg" style="width: 100%; max-width: 400px; height: auto; margin-top: 20px;" />
                        <div style="font-size: 18px;">游戏详情: {{ gameData.gameDetail }}</div>
                        <div style="font-size: 18px;">价格: {{ gameData.price }}</div>
                        <div style="font-size: 18px;">库存: {{ gameData.stock }}</div>
                        <div style="font-size: 18px;">类型: {{ gameData.type }}</div>
                        <div style="font-size: 18px;">所有者名称: {{ gameData.ownerName }}</div>
                    </el-card>
                </template>
            </div>

            <el-button type="primary" plain @click="back" class="backBotton">返回</el-button>
            <el-button type="success" plain @click="addToShoppingCar" class="bottom-right">加入购物车</el-button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data(){
        return{
            username : sessionStorage.getItem('username'),
            gameData:{}
        }
    },
    created() {
        this.gameData = JSON.parse(this.$route.query.game);
    },
    methods:{
        back()
        {
            axios.post('/OffView', {username:this.username},
            {headers:{'Content-Type': 'application/json'}});
            this.$router.push('/customer');
            // location.reload();
        },
        addToShoppingCar() {
            // 处理加入购物车的逻辑
            // 这里可以弹出提示
            axios.post('/shoppingCar', {username:this.username,gameName:this.gameData.gameName,unitPrice:this.gameData.price},
                        {headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
                        .then((successResponse) => {
                            if(successResponse.data.code===1)
                            {
                                this.$message({message: '成功将一个商品加入购物车，请到购物车中查看',type: 'success'})
                            }
                            else{
                                alert(successResponse.data.msg);
                            }
                        }
            );
        }
    }
}

</script>


<style scoped>
/* 右下角按钮样式 */
.bottom-right {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 30%;
}
.backBotton{
    position: fixed;
    bottom: 20px;
    left: 20px;
    width: 30%;
}
.background{
    background:url("./image/bg5.jpeg");
    width:100%;
    height:100%;
    background-size:100% 100%;
    position:fixed;
    /* background-attachment: fixed; */
}
/* 卡片样式 */
.card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    width: 100%; /* 宽度设置为屏幕宽度 */
    max-width: 980px; /* 最大宽度为 980px */
}


</style>