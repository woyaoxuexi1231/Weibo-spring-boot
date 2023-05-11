<template >
  <div style="margin-top: 100px;margin-bottom: 100px; position: absolute;
    left: 50%;
    top:350px;
    transform: translate(-50%,-50%);">
    <el-card style="width: 560px;padding-bottom: 10px;padding-top: 10px;" shadow="hover">
      <div style="margin-bottom: 20px;text-align: center">
        <span style="font-size: 20px;font-family:'黑体';font-weight: 600;">注册</span>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="userName" required>
          <el-input style="width: 200px" v-model="ruleForm.userName" ></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <div>
            <el-upload
              class="avatar-uploader"
              action="api/file/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <el-avatar :src="ruleForm.userAvatar"></el-avatar>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="手机" prop="userPhone" required>
          <el-input style="width: 200px" v-model="ruleForm.userPhone"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" required>
          <el-input style="width: 200px" v-model="ruleForm.password" show-password ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword" required>
          <el-input style="width: 200px" v-model="ruleForm.checkPassword" show-password ></el-input>
        </el-form-item>
        <el-form-item label="性别" >
          <el-select clearable style="width: 100px" v-model="ruleForm.userSex" placeholder="性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker value-format="yyyy-MM-dd" clearable style="width: 200px" type="date" placeholder="选择日期" v-model="ruleForm.userBirth" ></el-date-picker>
        </el-form-item>
        <el-form-item label="个性签名">
          <el-input style="width: 400px" type="textarea" v-model="ruleForm.UserDesc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    // 自定义表单验证
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (!/^[a-z0-9*-+)(*&^%$#@!/]+[a-z0-9*-+)(*&^%$#@!/]$/.test(value)) {
          callback(new Error("不能存在特殊字符，密码可以包含的字符有：!@#$%^&*()_+-=/"));
        } else {
          if (this.ruleForm.checkPassword !== '') {
            this.$refs.ruleForm.validateField('checkPassword');
          }
          callback();
        }
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validatePhone = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入手机号"));
      } else {
        if (!/^1[3456789]\d{9}$/.test(value)) {
          callback(new Error("请输入正确的手机号"));
        } else {
          callback();
        }
      }
    };
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else {
        if (/^[0-9]+[0-9]$/.test(value)) {
          callback(new Error("用户名不能全是数字"));
        } else {
          callback();
        }
      }
    };
    return {
      ruleForm: {
        userSex:'',
        UserDesc:'',
        userBirth:'',
        userAvatar:'',
      },
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      rules: {
        userName: [
          { required: true, message: '用户名为必填项', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'change' },
          { validator: validateName, trigger: "change" },
        ],
        userPhone: [
          { required: true, message: '手机号为必填项', trigger: 'blur' },
          { validator: validatePhone, trigger: "blur" }
        ],
        password: [
          { min: 6, max: 16, message: '密码长度在6-16个字符', trigger: 'blur' },
          { required: true, message: '密码为必填项', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ],
      },
      // checkPassword:'',
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var params = new URLSearchParams();
          params.append('id','')
          params.append('userName', this.ruleForm.userName);
          params.append('userPhone', this.ruleForm.userPhone);
          params.append('password',this.ruleForm.password);
          params.append('userSex', this.ruleForm.userSex);
          if(this.ruleForm.userBirth==null){
            params.append('userBirth', '');
          }else {
            params.append('userBirth', this.ruleForm.userBirth);
          }
          params.append('userDesc', this.ruleForm.UserDesc);
          params.append('userAvatar', this.ruleForm.userAvatar);
          this.$axios({
            method: 'post',
            url: "/user/insertUser",
            data: params
          })
            .then(res => {
              if(res.data.code==500) {
                this.$message({
                  showClose: true,
                  message: res.data.message,
                  type: 'error'
                });
              }
              if(res.data.code==200){
                this.$message({
                  showClose: true,
                  message: res.data.message,
                  type: 'success'
                });
                this.$router.push({name:'signUpSuccess'})
              }
            })
            .catch(err => {
              console.log(err);
            });

        } else {
          console.log('提交失败');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleAvatarSuccess(res, file) {
      this.file_URL = res;
      this.file_name = file.name;
      console.log("文件地址："+this.file_URL);
      console.log("文件名字："+this.file_name);
      this.ruleForm.userAvatar = this.file_URL.toString();
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG&&!isPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return (isJPG||isPNG) && isLt2M;
    },
    getDefaultAvatar(){
      this.$axios.get("/user/getDefaultAvatar")
        .then(res => {
          this.ruleForm.userAvatar = res.data.data;
        }).catch(err => {
        console.log(err);
      })
    }
  },
  // 过滤手机号码填写中非数字
  watch:{
    "ruleForm.userPhone": function(curVal, oldVal) {
      if (!curVal) {
        this.ruleForm.userPhone = "";
        return false;
      }
      // 实时把非数字的输入过滤掉
      this.ruleForm.userPhone = curVal.match(/\d/gi) ? curVal.match(/\d/gi).join("") : "";
    }
  },

  mounted() {
     this.getDefaultAvatar();
  }
}
</script>
