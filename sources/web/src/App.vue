<template>
  <div id="app">
      <router-view v-if="show"/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return{
      show:true,
    }
  },
  provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
    return {
      reload: this.reload
    }
  },
  methods:{
    reload () {
      this.show = false;            //先关闭，
      this.$nextTick(function () {
        this.show = true;         //再打开
      })
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
* {
  margin:0;
  padding:0;
}
html, body {
  height: 100%;
}
</style>
