<template>
  <div class="background">
    <div style="background-color: #3e3e3e;width: 100%;height: 40px;margin: 0 auto;display: flex;">
      <div style="display: flex;flex: 1;">
        <div class="active" @click="historyOrder"></div>
        <div class="active" @click="historyOrder"></div>
        <div class="active" @click="historyOrder">总订单</div>
        <div class="active" @click="managerSales">销售报表</div>
      </div>
      <div style="width: 258px;text-align: right;display: flex;">
        <div class="active">你好：{{ username }}</div>
        <div class="active" @click="disLogin">退出登录</div>
      </div>
    </div>

    <div class="table">
      <div>
        <el-card>
          <div slot="header" class="totalTitle">欢迎,以下是你发布的游戏</div>
        </el-card>
      </div>

      <el-button type="primary" plain @click="showAddGameForm" style="margin-top:20px;margin-bottom:20px;">发布新游戏</el-button>
      <el-dialog :visible.sync="addGameDialogVisible" :title="編輯您的新游戲的信息">
        <el-form :model="editForm" label-width="80px">
          <el-form-item label="游戏名" prop="gameName">
            <el-input v-model="editForm.gameName"></el-input>
          </el-form-item>
          <el-form-item label="游戏详情" prop="gameDetail">
            <el-input v-model="editForm.gameDetail"></el-input>
          </el-form-item>
          <el-form-item label="价格" prop="price">
            <el-input v-model="editForm.price"></el-input>
          </el-form-item>
          <el-form-item label="库存" prop="stock">
            <el-input v-model="editForm.stock"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-input v-model="editForm.type"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addGameDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddNewGameEdit">确认</el-button>
        </div>
      </el-dialog>

      <!-- 游戏总表 -->
      <div>
        <el-dialog :visible.sync="editDialogVisible" :title="editForm.gameName">
          <el-form :model="editForm" label-width="80px">
            <el-form-item label="游戏详情" prop="gameDetail">
              <el-input v-model="editForm.gameDetail"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="price">
              <el-input v-model="editForm.price"></el-input>
            </el-form-item>
            <el-form-item label="库存" prop="stock">
              <el-input v-model="editForm.stock"></el-input>
            </el-form-item>
            <el-form-item label="类型" prop="type">
              <el-input v-model="editForm.type"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitEdit">确认</el-button>
          </div>
        </el-dialog>
        <el-table :data="shoppingCar" style="width: 100%" >
          <el-table-column fixed prop="gameName" label="游戏名称"></el-table-column>
          <el-table-column fixed prop="gameDetail" label="游戏详情"></el-table-column>
          <el-table-column fixed prop="price" label="价格" width="100"></el-table-column>
          <el-table-column fixed prop="stock" label="库存" width="100"></el-table-column>
          <el-table-column fixed prop="type" label="类型" width="100"></el-table-column>
          <el-table-column fixed prop="img" label="游戏封面"></el-table-column>
          <el-table-column fixed label="状态" :formatter="formatStatus"></el-table-column>
          <el-table-column fixed label="操作">
            <template slot-scope="scope">
              <div class="button-container">
                <el-button type="success" @click="onSale(scope.row)">上架</el-button>
                <el-button type="danger" @click="offSale(scope.row)">下架</el-button>
                <el-button type="primary" @click="showEditDialog(scope.row)">编辑</el-button>
              </div>
            </template>
          </el-table-column>
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
            shoppingCar:[],
            editDialogVisible: false,
            addGameDialogVisible: false,
            editForm: { // 编辑表单数据
              gameName: '',
              gameDetail: '',
              price: '',
              stock: '',
              type: '',
              img: null,
              ownerName:sessionStorage.getItem("username")
            }
        }
    },
    created(){
        axios.post('/manager', {ownerName:this.username},
            {headers:{'Content-Type': 'application/json'}}).then((res)=>{this.shoppingCar=res.data.data});
    },
    methods: {
        disLogin(){
            this.$router.push('/disLogin');
        },
        historyOrder(){
          this.$router.push('/managerHistoryOrder');
        },
        managerSales()
        {
          this.$router.push('/managerSales');
        },
        formatStatus(row) {
          return row.isOnSale === 1 ? '已上架' : '未上架';
        },
        onSale(row){
          axios.post('/managerOnSaleGame', {gameName:row.gameName},
            {headers:{'Content-Type': 'application/json'}})
            .then((res)=>{
              if(res.data.code===1)
              {
                this.$message({
                message: '成功上架游戏！请刷新页面确认！',
                type: 'success'
                }),
                location.reload();
              }
              else{this.$message.error(res.data.msg)}
            });
        },
        offSale(row){
          axios.post('/managerOffSaleGame', {gameName:row.gameName},
            {headers:{'Content-Type': 'application/json'}})
            .then((res)=>{
              if(res.data.code===1)
              {
                this.$message({
                message: '成功下架游戏！请刷新页面确认！',
                type: 'success'
                })
              }
              else{this.$message.error(res.data.msg)}
            });
        },
        showEditDialog(row) {
          // 将当前行数据填充到编辑表单
          this.editForm = { ...row };
          // this.$nextTick(() => {
          //   this.$refs.editDialog.$el.querySelector('.el-dialog__title').textContent = row.gameName;
          // });
          this.editDialogVisible = true; // 显示编辑对话框
        },
        submitEdit() {
          // 发送编辑后的数据到后端
          axios.post('/managerSetGameDetail', this.editForm,
            {headers:{'Content-Type': 'application/json'}});
            location.reload();
        },
        cancelForm() {
          // 关闭对话框
          this.dialogVisible = false;
        },
        showAddGameForm() {
          this.addGameDialogVisible = true;
        },
        submitAddNewGameEdit() {
          // 发送编辑后的数据到后端
          axios.post('/managerAddNewGame', this.editForm,
            {headers:{'Content-Type': 'application/json'}});
            this.addGameDialogVisible=false;
            this.$message({message: '成功新增一個游戲,請刷新確認！',type: 'success'})
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


/* 标题样式 */
.totalTitle {
font-size: 40px;
font-weight: bold;
text-align: center; /* 让标题居中显示 */
color: black(43, 43, 43, 0.518); /* 设置标题字体颜色为黄色 */
/* opacity: 0.7; */
}

/* 表格按钮样式 */
.button-container {
  display: flex;
  justify-content: space-between;
}
.table{
    width: 95%;
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
  background-size:100% 100%;
  /* position:fixed; */
  background-attachment: fixed;
}
</style>