<template>
<div class="background">
    <div class="table">
        <div>
        <el-button type="primary" plain @click="back" style="width: 200px;margin-top:20px;">返回</el-button>
        </div>
        <div>
            <el-button type="primary" plain @click="back" class="bottom-right">返回</el-button>
        </div>
        <el-table :data="databaseLog" style="width: 100%;margin-top:20px;" >
        <el-table-column fixed prop="username" label="操作对象"></el-table-column>
        <el-table-column fixed prop="act" label="动作"></el-table-column>
        <el-table-column fixed prop="table" label="表"></el-table-column>
        <el-table-column fixed prop="formattedOrderTime" label="操作时间"></el-table-column>
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
        databaseLog : [],
    };
},
created(){
    axios.get('/databaseLog')
    .then((res)=>{this.databaseLog=res.data.data.map(log => ({
            ...log,
            formattedOrderTime: this.formatDateTime(log.time)
        })
        )});
},
methods:{
    back()
    {
        this.$router.push('/superManager')
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

<style scoped>
/* 右下角按钮样式 */
.bottom-right {
position: absolute;
bottom: 20px;
right: 20px;
}
.table{
    width: 75%;
    text-align: center;
    margin: 20px auto;
}
.background {
  /* background-image: url('./image/bg2.jpeg');
  background-size: cover;
  background-position: center center; */
  /* background-image: url('./image/bg2.jpeg');
  background-size: cover; 
  background-position: center; 
  background-attachment: fixed;  */

  background:url("./image/bg2.jpeg");
  width:100%;
  height:100%;
  background-attachment: fixed;
  background-size:100% 100%;
}
</style>