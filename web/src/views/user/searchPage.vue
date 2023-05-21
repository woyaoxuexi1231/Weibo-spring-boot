<template>
  <el-container>
    <el-main>
      <div v-if="users.length==0" style="margin-top: 300px;width: 30%;margin-left: 35%">
        <div style="text-align: center">
          <p>未找到关于{{ searchInfo }}的信息</p>
        </div>
        <div style="margin-top: 50px">
          <el-card style="width: 450px;padding-bottom: 30px;float: left" shadow="hover">
            <div style="margin-bottom: 10px"><span style="font-size: 14px;">今日热搜：</span></div>
            <el-divider content-position="right"></el-divider>
            <div v-for="(item,index) in hotWord" :key="item.value">
              <div>
                <span style="color: #f26d5f;font-size: 16px;font-family:'黑体';font-weight: 600">{{ index + 1 }} </span>
                <span style="font-size: 12px">{{ item.value }}</span>
              </div>
            </div>
          </el-card>
        </div>
      </div>
      <el-row v-for="(o, index) in users" :key="index">
        <el-card style="width: 600px;min-height: 120px;margin-top: 20px;text-align: left" shadow="hover">
          <div>
            <div @click="jumpToUserIndexBySearch(o.id)">
              <el-avatar :size="50" :src="o.userAvatar" style="float: left"></el-avatar>
            </div>
            <div style="margin-left: 10px">
              <span @click="jumpToUserIndexBySearch(o.id)">{{ o.username }}</span>
              <el-button size="mini" v-if="o.follow">已关注</el-button>
              <el-button size="mini" v-if="!o.follow" @click="addFollow(o.id,index)">+关注</el-button>
            </div>
            <div style="color: #808080;font-size: 12px;margin-top: 2px">
              <span>关注 {{ o.followCount }}</span>
              <el-divider direction="vertical"></el-divider>
              <span>粉丝 {{ o.followerCount }}</span>
              <el-divider direction="vertical"></el-divider>
              <span>微博 {{ o.weiboCount }}</span>
            </div>
            <div style="color: #808080;font-size: 12px;margin-top: 2px" v-if="o.userDesc">
              <span>简介：{{ o.userDesc }}</span></div>
          </div>
        </el-card>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import userIndexBySearch from "../search/userIndexBySearch";

export default {
  name: "searchPage",
  data() {
    return {
      users: [],
      searchInfo: '',
      hotWord: [],
    }
  },

  methods: {
    getUsersByLikeName() {
      if (sessionStorage.getItem("uid") == null) {
        sessionStorage.setItem("uid", "");
      }
      // 按照关键词进行搜索
      this.$axios.get('/search/getSearchResult', {
        // method: 'GET',
        // url: '/server/getSearchResult',
        params: {
          keyWord: this.searchInfo
        }
      }).then(res => {
        this.users = res.data.data.userCommonInfoDTOS;
      }).catch(err => {
        console.log(err)
        this.$notify.error({
          title: '失败',
          message: err.msg,
        });
      })
      console.log(this.users)
      // // 搜索后增加这个热词进入热搜库
      // this.$axios.get("/search/addHotWord?hotWord="+this.searchInfo)
      //   .then(res => {
      //   }).catch(err => {
      //   console.log(err);
      // })
    },

    //获取热搜词汇
    getHotWord() {
      this.$axios.get("/search/getHotWord")
        .then(res => {
          // console.log(res);
          this.hotWord = res.data;
        }).catch(err => {
        console.log(err);
      })
    },

    init() {
      this.searchInfo = sessionStorage.getItem("searchInfo");
      //获取搜索的结果
      this.getUsersByLikeName();
      this.getHotWord();
      // console.log(this.users);
    },
    addFollow(followId, index) {
      var uid = sessionStorage.getItem("uid");
      if (uid == null || uid == undefined || uid == "") {
        this.$message("请先登录")
      } else {
        this.$axios.get("/user/addFollow?followId=" + followId + "&uid=" + sessionStorage.getItem("uid"))
          .then(res => {
            this.users[index].follow = true;
          }).catch(err => {
          console.log(err);
        })
      }
    },
    jumpToUserIndexBySearch(id) {
      sessionStorage.setItem("searchId", id);
      this.$router.push({name: 'userIndexBySearch'});
    },
  },
  mounted() {
    this.init();
  }
}
</script>

<style scoped>
.el-main {
  color: #333;

}
</style>
