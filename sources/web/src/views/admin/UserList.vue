<template>
  <div>
    <el-table
      v-loading="loading"
      :data="users"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <div><el-form-item label="生日：">
              <span v-if="props.row.userBirth">{{ props.row.userBirth }}</span>
              <span v-if="!props.row.userBirth">用户暂时未设置生日</span>
            </el-form-item></div>
            <div><el-form-item label="性别：">
              <span v-if="props.row.userSex">{{props.row.userSex}}</span>
              <span v-if="!props.row.userSex">用户暂时未设置性别</span>
            </el-form-item></div>
            <div><el-form-item label="简介：">
              <span v-if="props.row.userDesc">{{ props.row.userDesc }}</span>
              <span v-if="!props.row.userDesc">用户暂时未设置简介</span>
            </el-form-item></div>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="ID"
        width="50"
        prop="id">
      </el-table-column>
      <el-table-column
        label="头像"
        align="center">
        <template slot-scope="scope">
          <el-popover placement="top-start" title="" trigger="hover">
            <el-avatar :src="scope.row.userAvatar" alt="" style="width: 150px;height: 150px"></el-avatar>
            <img slot="reference" :src="scope.row.userAvatar" style="width: 30px;height: 30px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="用户名"
        prop="userName">
      </el-table-column>
      <el-table-column
        label="手机号"
        prop="userPhone">
      </el-table-column>
      <el-table-column label="操作" align="right">
        <template slot="header" slot-scope="scope">
          <el-input
            size="mini"
            v-model="search"
            @change="getAllUser(current_page,limit)"
            placeholder="输入关键字搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">
            <i class="el-icon-edit"></i>
            编辑
          </el-button>
          <template v-if="scope.row.enabled">
            <el-popconfirm
              confirmButtonText='确认'
              cancelButtonText='取消'
              icon="el-icon-info"
              iconColor="red"
              title="确认注销账号吗？"
              @onConfirm="handleDelete(scope.$index, scope.row)"

            >
              <el-button
                size="mini"
                type="warning"
                slot="reference"
                >
                <i class="el-icon-switch-button"></i>
                注销
              </el-button>
            </el-popconfirm>
          </template>
          <template v-if="!scope.row.enabled">
            <el-popconfirm
              confirmButtonText='确认'
              cancelButtonText='取消'
              icon="el-icon-info"
              iconColor="red"
              title="确认恢复账号吗？"
              @onConfirm="handleDelete(scope.$index, scope.row)"
            >
              <el-button
                size="mini"
                type="danger"
                slot="reference"
              >
                <i class="el-icon-switch-button"></i>
                已注销
              </el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <div class="block" style="margin-top: 20px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current_page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size=limit
        layout="total, sizes, prev, pager, next, jumper"
        :total=count>
      </el-pagination>
    </div>
  </div>

</template>

<script>
export default {
  name: "UserList",
  inject:['reload'],
  data(){
    return{
      users:[],
      current_page:1,
      limit: 10,
      count:0,
      loading:true,
      search:'',
    }
  },
  methods:{
    getAllUser(current_page,limit){
        var params = new URLSearchParams();
        params.append('page', current_page);
        params.append('limit', limit);
        params.append('userName',this.search)
        this.$axios({
          method: 'post',
          url: "/admin/getAllUser",
          data: params
        })
          .then(res => {
            this.users = res.data.data;
            console.log(this.users)
            this.count = res.data.count;
            this.loading = false;
            if(this.count==0) {
              this.$notify.error({
                title: '失败',
                message: "查询失败，未能找到符合该条件的用户",
              });
            }
          })
          .catch(err => {
            console.log(err);
          });

    },
    handleEdit(index, row) {
      sessionStorage.setItem("id",row.id);
      this.$router.push('/EditUser')
    },
    handleDelete(index, row) {
      var enabled;
      if(row.enabled){
        enabled = 0;
      }else {
        enabled = 1;
      }
      this.$axios.get("/admin/deleteUserById?id="+row.id+"&enabled="+!row.enabled)
      .then(res => {
        this.$message({
          message:"成功!",
          type:"success"
        });
        this.reload()
      })
    },
    handleSizeChange(val) {
      this.getAllUser(this.current_page,val);
    },
    handleCurrentChange(val) {
      this.getAllUser(val,this.limit);
    },
    onConfirm(){
      console.log("hhhh")
    },
  },
  mounted() {
    this.getAllUser(this.current_page,this.limit);
  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
