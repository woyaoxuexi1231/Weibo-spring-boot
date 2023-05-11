<template>
  <el-container >

    <el-header height="200px" style="margin-top: 50px">
      <div>
        <el-avatar :size="70" :src="userInfo.userAvatar" ></el-avatar>
      </div>
      {{userInfo.userName}}
    </el-header>

    <el-container style="width: 70%;margin-left: 15%">
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
  name: "userIndexBySearch",
  data(){
    return{
      uid:'',
      searchId:'',
      userInfo:[],
      weibos:[],
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
      this.$router.push({name: 'userIndexBySearch'});
    },
    jumpToUserIndexFollow(){
      this.$router.push({name: 'userIndexFollowBySearch'});
    },
    jumpToUserIndexFollower(){
      this.$router.push({name: 'userIndexFollowerBySearch'});
    },
  },
  mounted() {
    this.uid = sessionStorage.getItem("uid");
    this.searchId = sessionStorage.getItem("searchId");
    this.getUserById(this.searchId);
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
  text-align: center;
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
