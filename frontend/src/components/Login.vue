<template>
  <el-tooltip effect="customized">
    <el-avatar :size="45" style="padding: 0; margin: 0" effect="customized"/>

    <template #content>
      <div class="loginRoot">
        <div class="loginInputData">
          <input type="text" required/>
          <div class="loginUnderLine" />
          <label>你的账户</label>
        </div>

        <div class="loginInputData" style="margin-top: 36px">
          <input type="password" required/>
          <div class="loginUnderLine" />
          <label>你的密码</label>
        </div>

        <el-button color="#c1ab85" style="color: white; margin-top: 36px; width: 100%">登录</el-button>
      </div>
    </template>
  </el-tooltip>
</template>

<script>
export default {
  name: "Login"
}
</script>

<style scoped>
*{
  outline: none;
  box-sizing: border-box;
}

label {
  font-family: "Microsoft YaHei";
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
  border: none;
  font-size: 17px;
  border-bottom: 2px solid transparent;
  background-color: transparent;
  font-family: "Microsoft YaHei";
  font-weight: normal;
  color: #eeeeee;
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
  left: 0px;
  color: #808080;
  pointer-events: none;
  transition: all 0.2s ease;
}

.loginRoot .loginInputData .loginUnderLine {
  position: absolute;
  bottom: 0px;
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
  box-shadow: 10px 10px 15px #1c1d20;
  border-radius: 8px;
  /*box-shadow: 10px 10px 20px rgba(0, 0, 0, 0.2);*/
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #c1ab85, #f6eacc);
  right: 0;
}
</style>
