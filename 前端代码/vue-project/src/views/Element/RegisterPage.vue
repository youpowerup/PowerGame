<!--
<template>
    <div v-loading="loading" element-loading-text="注册中..." element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.6)" class="login-container">
   
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
        <div v-if="TxStatus" class="avatar-box">
          <img src="../../assets/more-miui.jpg" alt="图片被吞了！">
        </div>
   
        <div class="title-container">
          <h3 class="title">欢迎</h3>
        </div>
   
        <el-form-item prop="username">
          <span class="svg-container">
            <svg-icon icon-class="user" />
          </span>
          <el-input ref="username" v-model="loginForm.username" placeholder="请输入用户名" name="username" type="text" tabindex="1" auto-complete="on" />
        </el-form-item>
   
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType" placeholder="请输入密码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
        <div>
          <el-button type="primary" style="width:100%;margin-bottom:20px;" @click.native.prevent="handleLogin">
              <span style="text-decoration: none;">注册</span>
          </el-button>
        </div>
   
      </el-form>
    </div>
  </template>
  <script>
  // 引入去除空格工具
  import { validUsername } from '@/utils/validate'
  import axios from 'axios'

  export default {
    name: 'LoginPage',
    data() {
      const validateUsername = (rule, value, callback) => {
        if (validUsername(value)==='') {
          callback(new Error('用户名不能为空！'))
        }setTimeout(() => {
          if (value.length > 20) {
            callback(new Error('用户名不能大于20个字符'));
          } else {
            callback();
          }
        }, 1000);
      }
      const validatePassword = (rule, value, callback) => {
        if (value.length < 6) {
          callback(new Error('密码最少为6位字符！'))
        } setTimeout(() => {
          if (value.length > 20) {
            callback(new Error('密码不能大于20个字符'));
          } else {
            callback();
          }
        }, 1000);
      }
      return {
        // 头像状态
        TxStatus: true,
        loginForm: {
          username: '',
          password: ''
        },
        // 登录规则
        loginRules: {
          username: [{ required: true, trigger: 'blur', validator: validateUsername }],
          password: [{ required: true, trigger: 'blur', validator: validatePassword }]
        },
        loading: false,
        passwordType: 'password',
        redirect: '/customer'
      }
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    methods: {
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      // 登录业务
      handleLogin() {
        this.$refs.loginForm.validate((valid) => {
          try {              
            axios.post('/register', {//用post方法传 输入框输入的用户名和密码
                        username: this.loginForm.username,
                        password: this.loginForm.password,
                      },{headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
                        .then((successResponse) => {//回调函数当post成功后执行
                          //alert(successResponse.data.code);
                          if (successResponse.data.code === 1) {//如果后端返回的状态码是1
                            if (valid) {
                              this.loading = true
                              setTimeout(() => {
                                this.$router.push('/customer').then(() => {
                                  //alert("欢迎！"+this.loginForm.username+"!");
                                  this.loading = false
                                }).catch(() => {
                                  this.loading = false
                                })
                              }, 1500)
                            } else {
                              console.log('error submit!!')
                              return false
                            }
                            // alert("登陆成功")//调用第一个弹窗方法表示登录成功
                            // this.$router.replace({//路由替换为index
                            //   path: '/customer'
                            // });
                          }
                          if (successResponse.data.code === 0 ) {alert("注册失败!此用户已经存在！")}
                        })
                        .catch(console.error())
          } catch (error) {  
            console.error(error);  
          }
        })
      },
    }
  }
  </script>
   
  <style lang="scss">
  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;
  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
   
      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;
   
        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }
   
    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
  </style>
   
  <style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;
   
  .login-container {
    min-height: 100%;
    width: 100%;
    overflow: hidden;
    background: url(~@/assets/bg6.jpg);
    background-size: cover;//100% 100%;
     // 头像
    .avatar-box {
      margin: 0 auto;
      width: 120px;
      height: 120px;
      border-radius: 50%;
      border: 1px solid #409eff;
      box-shadow: 0 0 10px #409eff;
      position: relative;
      bottom: 20px;
      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }
   
    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
      margin: 0 auto;
      overflow: hidden;
      margin-bottom: 305px;
   
    }
   
    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }
   
    .title-container {
      position: relative;
   
      .title {
        font-size: 30px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: 500;
      }
    }
   
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
  }
  </style>
-->

<template>
  <div v-loading="loading" element-loading-text="注册中..." element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.6)" class="login-container">
 
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <!-- 头像区域 -->
      <div v-if="TxStatus" class="avatar-box">
        <img src="./image/head.jpg" alt="图片被吞了！">
      </div>
 
      <div class="title-container">
        <h3 class="title">欢迎新用户</h3>
      </div>
 
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input ref="username" v-model="loginForm.username" placeholder="请输入用户名" name="username" type="text" tabindex="1" auto-complete="on" />
      </el-form-item>
 
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType" placeholder="请输入密码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <div>
        <!-- <router-link to="/customer"> -->
        <el-button type="primary" style="width:100%;margin-bottom:20px;" @click.native.prevent="handleLogin">
            <span style="text-decoration: none;">注册</span>
        </el-button>
      <!-- </router-link> -->
      </div>
      <div class="tips">
        <!-- <router-link to="/register"> -->
        <!-- <span style="margin-right:20px;">如果您还没有账号请先 <span style="color:#409EFF;cursor:pointer" @click="register">注册</span></span> -->
        <el-button type="primary" style="width:100%;margin-bottom:20px;" @click.native.prevent="handleRegister">
          回到登陆页
        </el-button>
        <!-- </router-link> -->
      </div>
 
    </el-form>
  </div>
</template>
 
<script>
// 引入去除空格工具
import { validUsername } from '@/utils/validate'
import axios from 'axios'
 
export default {
  name: 'RegisterPage',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (validUsername(value)==='') {
        callback(new Error('用户名不能为空！'))
      }setTimeout(() => {
        if (value.length > 20) {
          callback(new Error('用户名不能大于20个字符'));
        } else {
          callback();
        }
      }, 1000);
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码最少为6位字符！'))
      } else {
        callback()
      }
    }
    //users:[]
    return {
      // 头像状态
      TxStatus: true,
      loginForm: {
        username: '',
        password: ''
      },
      // 登录规则
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: '/customer'
    }
    
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    // 登录业务
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        // const jsonObject_out = { "username": this.loginForm.username, "password": this.loginForm.password };  
        // //const string_out="{\"username\":\""+this.loginForm.username+"\",\"password\":\""+this.loginForm.password+"\"}";
        // try {  
        //   const response =axios.post('/login', jsonObject_out,{headers:{'Content-Type': 'application/json'}}).then((res)=>{return res});
        //   console.log(jsonObject_out);
        //   alert(response.data);
        //   const jsonObject_in = JSON.parse(response.data);  
            
        //   if (jsonObject_in&&jsonObject_in.code == 1) {  
        //     // code值为1，表示请求成功  
        //     // 如果符合验证规则
        //     if (valid) {
        //       this.loading = true
        //       setTimeout(() => {
        //         this.$router.push('/customer').then(() => {
        //           this.loading = false
        //         }).catch(() => {
        //           this.loading = false
        //         })
        //       }, 1500)
        //     } else {
        //       console.log('error submit!!')
        //       return false
        //     }
        //     alert('请求成功');  
        //     // 在这里可以添加其他处理逻辑  
        //   } else {  
        //     // code值不为1，表示请求失败或其他错误  
        //     alert('请求失败，请联系管理员');  
        //     // 在这里可以添加其他处理逻辑  
        //   }  
        // } catch (error) {  
        //   console.error(error);  
        // }
        
        try {              
          axios.post('/register', {//用post方法传 输入框输入的用户名和密码
                      username: this.loginForm.username,
                      password: this.loginForm.password,
                    },{headers:{'Content-Type': 'application/json'}}).then((res)=>{return res})
                      .then((successResponse) => {//回调函数当post成功后执行
                        //alert(successResponse.data.code);
                        if (successResponse.data.code === 1) {//如果后端返回的状态码是1
                          sessionStorage.setItem('username',this.loginForm.username);
                          if (valid) {
                            this.loading = true
                            setTimeout(() => {
                              this.$router.push('/customer').then(() => {
                                //alert("欢迎！"+this.loginForm.username+"!");
                                this.loading = false
                              }).catch(() => {
                                this.loading = false
                              })
                            }, 1500)
                          } else {
                            console.log('error submit!!')
                            return false
                          }
                          // alert("登陆成功")//调用第一个弹窗方法表示登录成功
                          // this.$router.replace({//路由替换为index
                          //   path: '/customer'
                          // });
                        }
                        if (successResponse.data.code === 0 ) {sessionStorage.clear(),alert("注册失败!此用户已存在！")}
                      })
                      .catch(console.error())
        } catch (error) {  
          console.error(error);  
        }
      })
    },
    // 注册业务
    handleRegister() {
      this.$router.push('/login')
    }
  }
}
</script>
 
<style lang="scss">
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;
@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
 
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;
 
      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }
 
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>
 
<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
 
.login-container {
  min-height: 100%;
  width: 100%;
  overflow: hidden;
  background: url(~@/assets/bg6.jpg);
  background-size: cover;//100% 100%;
   // 头像
  .avatar-box {
    margin: 0 auto;
    width: 120px;
    height: 120px;
    border-radius: 50%;
    border: 1px solid #409eff;
    box-shadow: 0 0 10px #409eff;
    position: relative;
    bottom: 20px;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
 
  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
 
  }
 
  .tips {
    font-size: 18px;
    text-align: center;
    color: #000;
    margin-bottom: 280px;
  }
 
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
 
  .title-container {
    position: relative;
 
    .title {
      font-size: 30px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: 500;
    }
  }
 
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>