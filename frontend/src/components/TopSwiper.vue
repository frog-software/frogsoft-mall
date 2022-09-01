<script setup lang="ts">
import { onMounted, ref } from "vue";
import { CDN_URL }        from "../consts/urls"

interface TopSwiperItem {
  title: string,
  subtitle: string,
  image: string,
}

const topSwiper = ref<TopSwiperItem[]>([
  {
    title: 'APPAREL',
    subtitle: 'Free worldwide shipping',
    image: `${CDN_URL}/topswiper-example-1.jpg`,
  },
  {
    title: 'ACCESSORIES',
    subtitle: 'Legendary collection',
    image: `${CDN_URL}/topswiper-example-2.jpg`,
  },
])

const timer         = ref<number>(0);
const activeIndex   = ref<number>(0)
const activeImage = ref<string>(topSwiper.value[activeIndex.value].image)

const stopAutoPlay = () => {
  if (timer.value) {
    clearInterval(timer.value)
  }
}

const startAutoPlay = () => {
  timer.value = setInterval(() => {
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

startAutoPlay()

onMounted(() => {
  let data = sessionStorage.getItem('TopSwiperBlurLoading')

  if (data == 'false') {
    let elem = document.getElementsByClassName('banner')[0]
    elem.classList.add('banner-blur-show')
    sessionStorage.setItem('TopSwiperBlurLoading', 'true')
  }
})
</script>

<template>
  <div class="container">
    <el-image class="banner" :src="activeImage" fit="cover" lazy/>
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
  height: 500px;
  width: 100%;
  margin-bottom: 36px;

  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  animation: topSwiper-in;
  animation-iteration-count: 1;
  animation-duration: 0.5s;
  animation-timing-function: ease-out;
  animation-fill-mode: forwards;
}

@keyframes topSwiper-in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.banner {
  width: 100%;
  /*height: 100%;*/
  max-width: 1920px;
  background-size: cover;
  background-position: center;
}

.banner-blur-show {
  filter: blur(20px);
  animation: blur-show 1.4s ease-in-out 1 forwards;
}

.banner-blur-show:before {
  content: '';
  width: 100%;
  height: 100%;
  background-color: #42b983;
}

@keyframes blur-show {
  0% {
    filter: blur(20px);
  }
  100% {
    filter: none;
  }
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
  padding-top: 60px;
  margin-right: -1px;
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
  color: #f67129;
  font-size: 24px;
  font-weight: bold;
}

.title.active text {
  opacity: 1;
}

</style>
