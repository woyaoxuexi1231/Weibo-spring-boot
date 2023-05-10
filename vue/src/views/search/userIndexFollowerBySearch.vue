<template>
  <div>
    <el-row v-for="(o, index) in followers" :key="index">
      <transition name="el-fade-in">
        <el-card v-if="o.id" style="width: 600px;min-height: 120px;margin-top: 20px;text-align: left">
          <div>
            <el-avatar :size="50" :src="o.userAvatar" style="float: left"></el-avatar>
            <div style="margin-left: 10px">
              <span>{{ o.userName }}</span>
              <div style="float: right">
                <el-button size="mini" v-if="o.follow && !o.id===uid">相互关注</el-button>
                <el-button size="mini" v-if="!o.follow && !o.id===uid" @click="addFollow(o.fid,index)">+关注</el-button>
                <el-dropdown>
              <span class="el-dropdown-link">
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
                </el-dropdown>
              </div>
            </div>

            <div style="color: #808080;font-size: 12px;margin-top: 2px" v-if="o.userDesc">
              <span>简介：{{ o.userDesc }}</span></div>
          </div>
        </el-card>
      </transition>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "userIndexFollowerBySearch",
  data() {
    return {
      followers: [],
      uid: '',
    }
  },
  methods: {
    getFollowersByUid() {
      this.$axios.get("/user/getFollowersByUid?uid=" + sessionStorage.getItem("searchId"))
        .then(res => {
          this.followers = res.data.data;
        }).catch(err => {
        console.log(err);
      })
    },
    addFollow(followId, index) {
      this.$axios.get("/user/addFollow?followId=" + followId + "&uid=" + sessionStorage.getItem("searchId"))
        .then(res => {
          this.followers[index].follow = true;
        }).catch(err => {
        console.log(err);
      })
    },
    deleteFollower(fansTableId, index) {
      this.$axios.get("/user/deleteFollow?id=" + fansTableId)
        .then(res => {
          this.followers[index].id = '';
        }).catch(err => {
        console.log(err);
      })
    },
  },
  mounted() {
    this.getFollowersByUid();
    this.uid = sessionStorage.getItem("uid");
    console.log(this.uid);
  }
}
</script>

<style scoped>

</style>
