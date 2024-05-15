const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    //代理请求路径
    proxy: {
      '/api':{
        target:"http://47.113.226.221:8080/",
        changeOrigin:true,
        pathRewrite:{
          '^/api':''
        }
      }
    },
    port:7000
  }
})
