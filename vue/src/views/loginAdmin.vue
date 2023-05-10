<template>
  <div>
    <div class="main">
      <div style="            position: absolute;
            top: 50%;
            left: 47%;
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            -o-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);   ">
        <el-form :model="ruleForm" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item >
            <el-input style="width: 200px" v-model="ruleForm.name" placeholder="管理员账号"></el-input>
          </el-form-item>
          <el-form-item >
            <el-input style="width: 200px" type="password" v-model="ruleForm.password" autocomplete="off" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 200px" type="primary" @click="login">提交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import qs from 'qs';

export default {
name: "login",
  data() {
   return {
     ruleForm: {
       name:'',
       password:'',
     },
   }
  },
  methods:{
    login() {
      this.$axios
        .post("/login", qs.stringify(this.ruleForm), {
          headers: {
            'Content-Type':'application/x-www-form-urlencoded',
          }
        })
        .then(res => {
          if(res.data.msg.roles[0].name=="ROLE_admin"||res.data.msg.roles[0].name=="ROLE_dba"){
            console.log("登录成功");
            sessionStorage.setItem("admin",res.data.msg.roles[0].name);
            sessionStorage.setItem("adminId",res.data.msg.id)
            this.$router.push({name: 'admin'})
          }else {
            this.$message({
              message:"没有权限",
              type:"error"
            })
          }
        })
        .catch(err => {
          this.$message({
            message:err.msg,
            type:"error"
          })
          console.log(err);
        });
    },
  },
  mounted() {
    sessionStorage.setItem("loginPage","loginAdmin");
  },
}
</script>

<style scoped>
body{
  background-color: #eee;
}
.main{
  width: 100%;
  height: 100%;
  position: absolute;
  background-image: url("../assets/scenery2.jpg");
  background-size: 100%;
}
</style>
