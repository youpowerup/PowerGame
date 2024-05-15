<template>
    <!-- 业绩总表 -->
    <div>
      <div class="table">
        <div>
          <el-button type="primary" plain @click="back">返回</el-button>
        </div>
        <el-table :data="managerSales" style="width: 100%" >
          <el-table-column fixed prop="gameName" label="游戏名称"></el-table-column>
          <el-table-column fixed prop="tpye" label="游戏类型"></el-table-column>
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
            managerSales : [],
        };
    },
    created(){
      axios.post('/managerSales', {username:this.username},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{this.managerSales=res.data.data});
    },
    methods:{
        back()
        {
            this.$router.push('/managerPage')
        }
    }
}
</script>
<style>
.table{
    width: 75%;
    text-align: center;
    margin: 0 auto;
}
</style>