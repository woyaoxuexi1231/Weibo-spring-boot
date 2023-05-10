import Vue from 'vue'
import Router from 'vue-router'
import admin from "../views/admin/Index";
import UserList from "../views/admin/UserList";
import IndexHomePage from "../views/admin/IndexHomePage";
import AddUser from "../views/admin/AddUser";
import EditUser from "../views/admin/EditUser";
import loginAdmin from "../views/loginAdmin";
import login from "../views/user/login";
import home from "../views/user/home";
import userIndex from "../views/user/userIndex";
import homeMain from "../views/user/homeMain";
import searchPage from "../views/user/searchPage";
import searchIndex from "../views/user/searchIndex";
import userIndexWeibo from "../views/user/userIndexWeibo";
import userIndexFollow from "../views/user/userIndexFollow";
import userIndexFollower from "../views/user/userIndexFollower";
import signUp from "../views/user/signUp";
import signUpSuccess from "../views/user/signUpSuccess";
import WeiboList from "../views/admin/WeiboList";
import userIndexBySearch from "../views/search/userIndexBySearch";
import userIndexWeiboBySearch from "../views/search/userIndexWeiboBySearch";
import userIndexFollowBySearch from "../views/search/userIndexFollowBySearch";
import userIndexFollowerBySearch from "../views/search/userIndexFollowerBySearch";
import payGateWay from "../views/user/payGateWay";
import Authority from "../views/admin/Authority"
import EditUserInformation from "../views/user/EditUserInformation"

Vue.use(Router)


export default new Router({
  mode:'history',
  routes: [

    // 用户端跳转
    {
      path: '/',
      name: 'home',
      component: home,
      redirect: login,
      children: [
        {
          path: '/login',
          name: 'login',
          component: login,
        },
        {
          path: '/signUp',
          name: 'signUp',
          component: signUp,
        },
        {
          path: '/signUpSuccess',
          name: 'signUpSuccess',
          component: signUpSuccess,
        },
        {
          path: '/homeMain',
          name: 'homeMain',
          component: homeMain,
        },
        {
          path: '/userIndex',
          name: 'userIndex',
          component: userIndex,
          redirect: userIndexWeibo,
          children:[
            {
              path: '/userIndexWeibo',
              name: 'userIndexWeibo',
              component: userIndexWeibo,
            },
            {
              path: '/userIndexFollow',
              name: 'userIndexFollow',
              component: userIndexFollow,
            },
            {
              path: '/userIndexFollower',
              name: 'userIndexFollower',
              component: userIndexFollower,
            },
            {
              path: '/EditUserInformation',
              name: 'EditUserInformation',
              component: EditUserInformation,
            },
          ]
        },
        {
          path: '/userIndexBySearch',
          name: 'userIndexBySearch',
          component: userIndexBySearch,
          redirect: userIndexWeiboBySearch,
          children:[
            {
              path: '/userIndexWeiboBySearch',
              name: 'userIndexWeiboBySearch',
              component: userIndexWeiboBySearch,
            },
            {
              path: '/userIndexFollowBySearch',
              name: 'userIndexFollowBySearch',
              component: userIndexFollowBySearch,
            },
            {
              path: '/userIndexFollowerBySearch',
              name: 'userIndexFollowerBySearch',
              component: userIndexFollowerBySearch,
            },
          ]
        },
        {
          path: '/searchPage',
          name: 'searchPage',
          component: searchPage,
        },
        {
          path: '/searchIndex',
          name: 'searchIndex',
          component: searchIndex,
        },

      ],

    },
    // 管理员端登录页面
    {
      path: '/loginAdmin',
      name: 'loginAdmin',
      component: loginAdmin,
    },
    // 管理员端跳转
    {
      path: '/admin',
      name: 'admin',
      component: admin,
      redirect: '/IndexHomePage',
      children:[
        {
          path: '/IndexHomePage',
          name: 'IndexHomePage',
          component: IndexHomePage,
        },
        {
          path: '/UserList',
          name: 'UserList',
          component: UserList,
        },
        {
          path: '/AddUser',
          name: 'AddUser',
          component: AddUser,
        },
        {
          path:'/EditUser',
          name: 'EditUser',
          component: EditUser,
        },
        {
          path:'/WeiboList',
          name: 'WeiboList',
          component: WeiboList,
        },
        {
          path:'/Authority',
          name: 'Authority',
          component: Authority,
        },
      ]
    },

    {
      path: '/payGateWay',
      name: 'payGateWay',
      component: payGateWay,
    }
  ]
})

