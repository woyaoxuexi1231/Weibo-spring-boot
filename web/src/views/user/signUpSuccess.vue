<template>
  <div style="line-height: 500px;margin-left: 40%">
    <div @click="jumpToLogin">创建成功点击跳转登录界面</div>
  </div>
</template>

<script>
export default {
  name: "signUpSuccess",
  methods:{
    jumpToLogin(){
      this.$router.push({name:'login'})
    }
  }
}
</script>

<style scoped>

</style>
