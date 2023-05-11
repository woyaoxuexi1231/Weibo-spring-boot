<template>

  <el-container>
    <el-main>
      <!--热搜栏-->
      <el-card style="width: 400px;padding-bottom: 30px;float: left" shadow="hover">
        <div style="margin-bottom: 10px"><span style="font-size: 14px;">今日热搜：</span></div>
        <el-divider content-position="right"></el-divider>
        <div v-for="(item,index) in hotWord" :key="item.value">
          <div style="margin-bottom: 5px">
            <span style="color: #f26d5f;font-size: 16px;font-family:'黑体';font-weight: 600">{{ index + 1 }} </span>
            <span style="font-size: 12px">{{ item.value }}</span>
          </div>
        </div>
      </el-card>
    </el-main>
    <!--登录框-->
    <el-aside width="500px">
      <el-card
        style="width: 300px;height: 200px;margin-right: 150px;padding-bottom: 30px;float: right;text-align: center"
        shadow="hover">
        <el-form :model="ruleForm" status-icon ref="ruleForm" class="demo-ruleForm">
          <el-tooltip class="item" effect="dark" content="Top Center 提示文字" placement="top"
                      v-if="nameTips"></el-tooltip>
          <el-form-item>
            <el-input
              prefix-icon="el-icon-user"
              placeholder="手机号码登录"
              style="width: 200px;font-size: 10px"
              v-model="ruleForm.username">

            </el-input>
          </el-form-item>
          <el-popover
            v-model=nameTips
            placement="top-start"
            title="标题"
            width="200"
            trigger="hover"
            content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
          </el-popover>
          <el-form-item>
            <el-input
              prefix-icon="el-icon-lock"
              placeholder="请输入密码"
              style="width: 200px;font-size: 10px"
              type="password"
              v-model="ruleForm.password"
              autocomplete="off">

            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 200px" type="primary" @click="login()">提交</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-aside>
    <!--登陆栏-->
    <!--      <el-aside width="30%" style="margin-top: 20px"></el-aside>-->
  </el-container>

</template>

<script>
import qs from 'qs';

export default {

  name: "login",
  inject: ['reload'],
  data() {
    return {
      ruleForm: {
        username: '',
        password: '',
      },
      nameTips: false,
      passwordTips: false,
      hotWord: [],
    }
  },
  methods: {
    login() {
      if (this.ruleForm.username == null || this.ruleForm.username == "" || this.ruleForm.password == null || this.ruleForm.password == "") {
        this.$message({
          message: '请输入用户名和密码',
          type: 'warning'
        });
      }
      if (this.ruleForm.username != null && this.ruleForm.username != "" && this.ruleForm.password != null && this.ruleForm.password != "") {
        this.$axios
          .post("/security/login", qs.stringify(this.ruleForm), {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
            }
          })
          .then(res => {
            this.$message({
              message: '登陆成功',
              type: 'success',
              showClose: true,
            });
            sessionStorage.setItem("uid", res.data.msg.id);
            this.$router.push({name: 'homeMain'});
            this.reload();
          })
          .catch(err => {
            this.$message({
              message: err.msg,
              type: "error"
            })
            console.log(err);
          });
      }
    },
    //获取热搜词汇
    getHotWord() {
      this.$axios.get("/server/getHotWord")
        .then(res => {
          console.log(res);
          this.hotWord = res.data;
        }).catch(err => {
        console.log(err);
      })
    },
    // 初始化
    init() {
      this.getHotWord();
    },
  },
  mounted() {
    this.init();
    sessionStorage.setItem("loginPage", "login");
    var uid = sessionStorage.getItem("uid");
    if (uid != null && uid != undefined && uid != "") {
      this.$router.push({name: "homeMain"})
    }
  }
}
</script>

<style scoped>

.el-aside {
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  color: #333;
}

.el-container {
  margin-top: 40px;
  margin-bottom: 40px;
}

</style>
