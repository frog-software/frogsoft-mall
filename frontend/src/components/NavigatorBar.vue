<script setup lang="ts">
import { ref, watch } from "vue";
import { router } from '../router'

interface RouterLinkItem {
  content: string
  pathName: string
}

const routerLinkList = ref<RouterLinkItem[]>([
  {
    content: '首页',
    pathName: 'ShopPage',
  },
  {
    content: '我的商店',
    pathName: 'MyShopPage',
  },
  {
    content: '关于我们',
    pathName: 'AboutPage'
  },
])

const switchTab = (index: number) => {
  document.getElementsByName('dot').forEach(i => {
    if ((i as HTMLInputElement).checked) {
      (i as HTMLInputElement).checked = false
    }
  })

  if (index == -2) return
  else if (index == -1) {
    (document.getElementById('HomePage') as HTMLInputElement).checked = true
  } else {
    (document.getElementById('tab-' + index) as HTMLInputElement).checked = true
  }
}

watch(router.currentRoute, (newVal) => {
  if (newVal.fullPath === '/') switchTab(-1)
  else {
    let isTab = ref<boolean>(false)

    routerLinkList.value.forEach((i, idx) => {
      if (i.pathName === newVal.name) {
        switchTab(idx)
        isTab.value = true
      }
    })

    if (!isTab.value) switchTab(-2)
  }
})

</script>

<template>
  <div style="display: flex; justify-content: right; align-items: center">
    <div style="position: absolute; left: 240px">
      <input type="radio" name="dot" id="HomePage" class="input-dot" disabled checked/>
      <el-col style="display: flex; flex-direction: column; align-items: center">
        <router-link to="/" class="router-item" @click.native="switchTab(-1)">
          FrogSoft Mall
        </router-link>
        <div class="under-point"></div>
      </el-col>
    </div>

    <div class="router-group" v-for="(i, idx) in routerLinkList">
      <input type="radio" name="dot" :id="'tab-' + idx" class="input-dot" disabled/>
      <el-col style="display: flex; flex-direction: column; align-items: center">
        <router-link :to="{ name: i.pathName }" class="router-item" @click.native="switchTab(idx)">{{ i.content }}</router-link>
        <div class="under-point"></div>
      </el-col>
    </div>
  </div>
</template>

<script lang="ts">

export default {
  name: "test"
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

#HomePage ~ .el-col .router-item {
  font-size: 32px;
  font-family: 'Arial Black';
  color: white;
}

.router-group .under-point {
  margin-top: 6px;
}

.input-dot:checked ~ .el-col .under-point {
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
