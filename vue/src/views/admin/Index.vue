<template>
<el-container style=" border: 1px solid #eee">

  <!--后台管理系统标题-->
  <el-header>
    <i class="el-icon-chat-round"></i>
    <span style="font-size: large;font-family: 楷体" @click="jumpToIndexHomePage">微小博后台管理系统</span>
    <div style="float: right">
      <el-dropdown trigger="click">
        <span class="el-dropdown-link;">
          <el-avatar style="margin-top: 10px" :src="adminInfo.userAvatar"></el-avatar>
        </span>
        <el-dropdown-menu slot="dropdown" style="padding: 10px;text-align: center">
          <span style="color: #eb7350;line-height:0px;">当前管理员：{{adminInfo.userName}}</span>
          <el-dropdown-item><i class="el-icon-switch-button"></i><a @click="logout()">退出登录</a></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-header>

<el-container>
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <el-menu :default-openeds="['1', '2','3']" :default-active="this.$router.path" router>
      <el-submenu index="1">
        <template slot="title"><i class="el-icon-user-solid"></i>用户管理</template>
        <el-menu-item-group>
          <template slot="title">信息管理</template>
          <el-menu-item index="/UserList" ><i class="el-icon-s-operation"></i>用户列表</el-menu-item>
          <el-menu-item index="/AddUser"><i class="el-icon-circle-plus-outline"></i>新增用户</el-menu-item>
        </el-menu-item-group>

      </el-submenu>
      <el-submenu index="2">
        <template slot="title"><i class="el-icon-menu"></i>微小博管理</template>

        <el-menu-item-group>
          <el-menu-item index="/WeiboList" ><i class="el-icon-chat-line-round"></i>微小博列表</el-menu-item>
        </el-menu-item-group>

      </el-submenu>
      <el-submenu index="3" v-if="authority == 'ROLE_admin'">
        <template slot="title"><i class="el-icon-lock"></i>权限管理</template>

        <el-menu-item-group >
          <el-menu-item index="/Authority" ><i class="el-icon-key"></i>权限分配</el-menu-item>
        </el-menu-item-group>

      </el-submenu>
    </el-menu>
  </el-aside>

  <!--主页面-->
  <el-main style="min-height: 600px">
    <router-view></router-view>
  </el-main>
</el-container>

</el-container>

</template>
<style>
.el-header {
  border-bottom: 1px solid rgb(238, 238, 238);
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>

export default {
  inject:['reload'],
  data() {

    return {
      adminInfo:[],
      authority:sessionStorage.getItem("admin"),
    }
  },
  methods:{
    getAdminById(){
      this.$axios.get("/admin/getUserById?id="+sessionStorage.getItem("adminId"))
      .then(res => {
        this.adminInfo = res.data.data;
      })
      .catch(err => {
        console.log(err);
      })
    },
    jumpToIndexHomePage(){
      this.$router.push({name: 'IndexHomePage'});
      this.reload();
    },
    logout(){
      this.$axios.post("/logout",{
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      })
      .then(res => {
        sessionStorage.setItem("admin",'');
        this.$router.push('/loginAdmin')
      }).catch(err => {
        console.log(err)
      })
    },
  },

  mounted() {
    if(sessionStorage.getItem("admin")==''||sessionStorage.getItem("admin")==null){
      this.$message({
        message:"没有登录",
        type:"error"
      })
      this.$router.push('/loginAdmin');
    }
    this.getAdminById();
  }

};
</script>
