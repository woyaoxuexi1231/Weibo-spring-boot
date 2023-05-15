<template>
  <div>
    <el-row v-for="(o, index) in follows" :key="index">
      <el-card  style="width: 600px;min-height: 120px;margin-top: 20px;text-align: left" >
        <div>
          <el-avatar :size="50" :src="o.userAvatar" style="float: left"></el-avatar>
          <div style="margin-left: 10px" >
            <span>{{o.userName}}</span>
            <el-button size="mini" v-if="o.follow">已关注</el-button>
            <el-button size="mini" v-if="!o.follow" @click="addFollow(o.fid,index)">+关注</el-button>

            <el-dropdown style="float: right">
              <span class="el-dropdown-link">
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><a @click="deleteFollow(o.id,index)">取消关注</a></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div style="color: #808080;font-size: 12px;margin-top: 2px" v-if="o.userDesc"><span>简介：{{o.userDesc}}</span></div>
        </div>
      </el-card>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "userIndexFollow",
  data(){
    return{
      follows:[],

    }
  },
  methods:{
    getFollowsByUid(){
      this.$axios.get("/user/getFollowsByUid?uid="+sessionStorage.getItem("uid"))
      .then(res =>{
        this.follows = res.data.data;
      }).catch(err => {
        console.log(err);
      })
    },
    deleteFollow(fansTableId,index){
      this.$axios.get("/user/deleteFollow?id="+fansTableId)
        .then(res => {
          this.follows[index].follow = false;
        }).catch(err => {
        console.log(err);
      })

    },
    addFollow(followId,index){
      this.$axios.get("/user/addFollow?followId="+followId+"&uid="+sessionStorage.getItem("uid"))
        .then(res => {
          this.users[index].follow = true;
        }).catch(err => {
        console.log(err);
      })
    },
  },
  mounted() {
    this.getFollowsByUid();
  }
}
</script>

<style scoped>

</style>
