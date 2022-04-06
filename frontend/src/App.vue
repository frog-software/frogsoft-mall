<script setup>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
</script>

<template>
  <el-container style="margin-left: -28px; margin-right: -28px; margin-top: -8px">
    <el-header>
      <Header/>
    </el-header>

    <el-main>
      <router-view/>
    </el-main>

    <el-footer>
      <Footer/>
    </el-footer>
  </el-container>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /*margin-top: 60px;*/
}
</style>
