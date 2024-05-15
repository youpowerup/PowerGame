<template>
    <div class="background">
        <div class="logout-container">
            <p class="title">确定要退出登录吗？{{ username }}</p>
            <div class="buttons">
                <el-button type="primary" plain size="medium" @click="disLogin">确定</el-button>
                <el-button type="danger" plain size="medium" @click="back">取消</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data(){
        return{
            username : sessionStorage.getItem('username'),
        }
    },
    methods:{
        disLogin()
        {
            axios.post('/disLogin', {username: sessionStorage.getItem('username')},{headers:{'Content-Type': 'application/json'}})
            sessionStorage.clear();
            sessionStorage.setItem('username','stranger'),
            this.$store.dispatch('setUsername',"stranger"),
            this.$router.push('/login')
        },
        back()
        {
            this.$router.push('/customer')
            location.reload();
        }
    }
}

</script>
<style>
.background{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('./image/bg3.jpeg'); /* 设置背景图片的路径 */
  background-size: cover; /* 让背景图片充满整个容器 */
  background-position: center center; /* 设置背景图片居中 */
}
.title {
  font-size: 40px;
  font-weight: bold;
}
.logout-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.buttons {
    display: flex;
}

.el-button {
  font-size: 20px; /* 放大按钮文字 */
  padding: 15px 30px; /* 放大按钮 */
}

</style>
