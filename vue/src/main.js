// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'

Vue.use(ElementUI)
Vue.prototype.$axios = axios
Vue.config.productionTip = false
axios.defaults.baseURL="/api";
/* eslint-disable no-new */
// http response 响应拦截器
axios.interceptors.response.use(response => {
  // console.log(response);
  return response;
},error => {
  if (error.response) {
    switch (error.response.status) {
      // 返回401，清除token信息并跳转到登录页面
      case 401:
        //localStorage.removeItem('token');
        if(sessionStorage.getItem("loginPage")=="loginAdmin"){
          // 跳转管理员登录页面
          router.replace({
            path: '/loginAdmin'
            //登录成功后跳入浏览的当前页面
            // query: {redirect: router.currentRoute.fullPath}
          });
        }else {
          // 跳转普通用户登录页面
          router.replace({
            path: '/'
            //登录成功后跳入浏览的当前页面
            // query: {redirect: router.currentRoute.fullPath}
          });
        }
        break;

      case 403:
        console.log("非法访问已退出，重新登录");
        router.replace({
          path: '/'
        })
        break;
    }
    // 返回接口返回的错误信息
    return Promise.reject(error.response.data);
  }
});
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
