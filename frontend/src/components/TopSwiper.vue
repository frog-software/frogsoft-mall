<script setup lang="ts">
import { ref } from "vue";

interface TopSwiperItem {
  title: string,
  subtitle: string,
  image: string,
}

const topSwiper = ref<TopSwiperItem[]>([
  {
    title: 'AAAAAAAA',
    subtitle: 'aaaaaaa',
    image: 'https://images.unsplash.com/photo-1648737119422-2680a7e39089?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80',
  },
  {
    title: 'BBBBBBBB',
    subtitle: 'bbbbbbbb',
    image: 'https://images.unsplash.com/photo-1638913662415-8c5f79b20656?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
  },
  {
    title: 'AAAAAAAA',
    subtitle: 'aaaaaaa',
    image: 'https://images.unsplash.com/photo-1640622658799-54e6039d189b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
  },
])

let timer         = ref<any>(null);
let activeIndex   = ref<number>(0)
const activeImage = ref<string>('https://images.unsplash.com/photo-1638913662415-8c5f79b20656?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80')

const stopAutoPlay = () => {
  if (timer) {
    clearInterval(timer)
  }
}

const startAutoPlay = () => {
  timer = setInterval(() => {
    activeIndex.value++
    if (activeIndex.value >= topSwiper.value.length)
      activeIndex.value = 0

    changeBanner(activeIndex.value)
  }, 5000)
}

const changeBanner = (idx: number) => {
  stopAutoPlay()
  activeIndex.value = idx
  activeImage.value = topSwiper.value[idx].image
  startAutoPlay()
}

</script>

<template>
  <div class="container">
    <el-image class="banner" :src="activeImage" fit="cover"/>
    <div class="title-box">
      <div :class="['title', idx === activeIndex ? 'active' : '']" v-for="(i, idx) in topSwiper" @mouseover="changeBanner(idx)">
        {{ i.title }}
        <text>{{ i.subtitle }}</text>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "TopSwiper"
}
</script>

<style scoped>
.container {
  height: 540px;
  width: 85vw;
  margin: 36px 0 0;

  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  border-radius: 24px;
}

.banner {
  width: 100%;
  max-width: 1920px;
  background-size: cover;
  background-position: center;
}

.title-box {
  width: 24vw;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  position: absolute;
  top: 0;
  right: 0;
  color: white;
  backdrop-filter: blur(20px);
  padding-top: 20px;
}

.title {
  text-indent: 2vw;
  line-height: 54px;
  font-size: 20px;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  transition: font-size 0.1s;
  cursor: pointer;
}

.title text {
  font-size: 18px;
  opacity: 0;
}

.title.active {
  color: #c1ab85;
  font-size: 24px;
  font-weight: bold;
}

.title.active text {
  opacity: 1;
}

</style>
