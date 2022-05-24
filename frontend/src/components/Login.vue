<script setup lang="ts">
import { ElMessage } from "element-plus";
import { ref } from "vue";
import { useStore } from "../store";

const store = useStore();
const username = ref<string>("");
const password = ref<string>("");

function userLoginInHeader() {
  if (!username.value) ElMessage.warning("请输入用户名");
  else if (!password.value) ElMessage.warning("请输入密码");
  else store.dispatch("login", { username, password });
}
</script>

<template>
  <el-tooltip effect="customized" placement="bottom">
      <el-avatar
          :size="56"
          style="
        /*box-shadow: 0 0 64px #f6eacc;*/
        border: none;
        background-color: transparent;
      "
          effect="customized"
          :src="store.state.info?.avatar || '/avataaars.svg'"
      />


    <template #content>
      <div class="loginRoot">
        <el-row v-if="store.getters.hasLogin" justify="center">
          <el-button color="#c1ab85" @click="store.commit('logout')" style="color: white">
            退出登录
          </el-button>
        </el-row>
        <div v-else>
          <div class="loginInputData">
            <input type="text" required v-model="username" title=""/>
            <div class="loginUnderLine" />
            <label>你的账户</label>
          </div>

          <div class="loginInputData" style="margin-top: 36px">
            <input type="password" required v-model="password" title=""/>
            <div class="loginUnderLine" />
            <label>你的密码</label>
          </div>

          <el-button
            color="#c1ab85"
            style="color: white; margin-top: 36px; width: 100%"
            @click="userLoginInHeader"
          >
            登录
          </el-button>
        </div>
      </div>
    </template>
  </el-tooltip>
</template>

<script lang="ts">
export default {
  name: "Login",
};
</script>

<style scoped>
* {
  outline: none;
  box-sizing: border-box;
}

label {
  font-weight: normal;
}

.loginRoot {
  width: 300px;
  background-color: transparent;
  padding: 20px;
  margin-top: 20px;
}

.loginRoot .loginInputData {
  position: relative;
  width: 100%;
  height: 40px;
}

.loginRoot .loginInputData input {
  width: 100%;
  height: 100%;
  margin-top: 2px;
  border: none;
  font-size: 16px;
  border-bottom: 2px solid transparent;
  background-color: transparent;
  font-weight: normal;
  color: white;
}

.loginRoot .loginInputData input:focus ~ label {
  transform: translateY(-25px);
  font-size: 15px;
  color: #f6eacc;
}

input:valid ~ label {
  transform: translateY(-25px);
  font-size: 15px;
  color: transparent;
}

.loginRoot .loginInputData label {
  position: absolute;
  bottom: 10px;
  left: 0;
  color: #808080;
  pointer-events: none;
  transition: all 0.2s ease;
}

.loginRoot .loginInputData .loginUnderLine {
  position: absolute;
  bottom: 0;
  height: 2px;
  width: 100%;
  background: #f6eacc;
  transform: scaleX(0);
  transition: all 0.2s ease;
}

.loginRoot .loginInputData input:focus ~ .loginUnderLine,
.loginRoot .loginInputData input:valid ~ .loginUnderLine {
  transform: scaleX(1);
}
</style>

<style>
.el-popper.is-customized {
  /* Set padding to ensure the height is 32px */
  padding: 6px 12px;
  background: radial-gradient(circle at center, #2c2b2a, #1c1d20);
  /*box-shadow: 10px 10px 15px #1c1d20;*/
  border-radius: 8px;
  /*box-shadow: 10px 10px 20px rgba(0, 0, 0, 0.2);*/
  font-family: 微軟正黑體;
  color: white;
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #c1ab85, #f6eacc);
  right: 0;
}
</style>
