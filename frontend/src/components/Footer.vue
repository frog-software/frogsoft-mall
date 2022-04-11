<template>
  <div style="margin-top: 60px;">
    <el-row
        align="middle"
        justify="space-around"
        type="flex"
    >
      <el-col :span="3" :offset="5">
        <img
            src="/FrogsoftMall-White.svg"
            alt="网站页脚Footer"
            style="width: 20vw"
        >
      </el-col>
      <el-col :span="14" style="color: white; ">
        <span>相关链接：</span>

        <el-link
            href="https://github.com/frog-software/frogsoft-mall"
            style="color: white;padding-left: 8px;font-size: large"
        >
          Github - Frogsoft Mall
        </el-link>

        <div>
          Copyrights © {{ new Date().getFullYear() }}
          <el-link herf="https://github.com/frog-software" style="color: white;font-size: large">
            Frogsoft
          </el-link>
          . All Rights Reserved.
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>

</style>
