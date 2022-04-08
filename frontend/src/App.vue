<script setup>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import Main from "./components/Main.vue";
</script>

<template>
  <el-container class="container">
    <el-header>
      <Header/>
    </el-header>

    <el-main style="margin-top: 20px">
      <Main />
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

.container {
  padding-bottom: 60px;
  margin: -8px -8px -48px;
  background: radial-gradient(circle at center, #555, #000);
  height: 100vh;
}
</style>
