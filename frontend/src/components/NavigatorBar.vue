<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { useRouter }             from "vue-router";


interface RouterLinkItem {
  content: string,
  path: string,
  name: string,
}

const routerLinkList = ref<RouterLinkItem[]>([
  {
    content: '首页',
    path: '/main/shop',
    name: 'first',
  },
  {
    content: '关于我们',
    path: '/main/test1',
    name: 'second',
  },
])

const switchTab = (index: number) => {
  document.getElementsByName('dot').forEach(i => {
    if ((i as HTMLInputElement).checked) {
      (i as HTMLInputElement).checked = false
    }
  })

  switch (index) {
    case -1:
      (document.getElementById('Homepage') as HTMLInputElement).checked = true
      break
    case 0:
      (document.getElementById('first') as HTMLInputElement).checked = true
      break
    case 1:
      (document.getElementById('second') as HTMLInputElement).checked = true
      break
    case 2:
      (document.getElementById('third')as HTMLInputElement).checked = true
      break
    default:
      break
  }
}

const routerCheck = () => {

  if ('/' === useRouter().currentRoute?.value.fullPath) {
    (document.getElementById('Homepage') as HTMLInputElement).checked = true
    return
  }

  routerLinkList.value.forEach(i => {
    if (i.path === useRouter().currentRoute?.value.fullPath)
      (document.getElementById(i.name) as HTMLInputElement).checked = true
  })
}

</script>

<template>
  <div style="display: flex; justify-content: right; align-items: center">
    <div style="position: absolute; left: 14vw">
      <input type="radio" name="dot" id="Homepage" class="input-dot" disabled checked/>
      <el-col style="display: flex; flex-direction: column; align-items: center">
        <router-link to="/" class="router-item" @click.native="switchTab(-1)">
          FrogSoft Mall
        </router-link>
        <div class="under-point"></div>
      </el-col>
    </div>

    <div class="router-group" v-for="(i, idx) in routerLinkList">
      <input type="radio" name="dot" :id="i.name" class="input-dot" disabled/>
      <el-col style="display: flex; flex-direction: column; align-items: center">
        <router-link :to="{path: i.path}" class="router-item" @click.native="switchTab(idx)">{{ i.content }}</router-link>
        <div class="under-point"></div>
      </el-col>
    </div>
  </div>
</template>

<script lang="ts">

export default {
  name: "test",
  setup(){

  }
}
</script>

<style scoped>
.input-dot {
  display: none;
}

.router-group {
  display: inline-block;
  margin: 0 24px;
}

.router-item {
  text-decoration: none;
  font-family: 微軟正黑體;
  color: white;
  font-size: 24px;
  font-weight: bolder;
  text-align: right;
}

.under-point {
  width: 8px;
  height: 8px;
  background: #f6eacc;
  border-radius: 50%;
  opacity: 0;
}

.router-item:hover ~ .under-point {
  animation-name: dot-top-in;
  animation-iteration-count: 1;
  animation-duration: 0.2s;
  animation-timing-function: ease-in-out;
  opacity: 1;
}

#Homepage ~ .el-col .router-item {
  font-size: 32px;
  font-family: 'Arial Black';
  color: white;
}

.router-group .under-point {
  margin-top: 6px;
}

#Homepage:checked ~ .el-col .under-point,
#first:checked ~ .el-col .under-point,
#second:checked ~ .el-col .under-point,
#third:checked ~ .el-col .under-point {
  opacity: 1;
  animation: none;
}

@keyframes dot-top-in {
  0% {
    transform: translateY(45px);
    opacity: 0;
  }
  100% {
    transform: translateY(0px);
    opacity: 1;
  }
}
</style>

<!--<script setup>-->

<!--const switchTab = (index) => {-->
<!--  console.log(index)-->

<!--  document.getElementsByName('tab').forEach(i => {-->
<!--    i.checked = false;-->
<!--  })-->

<!--  switch (index) {-->
<!--    case 1:-->
<!--      document.getElementById('first').checked = true-->
<!--      break-->
<!--    case 2:-->
<!--      document.getElementById('second').checked = true-->
<!--      break-->
<!--    case 3:-->
<!--      document.getElementById('third').checked = true-->
<!--      break-->
<!--    default:-->
<!--      break-->
<!--  }-->
<!--}-->

<!--</script>-->

<!--<template>-->
<!--  <div class="wrapper">-->
<!--    <nav>-->
<!--      <input type="radio" name="tab" id="first" checked disabled>-->
<!--      <input type="radio" name="tab" id="second" disabled>-->
<!--      <input type="radio" name="tab" id="third" disabled>-->

<!--      <label for="first" class="first">-->
<!--        <router-link :to="{path: '/main/shop'}" class="router-link" @click.native="switchTab(1)">-->
<!--          商城主页-->
<!--        </router-link>-->
<!--      </label>-->
<!--      <label for="second" class="second">-->
<!--        <router-link :to="{path: '/main/test1'}" class="router-link" @click.native="switchTab(2)">-->
<!--          特色优选-->
<!--        </router-link>-->
<!--      </label>-->
<!--      <label for="third" class="third">-->
<!--        <router-link :to="{path: '/main/test1'}" class="router-link" @click.native="switchTab(3)">-->
<!--          为你推荐-->
<!--        </router-link>-->
<!--      </label>-->

<!--      <div class="tab"></div>-->
<!--    </nav>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  name: "NavigatorBar"-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--/** {*/-->
<!--/*  margin: 0;*/-->
<!--/*  padding: 0;*/-->
<!--/*  box-sizing: border-box;*/-->
<!--/*}*/-->

<!--.wrapper {-->
<!--  background: transparent;-->
<!--  line-height: 48px;-->
<!--  box-shadow: 0 0 6px #f6eacc;-->
<!--  border: 1px solid #f6eacc;-->
<!--  border-radius: 24px;-->
<!--}-->

<!--.wrapper nav {-->
<!--  display: flex;-->
<!--  position: relative;-->
<!--}-->

<!--.wrapper nav label {-->
<!--  flex: 1;-->
<!--  width: 100%;-->
<!--  position: relative;-->
<!--  z-index: 1;-->
<!--}-->

<!--.router-link {-->
<!--  position: relative;-->
<!--  z-index: 2;-->
<!--  color: #c1ab85;-->
<!--  font-size: 18px;-->
<!--  font-weight: bold;-->
<!--  text-decoration: none;-->
<!--  user-select: none;-->
<!--}-->

<!--.wrapper nav input {-->
<!--  display: none;-->
<!--}-->

<!--.wrapper nav .tab {-->
<!--  position: absolute;-->
<!--  height: 100%;-->
<!--  width: 33%;-->
<!--  left: 0;-->
<!--  bottom: 0;-->
<!--  border-radius: 24px;-->

<!--  /* 玻璃效果 */-->
<!--  background: rgba(255, 255, 255, 0.1);-->
<!--  box-shadow: 20px 20px 50px rgba(0, 0, 0, 0.5);-->
<!--  border-top: 1px solid rgba(255, 255, 255, 0.5);-->
<!--  border-left: 1px solid rgba(255, 255, 255, 0.5);-->
<!--  backdrop-filter: blur(5px);-->

<!--  /* 贝塞尔曲线动画过渡 */-->
<!--  transition: 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.55);-->
<!--}-->

<!--#first:checked ~ label.first .router-link,-->
<!--#second:checked ~ label.second .router-link,-->
<!--#third:checked ~ label.third .router-link {-->
<!--  color: #f6eacc;-->
<!--  transition: 0.6s;-->
<!--}-->

<!--.wrapper nav #first:checked ~ .tab {-->
<!--  left: 0;-->
<!--}-->

<!--.wrapper nav #second:checked ~ .tab {-->
<!--  left: 34%;-->
<!--}-->

<!--.wrapper nav #third:checked ~ .tab {-->
<!--  left: 67%;-->
<!--}-->

<!--</style>-->
