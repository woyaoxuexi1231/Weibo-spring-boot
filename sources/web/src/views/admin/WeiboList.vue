<template>
  <div>
    <el-table
      v-loading="loading"
      :data="weibos"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <div><el-form-item label="微博正文：">
              <span >{{ props.row.weiboText }}</span>
            </el-form-item></div>
            <div><el-form-item label="微博配图：">
              <span v-if="props.row.imageList!=null">
                <span v-for="i in props.row.imageList" :key="i.index">
                  <img :src="i.url" style="height: 120px;margin-left: 20px;margin-top: 10px">
                </span>
              </span>
              <span v-if="props.row.imageList==null">此微小博未配图</span>
            </el-form-item></div>
            <div><el-form-item label="微博视频：">
              <span v-if="props.row.video!=null">
                <video
                  controls
                  style="height: 120px;margin-left: 20px;margin-top: 10px">
                  <source :src="props.row.video.url">
                  您的浏览器暂不支持video标签，请更换浏览器后重试。
                </video>
              </span>
              <span v-if="props.row.video==null">此微小博没有上传相关视频</span>
            </el-form-item></div>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="微小博ID"
        prop="id">
      </el-table-column>
      <el-table-column
        label="博主名"
        prop="userName">
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="weiboTime">
      </el-table-column>
      <el-table-column
        label="评论数"
        prop="commentCount">
      </el-table-column>
      <el-table-column
        label="点赞量"
        prop="likeCount">
      </el-table-column>
      <el-table-column label="操作" align="right" width="400px">
        <template slot="header" slot-scope="scope">
          <el-input
            size="mini"
            v-model="search"
            @change="getAllWeibo(current_page,limit)"
            placeholder="输入关键字搜索"/>
        </template>
        <template slot-scope="scope">
          <template v-if="scope.row.enabled">
            <el-popconfirm
              confirmButtonText='确认'
              cancelButtonText='取消'
              icon="el-icon-info"
              iconColor="red"
              title="确认上锁吗？"
              @onConfirm="handleLock(scope.$index, scope.row)"

            >
              <el-button
                size="mini"
                type="success"
                slot="reference"
              >
                <i class="el-icon-unlock"></i>
                正常状态,点击封锁
              </el-button>
            </el-popconfirm>
          </template>
          <template v-if="!scope.row.enabled">
            <el-popconfirm
              confirmButtonText='确认'
              cancelButtonText='取消'
              icon="el-icon-info"
              iconColor="red"
              title="确认恢复此微小博吗吗？"
              @onConfirm="handleLock(scope.$index, scope.row)"
            >
              <el-button
                size="mini"
                type="danger"
                slot="reference"
              >
                <i class="el-icon-lock"></i>
                不可用(涉嫌违规,暂上锁)
              </el-button>
            </el-popconfirm>
          </template>
          <template v-if="scope.row.deleted">
            <el-popconfirm
              confirmButtonText='确认'
              cancelButtonText='取消'
              icon="el-icon-info"
              iconColor="red"
              title="确认恢复此微小博吗？"
              @onConfirm="handleDelete(scope.$index, scope.row)"
            >
              <el-button
                size="mini"
                type="danger"
                slot="reference"
              >
                <i class="el-icon-refresh-right"></i>
                已删除,点击恢复
              </el-button>
            </el-popconfirm>
          </template>
          <template v-if="!scope.row.deleted">
            <el-popconfirm
              confirmButtonText='确认'
              cancelButtonText='取消'
              icon="el-icon-info"
              iconColor="red"
              title="确认删除此微博？"
              @onConfirm="handleDelete(scope.$index, scope.row)"
            >
              <el-button
                size="mini"
                type="primary"
                slot="reference"
              >
                <i class="el-icon-warning"></i>
                正常,点击可删除
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
  name: "WeiboList",
  inject:['reload'],
  data(){
    return{
      weibos:[],
      current_page:1,
      limit: 10,
      count:0,
      loading:true,
      search:'',
    }
  },
  methods:{
    getAllWeibo(current_page,limit){
      var params = new URLSearchParams();
      params.append('page', current_page);
      params.append('limit', limit);
      params.append('weiboText',this.search)
      this.$axios({
        method: 'post',
        url: "/admin/getAllWeibo",
        data: params
      })
        .then(res => {
          this.weibos = res.data.data;
          console.log(this.weibos)
          this.count = res.data.count;
          this.loading = false;
          if(this.count==0) {
            this.$notify.error({
              title: '失败',
              message: "查询失败",
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
      this.$axios.get("/admin/deleteWeiboById?id="+row.id+"&deleted="+!row.deleted)
        .then(res => {
          this.$message({
            message:"成功!",
            type:"success"
          });
          this.reload()
        })
    },
    handleLock(index, row) {
      this.$axios.get("/admin/lockWeiboById?id="+row.id+"&enabled="+!row.enabled)
        .then(res => {
          this.$message({
            message:"成功!",
            type:"success"
          });
          this.reload()
        })
    },
    handleSizeChange(val) {
      this.getAllWeibo(this.current_page,val);
    },
    handleCurrentChange(val) {
      this.getAllWeibo(val,this.limit);
    },
    onConfirm(){
      console.log("hhhh")
    },
  },
  mounted() {
    this.getAllWeibo(this.current_page,this.limit);
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
