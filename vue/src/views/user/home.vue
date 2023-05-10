<template>
  <div class="main">
    <el-container>
      <!--首页工具栏-->
      <el-header>
        <div style="float: left;margin-left: 100px">
          <!--微博首页跳转-->
          <span style="float: left" @click="jumpToHome()">
          <i class="el-icon-chat-round"></i>
          微小博
        </span>
          <!--微博搜索-->
          <el-input placeholder="搜索微博或者用户" size="mini"
                    style="float: left;width: 400px;margin-left: 50px;margin-top: 15px"
                    v-model="searchInfo">
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </div>
        <div v-if="uid" style="float: right">
          <!--首页跳转-->
          <el-link style="font-size: large;font-family: 黑体;" @click="jumpToHomeMain">
            <i class="el-icon-monitor" style="margin-right: 5px"></i>首页
          </el-link>
          <!--个人主页跳转-->
          <el-link style="font-size: large;font-family: 黑体;" @click="jumpToUserIndex">
            <i class="el-icon-user"></i>
            {{ this.userInfo.userName }}
          </el-link>
          <el-divider direction="vertical"></el-divider>
          <!--更多选项-->
          <el-dropdown trigger="click">
          <span class="el-dropdown-link;">
            <i class="el-icon-s-tools el-icon--right"></i>
          </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item><a @click="jumpToEditUserInformation">编辑资料</a></el-dropdown-item>
              <el-dropdown-item><a @click="logout">退出登录</a></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div v-if="!uid" style="float: right">
          <span>没有账号？点击</span>
          <span @click="jumpToSignUp">注册</span>
        </div>
      </el-header>
      <!--首页主要内容(登录前login.vue、登陆后loginMain.vue)-->
      <el-container style="min-height: 800px;width: 80%;margin-left: 10%">
        <router-view>
        </router-view>
      </el-container>
      <!--首页底部信息-->
      <el-footer>微博客服QQ154347188 Copyright © 2020-2021 WEIBO</el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  name: "home",
  data() {
    return {
      uid: '',
      userInfo: [],
      textarea: '',
      searchInfo: '',
      users: [],
      isVip: '',
    }
  },
  methods: {

    // 根据用户id查询用户信息
    getUserById(id) {
      this.$axios.get("/user/getUserById?id=" + id)
        .then(res => {
          this.userInfo = res.data.data;
        }).catch(err => {
        console.log(err);
      })
    },
    // 跳转用户个人主页
    jumpToUserIndex() {
      this.$router.push({name: 'userIndex'});
    },
    // 跳转用户首页
    jumpToHomeMain() {
      this.$router.push({name: 'homeMain'});
    },
    // 跳转首页（没有登录跳登录，登录了跳用户首页）
    jumpToHome() {
      if (this.uid == "" || this.uid == null || this.uid == undefined) {
        this.$router.push({name: 'login'});
      } else {
        this.$router.push({name: 'homeMain'});
      }
      this.reload();
    },
    // 跳转用户注册页面
    jumpToSignUp() {
      this.$router.push({name: 'signUp'});
    },
    // 退出登录
    logout() {
      this.$axios.post("/logout", {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      })
        .then(res => {
          sessionStorage.setItem("uid", "");
          this.$router.push('/')
          this.reload();
        }).catch(err => {
        console.log(err)
      })
    },
    // 搜索
    search() {
      if (this.searchInfo == '') {
        this.$router.push({name: 'searchIndex'});
      } else {
        sessionStorage.setItem("searchInfo", this.searchInfo);
        this.$router.push({name: 'searchPage'});
      }
      this.reload();
    },
    // 跳转用户信息编辑页面
    jumpToEditUserInformation() {
      this.$router.push({name: 'EditUserInformation'});
    },

    checkIsVip(id) {
      this.$axios.get("/user/checkIsVip?userID=" + id)
        .then(res => {
          console.log(res.data);
          this.isVip = res.data;
          sessionStorage.setItem("isVip", this.isVip);
        }).catch(err => {
        console.log(err);
      })
    }

  },
  mounted() {
    this.uid = sessionStorage.getItem("uid");
    if (this.uid == "" || this.uid == null || this.uid == undefined) {
      console.log("当前为游客访问");
    } else {
      this.getUserById(this.uid);
      this.checkIsVip(this.uid);
    }
  }
}
</script>

<style scoped>
.el-header {
  box-shadow: 0 0 1px 0px rgba(0, 0, 0, 0.3), 0 0 6px 2px rgba(0, 0, 0, 0.15);
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-footer {

  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
}

body > .el-container {
  margin-bottom: 40px;
}


</style>
