<template>
  <el-row style="margin-top: -120px; margin-bottom: -56px; height: 880px">
    <el-col :span="9" :offset="4" style="font-family: 微軟正黑體, serif;">
      <div style="color: white; margin-top: 180px;  " class="main-text">
        <p style="margin-left: -12px; font-size: 72px; display: inline; width: auto;  font-weight: bold;" >知你所爱，寻你所需。</p>
      </div>

      <div style="height: 4px; margin: 8px -6px; background: linear-gradient(to right, white, transparent);" class="bar"/>

      <div style="color: #999999; margin-top: 24px; text-align: left" class="text">
        <p style="font-size: 18px">
          我们致力于打造一款现代电商，犹如提着油灯在黑暗中闪闪发光探索的前行者。
        </p>
      </div>

      <div style="color: #999999; margin-left: -23vw; margin-top: 64px;" class="text">
        <p>开始你的电商之旅来探索更多内容！</p>
      </div>

      <router-link :to="{ path: '/main' }" style="margin-left: -24vw; text-decoration: none">
        <el-button class="start-button" round>
          开始购物
        </el-button>
      </router-link>


      <div class="photo-text">
        <p>Lantern lighting up in the pitch darkness of the autumn night.</p>
        <p style="color: #999999; font-size: 14px; margin-top: -1em">By David Gabrić</p>
      </div>
    </el-col>

    <el-col :span="10" style="user-select: none" class="photo">
      <el-image src="/homepage-image-transparent.png" style="z-index: 0; -webkit-user-drag: none"/>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "Homepage"
}
</script>

<style scoped>
.main-text {
  animation-name: text-top-in;
  animation-iteration-count: 1;
  animation-duration: 0.8s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: forwards;
}

.text {
  animation-name: text-top-in;
  animation-iteration-count: 1;
  animation-duration: 1s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: forwards;
}

@keyframes text-top-in {
  0% {
    transform: translateY(40vh);
    opacity: 0;
  }
  100% {
    transform: translateY(0px);
    opacity: 1;
  }
}

.bar {
  animation-name: bar-in;
  animation-iteration-count: 1;
  animation-duration: 0.8s;
  animation-timing-function: ease-in-out;
  animation-delay: 0.6s;
  opacity: 0;
  animation-fill-mode: forwards;
}

@keyframes bar-in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.start-button {
  background: linear-gradient(to right, #fb902d, #ea4d2f);
  border: 0;
  color: white;
  width: 240px;
  height: 56px;
  opacity: 0;
  font-size: 16px;
  font-weight: bolder;
  margin-top: 8px;

  animation-name: start-button-in;
  animation-iteration-count: 1;
  animation-duration: 0.7s;
  animation-timing-function: ease-in-out;
  animation-delay: 0.3s;
  animation-fill-mode: forwards;
}

@keyframes start-button-in {
  0% {
    transform: translateY(35vh);
    opacity: 0;
  }
  100% {
    transform: translateY(0px);
    opacity: 1;
  }
}

.photo-text {
  color: white;
  margin-left: 8px;
  margin-top: 156px;
  font-family: 'Arial Black';
  text-align: left;
  opacity: 0;

  animation-name: bar-in;
  animation-iteration-count: 1;
  animation-duration: 0.8s;
  animation-timing-function: ease-in-out;
  animation-delay: 0.6s;
  animation-fill-mode: forwards;
}

.photo {
  opacity: 0;

  animation-name: bar-in;
  animation-iteration-count: 1;
  animation-duration: 0.8s;
  animation-timing-function: ease-in-out;
  animation-delay: 0.3s;
  animation-fill-mode: forwards;
}
</style>
