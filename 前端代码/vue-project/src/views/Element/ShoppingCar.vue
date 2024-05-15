<template>
    <!-- 购物车总表 -->
    <div class="table">
      <el-table :data="shoppingCar" style="width: 100%" >
        <el-table-column fixed prop="gameName" label="游戏名称"></el-table-column>
        <el-table-column fixed prop="unitPrice" label="单价"></el-table-column>
        <el-table-column fixed prop="amount" label="数量"></el-table-column>
        <el-table-column fixed prop="sumPrice" label="总价"></el-table-column>
        <el-table-column fixed label="操作">
            <template slot-scope="scope">
            <el-button @click="decrement(scope.row)">-</el-button>
            <el-button @click="increment(scope.row)">+</el-button>
            <el-button type="danger" @click="remove(scope.row)">删除</el-button>
            </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" plain @click="back" class="backBotton">返回</el-button>
      <el-button type="success" plain @click="submitOrder" class="bottom-right">结算</el-button>
    </div>
</template>

<script>
import axios from 'axios';
export default{
    data(){
        return {
            username:sessionStorage.getItem("username"),
            shoppingCar : [],
            row:null
        };
    },
    created(){
      axios.post('/showShoppingCarByName', {username:this.username},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{console.log('res:',res),this.shoppingCar=res.data.data,console.log('shoppingCar:',this.shoppingCar)});
    },
    methods:{
        back()
        {
            this.$router.push('/customer')
        },
        submitOrder()
        {
            axios.post('/submitOrder', {username:this.username},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
            .then((successResponse) => {//回调函数当post成功后执行
                          if (successResponse.data.code === 1) {//如果后端返回的状态码是1
                            alert('成功清空购物车！感谢您的购买'),
                            this.$router.push('/customer')
                          }
                          if (successResponse.data.code === 0 ) {
                            alert(successResponse.data.msg);
                          }
                        })
        },
        increment(row) {
        // 增加数量的逻辑
        axios.post('/shoppingCar', {username:this.username,gameName:row.gameName,unitPrice:row.price},
                        {headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
                        .then((successResponse) => {
                            if(successResponse.data.code===1)
                            {
                              location.reload()
                            }
                            else{
                                alert(successResponse.data.msg);
                            }
                        }
            );
        },
        decrement(row) {
        // 减少数量的逻辑
        axios.post('/decrementShoppingCar', {username:this.username,gameName:row.gameName},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
            .then((successResponse) => {//回调函数当post成功后执行s
                          if (successResponse.data.code === 1) {//如果后端返回的状态码是1
                            location.reload()
                          }
                          if (successResponse.data.code === 0 ) {
                            alert(successResponse.data.msg);
                          }
                        })
        },
        remove(row) {
        // 删除商品的逻辑
        axios.post('/deleteShoppingCarItem', {username:this.username,gameName:row.gameName},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
            .then((successResponse) => {//回调函数当post成功后执行s
                          if (successResponse.data.code === 1) {//如果后端返回的状态码是1
                            location.reload()
                          }
                          if (successResponse.data.code === 0 ) {
                            alert(successResponse.data.msg);
                          }
                        })
        }
    }
}
</script>

<style scoped>
/* 右下角按钮样式 */
.bottom-right {
  position: fixed;
  bottom: 200px;
  right: 20px;
  width: 15%;
}
.backBotton{
    position: fixed;
    bottom: 200px;
    left: 20px;
    width: 15%;
}
.table{
    width: 75%;
    text-align: center;
    margin: 0 auto;
}
</style>