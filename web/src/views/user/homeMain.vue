<template>
  <el-container>
    <el-aside></el-aside>

    <!--微博列表与发表-->
    <el-main>

      <!--微博发表-->
      <div>

        <el-card shadow="hover" style="padding-bottom: 0px">
          <span style="font-size: small;font-family: 黑体;float: left;">有什么新鲜事想告诉大家?</span>

          <!--文字发表-->
          <el-input
            style="margin-top: 10px"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="textarea">
          </el-input>

          <!--图片视频上传-->
          <div style="line-height: 60px">

            <!--图片上传组件-->

            <el-popover style="float: left;margin-right: 20px"
                        placement="bottom"
                        title="本地上传"
                        width="470"
                        trigger="click">
              <el-upload
                class="upload-demo"
                action="api/file/upload"
                :before-upload="beforeAvatarUpload"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :on-success="handleAvatarSuccess"
                multiple
                :limit="9"
                :on-exceed="handleExceed"
                list-type="picture-card"
                :auto-upload="true"
              >
                <i slot="default" class="el-icon-upload"></i>
              </el-upload>
              <el-button slot="reference">图片上传</el-button>
            </el-popover>

            <!--视频上传组件-->

            <el-popover
              style="float: left"
              placement="bottom"
              width="470"
              trigger="click"
            >
              <el-upload
                style="float: left"
                class="upload-demo"
                action="api/file/upload"
                :before-upload="beforeAvatarUploadVideo"
                :on-remove="handleRemoveVideo"
                :before-remove="beforeRemove"
                :on-success="handleAvatarSuccessVideo"
                :limit="1"
                :on-exceed="handleExceed"
                :auto-upload="true"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
              <video
                v-if="videoName != ''"
                controls
                style="height: 200px;width: 355px;margin-left: 55px;margin-top: 20px">
                <source :src="videoURL">
                您的浏览器暂不支持video标签，请更换浏览器后重试。
              </video>
              <el-button slot="reference">视频上传</el-button>
            </el-popover>

          </div>
          <el-button style="margin-top:10px;float: right" type="primary" @click="onSubmit">发布</el-button>
        </el-card>


      </div>
      <!--微博详情-->
      <div>

        <el-row v-for="(o, index) in weibos" :key="index">
          <el-card style="min-height: 120px;margin-top: 10px;text-align: left" shadow="hover">

            <div>
              <!--微博发表信息-->
              <div>
                <el-avatar :size="50" :src="o.userAvatar" style="float: left"></el-avatar>
                <div style="margin-left: 10px"><span>{{ o.userName }}</span></div>
                <div style="line-height: 40px;color: #808080;font-size: 12px"><span>{{ o.weiboTime }}</span></div>
              </div>
              <!--微博正文-->
              <div style="margin-left: 50px;margin-bottom: 15px"><span>{{ o.weiboText }}</span></div>
              <!--微博图片-->
              <div style="margin-left: 30px;margin-bottom: 15px">
                <span v-for="i in o.imageList" :key="i.index">
                  <img :src="i.url" style="height: 120px;margin-left: 20px;margin-top: 10px;">
                </span>
                <video
                  v-if="o.video!= null"
                  controls
                  style="height: 250px;margin-left: 20px;margin-top: 10px">
                  <source :src="o.video.url">
                  您的浏览器暂不支持video标签，请更换浏览器后重试。
                </video>
              </div>
              <!--微博视频-->
              <div>

              </div>
            </div>

            <!--微博操作-->
            <div>
              <div style="text-align: center;padding-bottom: 10px;margin-top: 10px">
                <div class="weiboComment" @click="commentMenu(index)">
                  <i class="el-icon-chat-line-round"></i>
                  <span style="font-size: 12px" v-if="o.commentCount == 0">评论</span>
                  <span style="font-size: 12px" v-if="o.commentCount > 0">{{ o.commentCount }}</span>
                </div>
                <div style="float: left;width: 5%">
                  <el-divider direction="vertical"></el-divider>
                </div>
                <div class="weiboComment" @click="submitWeiboLike(o.id,o.likeState,index)">
                  <i class="el-icon-star-off" v-if="!o.likeState"></i>
                  <i class="el-icon-star-on" v-if="o.likeState"></i>
                  <span style="font-size: 12px" v-if="o.likeCount == 0">点赞</span>
                  <span style="font-size: 12px" v-if="o.likeCount > 0">{{ o.likeCount }}</span>
                </div>
              </div>
            </div>

            <!--微博评论框-->
            <div>
              <el-collapse-transition>
                <div v-if="o.commentMenuState" class="transition-box">
                  <el-divider></el-divider>
                  <div style="margin-left: 5px">
                    <el-avatar style="float: left;margin-right: 5px" shape="square" :size="30"
                               :src="userInfo.userAvatar"></el-avatar>
                    <el-input v-model="o.commentInput" style="width: 80%" size="mini"
                              placeholder="请输入内容"></el-input>
                    <el-button size="mini" type="warning" @click="submitCommentInput(o.id,index,o.commentInput)" plain>
                      评论
                    </el-button>
                  </div>
                  <div style="margin-top: 20px">
                    <div style="width: 100%;float: left;margin-bottom: 5px" v-for="(c,index) in o.commentList"
                         :key="index">
                      <el-avatar :size="30" :src="c.commentUserAvatar" style="float: left"></el-avatar>
                      <div style="margin-left: 10px;font-size: 10px;">
                        <span style="color: #eb7350">{{ c.commentUserName }}：</span>
                        <span>{{ c.commentText }}</span>
                      </div>
                      <div style="line-height: 20px;color: #808080;font-size: 12px">
                        <span>{{ c.commentCreateTime }}</span></div>
                    </div>
                  </div>
                </div>
              </el-collapse-transition>
            </div>
          </el-card>
        </el-row>
      </div>

      <!--如果查出没有微博则显示心灵鸡汤-->
      <div v-if="weibos.length==0" style="margin-top: 250px">
        <div><span>发现新鲜事</span></div>
        <div><span>发现更美好的世界</span></div>
      </div>

      <!--上传图片之后查看大图-->
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>

    </el-main>

    <!--个人资料卡-->
    <el-aside width="300px">
      <el-card style="width: 250px;height: 200px;margin-left: 25px;padding-bottom: 30px" shadow="hover">
        <div>
          <el-avatar :size="70" :src="this.userInfo.userAvatar"></el-avatar>
        </div>
        <div>
          <div v-if="isVip=='true'">
            <span style="color: red">{{ this.userInfo.userName }}</span>
          </div>
          <div v-else>
            <span>{{ this.userInfo.userName }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <el-button size="small" @click="jumpToUserIndexFollow" round>
          <div>
            <span>{{ followCount }}</span>
          </div>
          <div>
            关注
          </div>
        </el-button>
        <el-button size="small" @click="jumpToUserIndexFollower" round>
          <div>
            <span>{{ followerCount }}</span>
          </div>
          <div>
            粉丝
          </div>
        </el-button>
        <el-button size="small" @click="jumpToUserIndex" round>
          <div>
            <span>{{ weiboCount }}</span>
          </div>
          <div>
            微博
          </div>
        </el-button>
      </el-card>
    </el-aside>

    <!--会员充值功能-->
    <el-popover
      placement="bottom"
      title="会员充值"
      width="300"
      trigger="click"
      content="进行会员充值。">

      <el-form ref="form" :model="alipayForm" label-width="80px">
        <el-form-item label="金额">
          <span style="font-size: large;font-family: 黑体;float: left;" id="vipTotalAmount"></span>
        </el-form-item>
        <el-form-item label="充值时长">
          <el-select v-model="vipTimeValue" placeholder="请选择" @change="getVipTotalAmount">
            <el-option
              v-for="item in vipTime"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="alipay">支付</el-button>
        </el-form-item>
      </el-form>
      <el-button slot="reference">点击充值会员</el-button>
    </el-popover>
  </el-container>

</template>


<script>
export default {
  name: "homeMain",
  inject: ['reload'],
  data() {
    return {
      uid: '',
      userInfo: [],
      textarea: '',
      weiboCount: 0,
      followCount: 0,
      followerCount: 0,
      weibos: [],
      commentInput: '',
      currentComment: [],
      fileList: [],
      videoName: '',
      videoURL: '',
      fileNameList: [],
      fileURLList: [],
      imageUploadMenuState: false,
      dialogImageUrl: '',
      dialogVisible: false,
      isShouldBeDelete: false,
      alipayForm: {},
      //会员时长，默认1个月
      vipTime: [{
        value: '20',
        label: '一个月'
      }, {
        value: '60',
        label: '三个月'
      }, {
        value: '120',
        label: '六个月'
      }, {
        value: '240',
        label: '一年'
      }],
      // 充值相应的会员时长对应的金额
      vipTimeValue: '',
      isVip: '',
    }
  },
  methods: {

    /**********************************数据查询相关接口*********************************/
    /* 获取用户的基本信息 */
    getCurrentUserInfo(id) {
      this.$axios.post("/user/getCurrentUserInfo", {
        firstName: 'Fred',
        lastName: 'Flintstone',
        token: "123"
      })
        .then(res => {
          this.userInfo = res.data.data;
        }).catch(err => {
        // console.log(err);
        this.$notify.error({
          title: '失败',
          message: "登录已失效, 请重新登录! ",
        });
        this.$cookies.remove('token');
        this.$router.push({name: 'login'});
      })
    },

    getWeiboFromFollowsAndMe() {
      this.$axios.get("/user/getWeiboFromFollowsAndMe?uid=" + this.uid)
        .then(res => {
          this.weibos = res.data.data;
          console.log(this.weibos)
        }).catch(err => {
        console.log(err);
      })
    },
    getWeiboCountByUserId() {
      this.$axios.get("/user/getWeiboCountByUserId?userId=" + this.uid)
        .then(res => {
          this.weiboCount = res.data.count;
        }).catch(err => {
        console.log(err);
      })
    },
    getFollowCountByUserId() {
      this.$axios.get("/user/getFollowCountByUserId?userId=" + this.uid)
        .then(res => {
          this.followCount = res.data.count;
        }).catch(err => {
        console.log(err);
      })
    },
    getFollowerCountByUserId() {
      this.$axios.get("/user/getFollowerCountByUserId?userId=" + this.uid)
        .then(res => {
          this.followerCount = res.data.count;
        }).catch(err => {
        console.log(err);
      })
    },
    onSubmit() {
      if ((this.textarea == null || this.textarea == "")
        && (this.fileNameList.length == 0) && this.videoName == '') {
        this.$message({
          message: '有什么新鲜事要分享呢?先写写吧!',
          type: 'warning'
        });
      } else {
        if (this.textarea == null || this.textarea == "") {
          this.textarea = "分享新鲜事"
        }
        console.log(this.fileNameList);
        console.log(this.fileURLList);
        var params = new URLSearchParams();
        params.append('userId', this.uid);
        params.append('weiboText', this.textarea);
        params.append("imageNameList", this.fileNameList);
        params.append("imageURLList", this.fileURLList);
        params.append("videoName", this.videoName);
        params.append("videoURL", this.videoURL);
        this.$axios({
          method: 'post',
          url: "/user/submitWeibo",
          data: params
        })
          .then(res => {
            this.$message({
              message: res.data.message,
              type: 'success'
            });
            this.textarea = '';
            this.reload();
          })
          .catch(err => {
            console.log(err);
            this.textarea = '';
          });
      }
    },
    jumpToUserIndex() {
      this.$router.push({name: 'userIndex'});
    },
    jumpToUserIndexFollow() {
      this.$router.push({name: 'userIndexFollow'});
    },
    jumpToUserIndexFollower() {
      this.$router.push({name: 'userIndexFollower'});
    },
    commentMenu(index) {
      this.weibos[index].commentMenuState = !this.weibos[index].commentMenuState;
      if (this.weibos[index].commentMenuState == true) {
        this.$axios.get("/user/getCommentListByWeiboId?weiboId=" + this.weibos[index].id)
          .then(res => {
            this.weibos[index].commentList = res.data.data;
          }).catch(err => {
          console.log(err);
        })
      }
    },
    submitCommentInput(weiboId, index, commentInput) {

      if (commentInput == '' || commentInput == undefined) {
        this.$message({
          message: '想留下点什么呢?先写写吧!',
          type: 'warning'
        });
      } else {
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
          this.weibos[index].commentInput = '';
          this.weibos[index].commentCount++;
        }).catch(err => {
          console.log(err);
        });
      }
    },
    submitWeiboLike(weiboId, likeState, index) {
      /*如果已经点赞就取消点赞*/
      if (likeState == true) {
        this.$axios.get("/user/deleteWeiboLike?weiboId=" + weiboId + "&userId=" + this.uid)
          .then(res => {
            console.log(res)
            this.weibos[index].likeState = false;
            this.weibos[index].likeCount--;
          })
          .catch(err => {
            console.log(err);
          })
      }
      if (likeState == false) {
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

    // 退出登录
    logout() {
      this.$axios.post("/security/logout", {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      })
        .then(res => {
          this.$router.push('/')
        }).catch(err => {
        console.log(err)
      })
    },

    /******************************文件相关*******************************/
    // 多图片上传，成功之后返回文件信息
    handleAvatarSuccess(res, file) {
      console.log("文件地址：" + res);
      console.log("文件名字：" + file.name);
      this.fileList.push({name: file.name, url: res})
      this.fileNameList.push(file.name);
      this.fileURLList.push(res);
      console.log(this.fileList)
    },
    // 视频上传
    handleAvatarSuccessVideo(res, file) {
      console.log("文件地址：" + res);
      console.log("文件名字：" + file.name);
      this.videoName = file.name;
      this.videoURL = res;
    },
    // 文件上传数量限制
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    // 文件上传之前判断文件格式
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.isShouldBeDelete = true;
        this.$message.error('上传只能是 JPG 或者 PNG 格式!');
        return false;
      }
      if (!isLt2M) {
        this.isShouldBeDelete = true;
        this.$message.error('上传大小不能超过 2MB!');
        return false;
      }
    },
    // 视频文件格式判断
    beforeAvatarUploadVideo(file) {
      const isMp4 = file.type === 'video/mp4';
      if (!(isMp4)) {
        this.$message.error('上传视频只能 mp4 格式!');
      }
      return isMp4;
    },
    // 视频文件删除
    beforeRemoveVideo(file, fileList) {

      return this.$confirm(`确定移除 ${file.name}？`);

    },
    // 文件删除前提醒，如果文件不符合文件格式，会执行这个函数
    beforeRemove(file, fileList) {
      if (this.isShouldBeDelete) {
        for (var i = 0; i < fileList.length; i++) {
          if (fileList[i] == file.name) {
            fileList.splice(i, 1);
            break;
          }
        }
      } else {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    },
    // 文件删除之后返回文件信息
    handleRemove(file) {
      for (var i = 0; i < this.fileNameList.length; i++) {
        if (this.fileNameList[i] == file.name) {
          this.fileNameList.splice(i, 1);
          this.fileURLList.splice(i, 1);
          break;
        }
      }
      console.log(file);
    },
    handleRemoveVideo(file) {
      this.videoName = '';
      this.videoURL = '';
      console.log(file);
    },
    // 查看已上传文件的信息
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 文件上传菜单弹出层状态修改
    imageUploadMenuStateChange() {
      this.imageUploadMenuState = !this.imageUploadMenuState;
    },
    //动态获取当前挡位的会员充值需要的费用
    getVipTotalAmount() {
      document.getElementById('vipTotalAmount').innerText = this.vipTimeValue;
    },
    // 充值会员
    alipay() {
      var params = new URLSearchParams();
      params.append('total_amount', this.vipTimeValue);
      params.append('body', this.uid);
      this.$axios({
        method: 'post',
        url: "/index/pay",
        data: params
      }).then(res => {

        console.log(res)

        if (res.status == 200) {

          document.querySelector('body').innerHTML = res.data;//查找到当前页面的body，将后台返回的form替换掉他的内容
          document.forms[0].submit();  //执行submit表单提交，让页面重定向，跳转到支付宝页面
        }

      }).catch(err => {
        console.log(err);
      })
    },

  },
  mounted() {
    this.uid = sessionStorage.getItem("uid");
    this.isVip = sessionStorage.getItem("isVip");
    if (this.uid == null || this.uid == undefined || this.uid == "") {
      this.$router.push({name: "login"})
    }
    this.getCurrentUserInfo(this.uid);
    // this.getWeiboCountByUserId()
    // this.getFollowCountByUserId();
    // this.getFollowerCountByUserId();
    // this.getWeiboFromFollowsAndMe();
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
}

.el-main {
  min-width: 600px;
  color: #333;
  text-align: center;
}

.el-container {
  margin-top: 40px;
  margin-bottom: 40px;
}

.weiboComment {
  width: 45%;
  float: left;
}

.weiboComment:hover {
  color: coral;
}

.transition-box {

}
</style>
