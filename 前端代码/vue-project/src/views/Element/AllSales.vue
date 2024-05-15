<template>
    <div class="background">
      <!-- 业绩总表 -->
      <div class="table">
        <div>
          <el-button type="primary" plain @click="back" style="width: 200px;margin-top:20px;">返回</el-button>
        </div>
        <el-table :data="allSales" style="width: 100%;margin-top:20px;margin-bottom:20px" >
          <el-table-column fixed prop="gameName" label="游戏名称"></el-table-column>
          <el-table-column fixed prop="type" label="游戏类型"></el-table-column>
          <el-table-column fixed prop="amount" label="销售总数量"></el-table-column>
          <el-table-column fixed prop="sumPrice" label="销售总额"></el-table-column>
        </el-table>
      </div>
    </div>
</template>

<script>
import axios from 'axios';
export default{
    data(){
        return {
            username:sessionStorage.getItem("username"),
            allSales : [],
        };
    },
    created(){
      axios.get('/allSales').then((res)=>{this.allSales=res.data.data});
    },
    methods:{
        back()
        {
            this.$router.push('/superManager')
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
  background:url("./image/bg2.jpeg");
  width:100%;
  height:100%;
  background-attachment: fixed;
  background-size:100%;
}

</style>