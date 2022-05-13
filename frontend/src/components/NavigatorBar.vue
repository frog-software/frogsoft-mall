<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { useRouter }             from "vue-router";


interface RouterLinkItem {
  content: string,
  pathName: string,
  name: string,
}

const routerLinkList = ref<RouterLinkItem[]>([
  {
    content: '首页',
    pathName: 'ShopPage',
    name: 'first',
  },
  {
    content: '关于我们',
    pathName: 'AboutPage',
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
    <div style="position: absolute; left: 240px">
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
        <router-link :to="{ name: i.pathName }" class="router-item" @click.native="switchTab(idx)">{{ i.content }}</router-link>
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
