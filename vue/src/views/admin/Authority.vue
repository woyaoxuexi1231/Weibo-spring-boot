<template>
  <div>
    <el-table
      :data="users"
      v-loading="loading"
      style="width: 100%">
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
        width="300px"
        prop="userName">
      </el-table-column>
      <el-table-column
        label="手机号"
        prop="userPhone"
        width="300px">
      </el-table-column>
      <el-table-column label="操作" align="right" style="width: 100%">

        <template slot="header" slot-scope="scope">
          <el-input
            size="mini"
            v-model="search"
            @change="getAllUser(current_page,limit)"
            placeholder="输入关键字搜索"/>
        </template>

        <template slot-scope="scope">
          <div style="margin-top: 20px">
            <el-radio-group v-model="scope.row.roleID" size="small">
              <el-radio label="1" @change="authorityChange(scope.$index, scope.row, 1)" border>普通用户</el-radio>
              <el-radio label='3' @change="authorityChange(scope.$index, scope.row, 3)" border>管理员</el-radio>
            </el-radio-group>
          </div>
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
  name: "Authority",
  data() {
    return {
      users:[],
      current_page:1,
      limit: 10,
      count:0,
      loading:true,
      search:'',
    }
  },
  methods:{
    // 初始化
    init(){
      this.getAllUser(this.current_page,this.limit);
    },
    // 获取所有用户
    getAllUser(current_page,limit){
      var params = new URLSearchParams();
      params.append('page', current_page);
      params.append('limit', limit);
      params.append('userName',this.search)
      this.$axios({
        method: 'post',
        url: "/admin/authorityGetAllUser",
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
    // 修改用户权限(当前页行号，当前行信息，修改成的权限等级：1表示用户，3表示管理员)
    authorityChange(index, row, roleID){
      var params = new URLSearchParams();
      params.append('uid', row.id);
      params.append('rid', roleID);
      this.$axios({
        method: 'post',
        url: "/admin/authorityChange",
        data: params
      })
        .then(res => {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        })
        .catch(err => {
          this.$message.error('修改错误');
        });
    },
    handleSizeChange(val) {
      this.getAllUser(this.current_page,val);
    },
    handleCurrentChange(val) {
      this.getAllUser(val,this.limit);
    },
  },
  mounted() {
    this.init();
  }
}
</script>

<style scoped>

</style>
