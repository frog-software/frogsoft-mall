<script setup>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import Main from "./views/Main.vue";
import Homepage from "./views/Homepage.vue";
</script>

<template>
  <div class="root">
    <el-container class="container">
      <el-header style="height: 100px">
        <Header/>
      </el-header>

      <el-main >
        <router-view />

        <Footer />
      </el-main>
    </el-container>
  </div>


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

.root {
  width: 100%;
  margin-left: -8px;
  /*background: radial-gradient(circle at center, #2d2c2b, #1c1d20);*/
  background: #010101;
}

.container {
  margin: -8px -16px -48px -8px;
  height: 100vh;
}


</style>
