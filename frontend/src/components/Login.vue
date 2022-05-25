<script setup lang="ts">
import { ElMessage, ElNotification } from "element-plus";
import { ref }                       from "vue";
import { useStore }                  from "../store";
import { UserRegisterPost } from "../types/user";
import { login, register }  from "../services/user/user";

const store    = useStore();
const username = ref<string>("");
const password = ref<string>("");

const registerDialogOnShow = ref<boolean>(false);

function userLoginInHeader() {
  if (!username.value) ElMessage.warning("请输入用户名");
  else if (!password.value) ElMessage.warning("请输入密码");
  else store.dispatch("login", { username: username.value, password: password.value });
}

const registerForm = ref<UserRegisterPost>({
  username: '',
  nickname: '',
  password: '',
  phone: '',
  code: '',
  avatar: '',
})

const userRegister = () => {
  if (!registerForm.value.username || !registerForm.value.nickname || !registerForm.value.password
      || !registerForm.value.phone || !registerForm.value.code || !registerForm.value.avatar) {
    ElNotification({
      title: '注册失败',
      message: '注册资料填写不完整',
      type: 'error',
    })
    return
  }

  register(registerForm.value).then(res => {
    ElNotification({
      title: '注册成功',
      type: 'success',
    })

    registerDialogOnShow.value = false

    username.value = registerForm.value.username
    password.value = registerForm.value.password
    userLoginInHeader()
  })
}
</script>

<template>
  <el-tooltip effect="customized" placement="bottom">
    <el-avatar
        :size="56"
        style="border: none; background-color: transparent;"
        effect="customized"
        :src="store.state.info?.avatar || '/avataaars.svg'"
    />

    <template #content>
      <div class="loginRoot">
        <el-row v-if="store.state.username" justify="center">
          <el-button color="#c1ab85" @click="store.commit('logout')" style="color: white">
            退出登录
          </el-button>
        </el-row>
        <div v-else>
          <div class="loginInputData">
            <input type="text" required v-model="username" title=""/>
            <div class="loginUnderLine"/>
            <label>你的账户</label>
          </div>

          <div class="loginInputData" style="margin-top: 36px">
            <input type="password" required v-model="password" title=""/>
            <div class="loginUnderLine"/>
            <label>你的密码</label>
          </div>

          <el-button color="#c1ab85" class="login-button" @click="userLoginInHeader">登录</el-button>
          <el-button color="transparent" class="register-button" @click="() => registerDialogOnShow = true">
            注册
          </el-button>


        </div>
      </div>
    </template>
  </el-tooltip>

  <el-dialog
      v-model="registerDialogOnShow"
      title="用户注册"
      width="600px"
  >
    <div>
      <el-form :model="registerForm" label-width="auto" label-position="right">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input v-model="registerForm.nickname" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="registerForm.phone" style="width: 240px"/>
          <el-button style="margin-left: 16px">发送验证码</el-button>
        </el-form-item>
        <el-form-item label="验证码">
          <el-input v-model="registerForm.code" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="registerForm.avatar" style="width: 480px"/>
        </el-form-item>
        <el-image :src="registerForm.avatar" style="width: 100px; height: 100px"
                  :preview-src-list="[registerForm.avatar]"
        />
        <el-image :src="registerForm.avatar" style="width: 60px; height: 60px; margin-left: 20px"/>
        <el-image :src="registerForm.avatar" style="width: 30px; height: 30px; margin-left: 20px"/>
      </el-form>

      <div style="text-align: left">
        <el-button type="primary" style="width: 120px; margin-left: 72px; margin-top: 24px" @click="userRegister">注册
        </el-button>
      </div>
    </div>

  </el-dialog>
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

.login-button {
  color: white;
  margin: 36px 0 0;
  width: 50%;
  border: 1px solid #f6eacc;
  border-right: none;
  border-radius: 4px 0 0 4px;
}

.register-button {
  color: #f6eacc;
  margin: 36px 0 0;
  width: 50%;
  border: 1px solid #f6eacc;
  border-left: none;
  border-radius: 0 4px 4px 0;
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
