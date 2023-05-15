<template>
<div>
  <el-row v-for="(o, index) in weibos" :key="index">

    <el-card  style="min-height: 120px;margin-top: 20px;text-align: left" shadow="hover">
      <!--博主信息和发表时间，删除微博-->
      <div>
        <!--头像-->
        <el-avatar :size="50" :src="o.userAvatar" style="float: left"></el-avatar>
        <!--删除微博-->
        <div style="float: right"><i class="el-icon-delete" @click="deleteWeibo(o.id)"></i></div>
        <!--名字-->
        <div style="margin-left: 10px"><span>{{o.userName}}</span></div>
        <!--发表时间-->
        <div style="line-height: 40px;color: #808080;font-size: 12px"><span>{{o.weiboTime}}</span></div>
      </div>
      <!--微博文字信息-->
      <div style="margin-left: 50px;margin-bottom: 15px"><span>{{o.weiboText}}</span></div>
      <!--微博图片视频-->
      <div>
        <!--图片信息-->
        <span v-for="i in o.imageList" :key="i.index">
          <img :src="i.url" style="height: 120px;margin-left: 20px;margin-top: 10px">
        </span>
        <!--视频信息-->
        <video
          v-if="o.video!= null"
          controls
          style="height: 120px;margin-left: 20px;margin-top: 10px">
          <source :src="o.video.url">
          您的浏览器暂不支持video标签，请更换浏览器后重试。
        </video>
      </div>
      <!--微博评论点赞操作-->
      <div style="text-align: center;padding-bottom: 10px;margin-top: 10px">
        <div class="weiboComment" @click="commentMenu(index)">
          <i class="el-icon-chat-line-round"></i>
          <span style="font-size: 12px" v-if="o.commentCount == 0">评论</span>
          <span style="font-size: 12px" v-if="o.commentCount > 0">{{o.commentCount}}</span>
        </div>
        <div style="float: left;width: 5%"><el-divider direction="vertical"></el-divider></div>
        <div class="weiboComment" @click="submitWeiboLike(o.id,o.likeState,index)">
          <i class="el-icon-star-off" v-if="!o.likeState"></i>
          <i class="el-icon-star-on" v-if="o.likeState"></i>
          <span style="font-size: 12px" v-if="o.likeCount == 0">点赞</span>
          <span style="font-size: 12px" v-if="o.likeCount > 0">{{o.likeCount}}</span>
        </div>
      </div>

      <div>
        <el-collapse-transition>
          <div v-if="o.commentMenuState" class="transition-box">
            <el-divider></el-divider>
            <div style="margin-left: 5px">
              <el-avatar style="float: left;margin-right: 5px" shape="square" :size="30" :src="userInfo.userAvatar"></el-avatar>
              <el-input v-model="o.commentInput" style="width: 80%" size="mini" placeholder="请输入内容"></el-input>
              <el-button size="mini" type="warning" @click="submitCommentInput(o.id,index,o.commentInput)" plain>评论</el-button>
            </div>
            <div style="margin-top: 20px">
              <div style="width: 100%;float: left;margin-bottom: 5px" v-for="(c,index) in o.commentList" :key="index">
                <el-avatar :size="30" :src="c.commentUserAvatar" style="float: left"></el-avatar>
                <div style="margin-left: 10px;font-size: 10px;">
                  <span style="color: #eb7350">{{c.commentUserName}}：</span>
                  <span>{{c.commentText}}</span>
                </div>
                <div style="line-height: 20px;color: #808080;font-size: 12px"><span>{{c.commentCreateTime}}</span></div>
              </div>
            </div>
          </div>
        </el-collapse-transition>
      </div>
    </el-card>
  </el-row>
</div>
</template>

<script>
export default {
name: "userIndexWeibo",
  inject:['reload'],
  data(){
    return{
      uid:'',
      userInfo:[],
      weibos:[],
    }
  },
  methods:{
    getWeiboByUserId(){
      this.$axios.get("/user/getWeiboByUserId?userId="+this.uid)
        .then(res => {
          this.weibos = res.data.data;
          console.log(this.weibos)
        }).catch(err => {
        console.log(err);
      })
    },
    commentMenu(index){
      this.weibos[index].commentMenuState = !this.weibos[index].commentMenuState;
      if(this.weibos[index].commentMenuState == true){
        this.$axios.get("/user/getCommentListByWeiboId?weiboId="+this.weibos[index].id)
          .then(res =>{
            this.weibos[index].commentList = res.data.data;
          }).catch(err => {
          console.log(err);
        })
      }
    },
    submitCommentInput(weiboId,index,commentInput){
      if(commentInput==''||commentInput==undefined){
        this.$message({
          message: '想留下点什么呢?先写写吧!',
          type: 'warning'
        });
      }else {
        var params = new URLSearchParams();
        params.append("weiboId", weiboId);
        params.append("commentText", commentInput);
        params.append("commentUserId", this.uid);
        this.$axios({
          method: 'post',
          url: "/user/submitCommentInput",
          data: params,
        }).then(res => {
          console.log(res);
          this.weibos[index].commentList.unshift({
            commentUserAvatar: this.userInfo.userAvatar,
            commentUserName: this.userInfo.userName,
            commentCreateTime: new Date().toLocaleString('chinese', {hour12: false}).split('/').join('-'),
            commentText: commentInput,
          });
          console.log(this.commentList);
          this.commentInput = '';
        }).catch(err => {
          console.log(err);
        });
      }
    },
    submitWeiboLike(weiboId,likeState,index){
      /*如果已经点赞就取消点赞*/
      if(likeState==true){
        this.$axios.get("/user/deleteWeiboLike?weiboId="+weiboId+"&userId="+this.uid)
          .then(res => {
            console.log(res)
            this.weibos[index].likeState = false;
            this.weibos[index].likeCount--;
          })
          .catch(err => {
            console.log(err);
          })
      }
      if(likeState==false) {
        this.$axios.get("/user/submitWeiboLike?weiboId=" + weiboId + "&userId=" + this.uid)
          .then(res => {
            console.log(res)
            this.weibos[index].likeState = true;
            this.weibos[index].likeCount++;
          })
          .catch(err => {
            console.log(err);
          })
      }
    },
    getUserById(id){
      this.$axios.get("/user/getUserById?id="+id)
        .then(res => {
          this.userInfo = res.data.data;
        }).catch(err => {
        console.log(err);
      })
    },
    // 通过微博id删除微博
    deleteWeibo(id){
      this.$confirm('确认删除此微博吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.get("/user/deleteWeiboById?id=" + id)
          .then(res => {
            this.$message({
              message: "删除成功!",
              type: "success"
            });
            this.reload()
          })
        .catch(err =>{
          console.log(err);
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  },
  mounted() {
    this.uid = sessionStorage.getItem("uid");
    this.getWeiboByUserId();
    this.getUserById(this.uid);
  }
}
</script>

<style scoped>
.weiboComment{
  width: 45%;
  float: left;
}
.weiboComment:hover{
  color: coral;
}
</style>
