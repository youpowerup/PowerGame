<template>
<div class="background">
    <div style="background-color: #3e3e3e;width: 100%;height: 40px;margin: 0 auto;display: flex;">
    <div style="display: flex;flex: 1;">
        <div class="active">  </div>
        <div class="active" @click="allSales">销售报表</div>
        <div class="active" @click="databaseLog">数据库操作记录</div>
        <div class="active" @click="loginLog">账号登陆记录</div>
        <div class="active" @click="showAddNewManagerForm">增加游戏作者</div>
    </div>
        <div style="width: 258px;text-align: right;display: flex;">
            <div class="active">你好：{{ username }}</div>
            <div class="active" @click="disLogin">退出登录</div>
        </div>
    </div>

    <div class="table">
        
        <el-dialog :visible.sync="addNewManagerDialogVisible" :title="'新作者的信息'">
        <el-form :model="editForm" label-width="80px">
            <el-form-item label="用户名" prop="username">
            <el-input v-model="editForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
            <el-input v-model="editForm.password"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="addNewManagerDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitAddNewManager">确认</el-button>
        </div>
        </el-dialog>

        <div>
        <el-card>
            <div slot="header" class="totalTitle">游戏作者</div>
        </el-card>
        </div>

        <!-- 修改密码表单 -->
        <el-dialog :visible.sync="resetPasswordVisible" :title="editForm.username">
            <el-form :model="editForm" label-width="80px">
            <el-form-item label="新密码" prop="password">
                <el-input v-model="editForm.password"></el-input>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
            <el-button @click="resetPasswordVisible = false">取消</el-button>
            <el-button type="primary" @click="submitResetPasswordEdit">确认</el-button>
            </div>
        </el-dialog>

        <!-- 作者总表 -->
        <div>
        <el-table :data="manager" style="width: 100%;margin-bottom:20px;" >
            <el-table-column fixed prop="username" label="账户名"></el-table-column>
            <el-table-column fixed prop="password" label="密码"></el-table-column>
            <el-table-column fixed label="操作">
            <template slot-scope="scope">
                <div class="button-container">
                <el-button type="primary" @click="showResetPasswordForm(scope.row)">修改密码</el-button>
                </div>
            </template>
            </el-table-column>
        </el-table>
        </div>
        <div>
        <el-card>
            <div slot="header" class="totalTitle">普通用户</div>
        </el-card>
        </div>
        <!-- 用户总表 -->
        <div>
        <el-table :data="user" style="width: 100%;margin-bottom:20px;" >
            <el-table-column fixed prop="username" label="账户名"></el-table-column>
            <el-table-column fixed prop="password" label="密码"></el-table-column>
            <el-table-column fixed label="操作">
            <template slot-scope="scope">
                <div class="button-container">
                <el-button type="primary" @click="resetManagerPassword(scope.row)">修改密码</el-button>
                </div>
            </template>
            </el-table-column>
        </el-table>
        </div>

        <div>
        <el-card>
            <div slot="header" class="totalTitle">系统默认推荐游戏</div>
        </el-card>
        </div>


        <!-- 系统默认推荐游戏总表 -->
        <div>
        <el-table :data="commendGame" style="width: 100%;margin-bottom:20px;" >
            <el-table-column fixed prop="mostLove" label="游戏名"></el-table-column>
            <el-table-column fixed prop="secondLove" label="游戏名"></el-table-column>
            <el-table-column fixed label="操作">
            <template slot-scope="scope">
                <div class="button-container">
                <el-button type="primary" @click="resetCommendGame(scope.row)">修改</el-button>
                </div>
            </template>
            </el-table-column>
        </el-table>
        </div>

        <!-- 修改推荐游戏表单 -->
        <el-dialog :visible.sync="resetCommendGameVisible" :title="'请填写游戏名或填写all来由算法推荐'">
            <el-form :model="commendGameForm" label-width="80px">
            <el-form-item label="推荐游戏" prop="mostLove">
                <el-input v-model="commendGameForm.mostLove"></el-input>
            </el-form-item>
            <el-form-item label="推荐游戏" prop="secondLove">
                <el-input v-model="commendGameForm.secondLove"></el-input>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
            <el-button @click="resetCommendGameVisible = false">取消</el-button>
            <el-button type="primary" @click="submitResetCommendGame">确认</el-button>
            </div>
        </el-dialog>

        <div>
        <el-card>
            <div slot="header" class="totalTitle">预测爆款游戏</div>
        </el-card>
        </div>
        <!-- 预测销售游戏总表 -->
        <div>
        <el-table :data="forecastGame" style="width: 100%;margin-bottom:20px;" >
            <el-table-column fixed prop="mostLove" label="游戏名"></el-table-column>
            <el-table-column fixed prop="secondLove" label="游戏名"></el-table-column>
        </el-table>
        </div>
    </div>
</div>
</template>



<script>
import axios from 'axios';
export default {
    data(){
        return{
            username:sessionStorage.getItem("username"),
            resetPasswordVisible:false,
            addNewManagerDialogVisible:false,
            resetCommendGameVisible:false,
            manager:[],
            user:[],
            commendGame:[],
            forecastGame:[],
            commendGameForm:{
                mostLove:'',
                secondLove:''
            },
            editForm:{
                username:'',
                password:''
            }
        }
    },
    created(){
        axios.get('/allManager').then((res)=>{this.manager=res.data.data});
        axios.get('/allUser').then((res)=>{this.user=res.data.data});
        axios.get('/getCommendGame').then((res)=>{this.commendGame=res.data.data});
        console.log(this.commendGame);
        axios.get('/setUserProfile');
        axios.get('/saleForecast').then((res)=>{this.forecastGame=res.data.data});
    },
    methods: {
        disLogin(){
            this.$router.push('/disLogin');
        },
        allSales(){
            this.$router.push('/allSales');
        },
        databaseLog(){
            this.$router.push('/databaseLog');
        },
        loginLog(){
            this.$router.push('/loginLog');
        },
        showResetPasswordForm(row)
        {
            // 将当前行数据填充到编辑表单
            this.editForm = { ...row };
            this.resetPasswordVisible = true; // 显示编辑对话框
        },
        submitResetPasswordEdit(){
            // 发送编辑后的数据到后端
          axios.post('/resetPassword', this.editForm,
            {headers:{'Content-Type': 'application/json'}});
            location.reload();
        },
        showAddNewManagerForm() {
          this.addNewManagerDialogVisible = true;
        },
        submitAddNewManager(){
            axios.post('/managerRegister', this.editForm,
            {headers:{'Content-Type': 'application/json'}});
            this.addNewManagerDialogVisible=false;
            this.$message({message: '成功新增一位游戏作者,請刷新確認！',type: 'success'})
        },
        resetCommendGame(row)
        {
            // 将当前行数据填充到编辑表单
            this.commendGameForm = { ...row };
            this.resetCommendGameVisible = true; // 显示编辑对话框
        },
        submitResetCommendGame(){
            // 发送编辑后的数据到后端
          axios.post('/setCommendGame', this.commendGameForm,
            {headers:{'Content-Type': 'application/json'}})
            .then((response) => {
                // 判断后端返回的code属性是否表示成功执行了请求
                if (response.data.code === 1) {
                    // 请求成功的处理逻辑
                    location.reload();
                } else {
                    // 请求失败的处理逻辑
                    alert(response.data.msg);
                }
            });
        }
    }
}
</script>







<style>
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
.table{
    width: 75%;
    text-align: center;
    margin: 20px auto;
}
.background {
  background-image: url('./image/bg1.jpeg'); /* 设置背景图片的路径 */
  background-size: cover; /* 让背景图片充满整个容器 */
  background-position: center center; /* 设置背景图片居中 */
}

/* 标题样式 */
.totalTitle {
font-size: 30px;
font-weight: bold;
/* text-align: center; */
color: black(43, 43, 43, 0.518); /* 设置标题字体颜色为黄色 */
}

/* 表格按钮样式 */
.button-container {
display: flex;
justify-content: space-between;
}

</style>