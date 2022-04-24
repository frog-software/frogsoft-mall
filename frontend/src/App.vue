<script setup>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";

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
}

.root {
  width: calc(99vw + 2px);
  margin-left: -8px;
  /*background: radial-gradient(circle at center, #2d2c2b, #1c1d20);*/
  background: #010101;
}

.container {
  margin: -8px -16px -48px -8px;
  width: 100%;
  /*height: 100vh;*/
}


</style>
