<script setup lang="ts">
import Login        from "./Login.vue";
import RotateMenu   from "./DrawerMenu.vue";
import NavigatorBar from "./NavigatorBar.vue";
import test from "./test.vue";

</script>

<template>
  <div style="margin-top: 20px; user-select: none;">
    <el-row
        type="flex"
        style="align-items: center"
    >
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

      <el-col :span="12" :offset="3">
        <NavigatorBar />
      </el-col>

      <el-col :span="1" :offset="1">
        <Login />
      </el-col>

      <el-col :span="1" :offset="1">
        <RotateMenu />
      </el-col>
    </el-row>
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

</style>
