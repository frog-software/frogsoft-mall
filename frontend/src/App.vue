<script setup>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
</script>
<template>
  <el-header>
    <el-header>
      <Header/>
    </el-header>

    <el-main>
      <router-view/>
    </el-main>

    <el-footer>
      <Footer/>
    </el-footer>
  </el-header>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
