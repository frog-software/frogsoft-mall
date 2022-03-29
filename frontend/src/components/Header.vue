<template>
  <el-row
      align="middle"
      justify="center"
      type="flex"
  >
    <el-col :span="7">
      <router-link to="/">
        <img
            src="FrogsoftMall-Black.svg"
            alt="网站页眉LOGO"
            style="max-width: 25vw"
        >
      </router-link>
    </el-col>
    <el-col :span="6">
      <el-input
          v-model:value="searchContent"
          placeholder="开始搜索"
      />
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      searchContent:''
    }
  }
}
</script>

<style scoped>

</style>
