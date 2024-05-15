<template>
    <!-- 订单总表 -->
    <div>
        <div class="table">
            <div>
                <el-button type="primary" plain @click="back">返回</el-button>
            </div>
            <el-table :data="historyOrder" style="width: 100%" >
                <el-table-column fixed prop="gameName" label="游戏名称"></el-table-column>
                <el-table-column fixed prop="unitPrice" label="单价"></el-table-column>
                <el-table-column fixed prop="amount" label="数量"></el-table-column>
                <el-table-column fixed prop="sumPrice" label="总价"></el-table-column>
                <el-table-column fixed prop="formattedOrderTime" label="订单完成时间"></el-table-column>
                <el-table-column fixed prop="ownerName" label="游戏作者"></el-table-column>
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
            historyOrder : [],
        };
    },
    created(){
      axios.post('/managerHistoryOrder', {ownerName:this.username},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{this.historyOrder=res.data.data.map(order => ({
                ...order,
                formattedOrderTime: this.formatDateTime(order.orderTime)
            })
            )});
    },
    methods:{
        back()
        {
            this.$router.push('/managerPage')
        },
        formatDateTime(timestamp) {
            let date = new Date(timestamp);
            let year = date.getFullYear();
            let month = (date.getMonth() + 1).toString().padStart(2, '0');
            let day = date.getDate().toString().padStart(2, '0');
            let hours = date.getHours().toString().padStart(2, '0');
            let minutes = date.getMinutes().toString().padStart(2, '0');
            let seconds = date.getSeconds().toString().padStart(2, '0');
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
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
</style>