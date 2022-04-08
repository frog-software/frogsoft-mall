<script setup lang="ts">

import Login from "./Login.vue";
</script>

<template>
  <div style="padding: 24px 0 24px">
    <el-row
        align="middle"
        type="flex"
    >
      <el-col :span="1" :offset="4">
        <img src="/frogsoft.svg" class="logoImage"/>
      </el-col>

      <el-col :span="4">
        <el-button type="text">
          <div class="logoText">
            <p class="logoTextContent">Frogsoft Mall</p>
          </div>
        </el-button>
      </el-col>

      <el-col :span="1" :offset="8">
        <Login />
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
}

.logoTextContent {
  color: #333333;
  text-transform: uppercase;
  font-size: 52px;
  font-weight: bold;
  font-family: monospace;
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
