<script setup lang="ts">
import Login         from "./Login.vue";
import RotateMenu    from "./DrawerMenu.vue";
import NavigatorBar  from "./NavigatorBar.vue";
import {
  Search,
  CloseBold,
}                    from '@element-plus/icons-vue'
import { ref }       from "vue";
import { translate } from "element-plus";

const openSearchInput = () => {
  const header = document.getElementById('header-root')
  header.className = "header-disappear"

  const search = document.getElementById('searchInput')
  search.className = "search-input-appear"
}
const closeSearchInput = () => {
  const header = document.getElementById('header-root')
  header.className = "header-appear"

  const search = document.getElementById('searchInput')
  search.className = "search-input-disappear"
}

const searchContent = ref<string>('')

const goSearchDetail = () => {
  console.log(searchContent.value)
}
</script>

<template>
  <div style="margin-top: 20px; user-select: none;">
    <div id="header-root">
      <el-row type="flex" style="align-items: center">
        <!--      <el-col :span="1" :offset="3">-->
        <!--        <img src="/frogsoft.svg" class="logoImage"/>-->
        <!--      </el-col>-->

        <!--      <el-col :span="4" :offset="3">-->
        <!--        <el-button type="text">-->
        <!--          <div class="logoText">-->
        <!--            <p class="logoTextContent">Frogsoft Mall</p>-->
        <!--          </div>-->
        <!--        </el-button>-->
        <!--        <router-link to="/" style="text-decoration: none; font-size: 32px; font-family: 'Arial Black'; color: white;">-->
        <!--          FrogSoft Mall ·-->
        <!--        </router-link>-->
        <!--      </el-col>-->

        <el-col :span="13" :offset="3">
          <NavigatorBar/>
        </el-col>

        <el-col :span="1" :offset="2">
          <Login/>
        </el-col>

        <el-col :span="1" style="">
          <el-button type="primary" :icon="Search" size="large" class="button" @click="openSearchInput"/>
        </el-col>

        <el-col :span="1" style="margin-left: 18px">
          <RotateMenu />
        </el-col>
      </el-row>
    </div>

    <div id="searchInput" style="position: absolute; left: 0; right: 0; top: -100px;">
      <div style="justify-content: center; display: flex; align-items: center">
        <div class="search-input-root">
          <el-icon :size="24" color="#010101" style="cursor: pointer" @click="goSearchDetail" >
            <search/>
          </el-icon>
          <input type="text" class="search-input" v-model="searchContent"/>
        </div>
        <el-icon :size="28" color="white" style="cursor: pointer; margin-left: 8px" @click="closeSearchInput">
          <close-bold/>
        </el-icon>
      </div>
    </div>

  </div>
</template>

<script lang="ts">
export default {
  name: "Header",
  data() {
    return {}
  },

  methods: {}
}
</script>

<style scoped>
.logoImage {
  background: linear-gradient(to right, #c1ab85, #f6eacc);
  width: 48px;
  border-radius: 50%;
}

.logoText {
  justify-content: center;
  align-items: center;
  font-family: "Microsoft YaHei";
}

.logoTextContent {
  color: #333333;
  text-transform: uppercase;
  font-size: 52px;
  font-weight: bold;
  position: relative;
  padding: 0;
  margin: 0;
}

.logoTextContent:after {
  content: "Frogsoft Mall";
  color: transparent;
  position: absolute;
  left: 0;
  background: linear-gradient(to right, #c1ab85, #f6eacc);
  background-clip: content-box;
  -webkit-background-clip: text;

  clip-path: circle(50px at 0% 50%);
  animation: logoTextLight 5s infinite;
  padding: 0;
  margin: 0;
}

@keyframes logoTextLight {
  0% {
    clip-path: circle(64px at 0% 50%);
  }
  50% {
    clip-path: circle(64px at 100% 50%);
  }
  100% {
    clip-path: circle(64px at 0% 50%);
  }
}

.button {
  height: 48px;
  width: 48px;
  font-size: 30px;
  margin: auto;
  color: rgba(255, 255, 255, 0.7);
  background: transparent;
  border: none;
}

.button:hover {
  color: white;
}

.search-input-root {
  background: white;
  display: flex;
  align-items: center;
  border-radius: 16px;
  overflow: hidden;
  padding-left: 12px;
}

.search-input {
  width: 22vw;
  height: 32px;
  border-radius: 16px;
  border: none;
  outline: none;
  font-family: 微軟正黑體;
  font-size: 16px;
  margin-left: 12px;
  font-weight: bold;
}

.search-input-appear {
  opacity: 0;
  animation-name: search-input-top-in;
  animation-iteration-count: 1;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: forwards;
}

.search-input-disappear {
  opacity: 1;
  animation-name: search-input-top-out;
  animation-iteration-count: 1;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: forwards;
}

.header-appear {
  opacity: 0;
  visibility: visible;
  animation-name: header-visible;
  animation-iteration-count: 1;
  animation-duration: 0.3s;
  animation-timing-function: ease-out;
  animation-fill-mode: forwards;
}

.header-disappear {
  animation-name: header-hidden;
  animation-iteration-count: 1;
  animation-duration: 0.3s;
  animation-timing-function: ease-out;
  animation-fill-mode: forwards;
}

@keyframes search-input-top-in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
    transform: translateY(135px);
  }
}

@keyframes search-input-top-out {
  0% {
    opacity: 1;
    transform: translateY(135px);
  }
  100% {
    opacity: 0;
    transform: translateY(-100px);
  }
}

@keyframes header-visible {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes header-hidden {
  0% {
    opacity: 1;
  }
  100% {
    opacity: 0;
    visibility: hidden;
  }
}
</style>
