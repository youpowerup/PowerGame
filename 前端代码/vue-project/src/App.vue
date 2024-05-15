<template>
  <div>
    <!-- <h1>{{ message }}</h1> -->
    <!-- <login-page></login-page> -->
    <router-view></router-view>
  </div>
</template>

<script>
// import LoginPage from './views/Element/LoginPage.vue'
export default {
  components: { },//LoginPage },
  data:function(){
    return {
      message:"hello"
    }
  },
  methods:{

  }
}
</script>

<style>

</style>
