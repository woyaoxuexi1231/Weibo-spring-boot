<template>
  <el-container >

    <!--头像和用户名-->
    <el-header height="200px" style="margin-top: 50px">
      <div>
        <el-avatar :size="70" :src="userInfo.userAvatar" ></el-avatar>
      </div>
      <div>
        <div v-if="isVip=='true'">
          <span style="color: red">{{this.userInfo.userName}}</span>
        </div>
        <div v-else>
          <span >{{this.userInfo.userName}}</span>
        </div>
      </div>
    </el-header>

    <!--主要信息-->
    <el-container style="width: 70%;margin-left: 15%">
      <!--微博、粉丝信息汇总-->
      <el-aside width="25%" style="min-width: 200px;">
        <el-button size="small" @click="jumpToUserIndexFollow" round>
          <div>
            <span>{{this.userInfo.followCount}}</span>
          </div>
          <div>
            关注
          </div>
        </el-button>
        <el-button size="small" @click="jumpToUserIndexFollower" round>
          <div>
            <span>{{this.userInfo.followerCount}}</span>
          </div>
          <div>
            粉丝
          </div>
        </el-button>
        <el-button size="small" @click="jumpToUserIndex" round>
          <div>
            <span>{{this.userInfo.weiboCount}}</span>
          </div>
          <div>
            微博
          </div>
        </el-button>
      </el-aside>
      <!--微博主要信息-->
      <el-main  width="75%" style="min-width: 600px;padding-top: 0px">
        <transition name="el-fade-in-linear">
          <router-view/>
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
name: "userIndex",
  data(){
    return{
      uid:'',
      userInfo:[],
      weibos:[],
      isVip:'',
    }
  },
  methods:{
    getUserById(id){
      this.$axios.get("/user/getUserById?id="+id)
        .then(res => {
          this.userInfo = res.data.data;
          console.log(this.userInfo)
        }).catch(err => {
        console.log(err);
      })
    },
    jumpToUserIndex(){
      this.$router.push({name: 'userIndex'});
    },
    jumpToUserIndexFollow(){
      this.$router.push({name: 'userIndexFollow'});
    },
    jumpToUserIndexFollower(){
      this.$router.push({name: 'userIndexFollower'});
    },
  },
  mounted() {
    this.uid = sessionStorage.getItem("uid");
    this.isVip = sessionStorage.getItem("isVip");
    this.getUserById(this.uid);
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  color: #333;
  text-align: center;
  line-height: 100px;
}
.el-main {
  color: #333;

}

body > .el-container {
  margin-bottom: 40px;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
