<template>
  <div style="margin-top: 300px;width: 30%;margin-left: 35%">
    <div style="text-align: center;">
      微小博搜索
      <el-input placeholder="请输入内容" class="input-with-select" style="margin-bottom: 40px" v-model="searchInfo">
        <el-button @click="" slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>
    </div>
    <div>
      <el-card style="width: 450px;padding-bottom: 30px;float: left" shadow="hover">
        <div style="margin-bottom: 10px"><span style="font-size: 14px;">今日热搜：</span></div>
        <el-divider content-position="right"></el-divider>
        <div v-for="(item,index) in hotWord" :key="item.value">
          <div>
            <span style="color: #f26d5f;font-size: 16px;font-family:'黑体';font-weight: 600">{{ index + 1 }} </span>
            <span style="font-size: 12px">{{ item.value }}</span>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  name: "searchIndex",
  data() {
    return {
      searchInfo: '',
      hotWord: [],
    }
  },
  methods: {
    // 搜索
    search() {
      if (this.searchInfo == '') {

      } else {
        sessionStorage.setItem("searchInfo", this.searchInfo);
        this.$router.push({name: 'searchPage'});
      }
      this.reload();
    },
    //获取热搜词汇
    getHotWord() {
      this.$axios.get("/search/getHotWord", {
        // withCredentials: true,
      })
        .then(res => {
          console.log(res);
          this.hotWord = res.data;
        }).catch(err => {
        console.log(err)
        this.$notify.error({
          title: '失败',
          message: "查询失败，未能找到符合该条件的用户",
        });
      })
    },
    // 初始化
    init() {
      this.getHotWord();
    },
  },
  mounted() {
    this.init();
  }
}
</script>

<style scoped>

</style>
