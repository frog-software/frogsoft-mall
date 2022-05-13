<script setup lang="ts">
import GoodsCard                       from "../components/GoodsCard.vue";
import TopSwiper                       from "../components/TopSwiper.vue";
import { onMounted, onUnmounted, ref } from "vue";
import {
  ArrowDown,
  ColdDrink,
  ArrowDownBold,
}                                      from '@element-plus/icons-vue'
import { CDN_URL }                     from "../consts/urls"

interface shopMainItem {
  image: string,
  title: string,
  description: string,
  // goodsList: goods[],
  id: number,
}

// 特殊展示的商店商品列表
const shopMainList = ref<shopMainItem[]>([
  {
    image: CDN_URL + '/shop-special-example-1.webp',
    title: '大显身手',
    description: '精彩由此开始。',
    // goodsList: testGoodsList,
    id: 1,
  },
  {
    image: CDN_URL + '/shop-special-example-2.webp',
    title: '帮手在此',
    description: '时时待命应你所需。',
    // goodsList: testGoodsList,
    id: 2,
  },
])

// 下方一般分类的商品列表
const commandGoodsList = ref<any>([
  {
    name: '新品上市',
    // goodsList
  },
  {
    name: '运动健康',
    //
  },
  {
    name: '智能家居'
  }
])

const startExplore = () => {
  document.getElementById('shop-main').scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

const autoScrollDown = (id: number) => {
  let rowList = document.getElementsByName('shop-main-row')

  rowList[id].scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

const rowAppear = document.getElementsByName('shop-main-row')

const scrollHandler = () => {
  // console.log(window.innerHeight)



  rowAppear.forEach((elem) => {
    const vwTop      = window.scrollY;
    const vwBottom   = (window.scrollY + window.innerHeight);
    const elemTop    = elem.offsetTop;
    const elemHeight = elem.offsetHeight;

    if (vwBottom > elemTop && ((vwTop - elemHeight) < elemTop)) {
      elem.classList.add("visible");
    }
  });
}

const menuIsShow = ref<boolean>(false)
const showMenu   = () => {
  menuIsShow.value = !menuIsShow.value
  for (let i = 0; i < shopMainList.value.length + commandGoodsList.value.length; i++) {
    let menuItemBar  = document.getElementById('menu-item-bar-' + i)
    let menuTextItem = document.getElementById('menu-item-name-' + i)
    let menuButton   = document.getElementById('menu-button')

    if (menuIsShow.value) {
      menuItemBar.style.transform  = 'translateY(' + (54 + i * 42) + 'px)'
      menuItemBar.style.opacity    = '1'
      menuItemBar.style.visibility = 'visible'
      menuTextItem.classList.remove('menu-item-name-disappear')
      menuTextItem.classList.add('menu-item-name-appear')
      menuButton.style.transform = 'rotate(180deg)'
    } else {
      menuItemBar.style.transform  = 'translateY(0)'
      menuItemBar.style.opacity    = '0'
      menuItemBar.style.visibility = 'hidden'
      menuTextItem.classList.remove('menu-item-name-appear')
      menuTextItem.classList.add('menu-item-name-disappear')
      menuButton.style.transform = 'rotate(0)'
    }
  }
}

const handleRollMenu = (idx: number) => {
  console.log(idx)

  if (idx < shopMainList.value.length) {
    let rowList = document.getElementsByName('shop-main-row')

    rowList[idx].scrollIntoView({
      behavior: 'smooth',
      block: 'start',
    })
  } else {
    let elem = document.getElementById('command-goods-' + (idx - shopMainList.value.length))
    if (!elem) return
    elem.scrollIntoView({
      behavior: 'smooth',
      block: 'start',
    })
  }
}

onMounted(() => {
  window.addEventListener('scroll', scrollHandler, true)
})

onUnmounted(() => {
  window.removeEventListener('scroll', scrollHandler, true)
})

</script>

<template>
  <div style="margin-bottom: 36px">
    <div style="height: calc(100vh - 100px)">
      <div style="justify-content: center; display: flex">
        <TopSwiper/>
      </div>

      <div style="position: relative">
        <p class="slogan">
          <span style="color: #c1ab85; font-weight: bold">这个商店</span>，产品都称心，体验更如意。
        </p>

        <div style="position: absolute; right: 24px; top: 0;">
          <el-affix :offset="64">
            <el-button type="primary" :icon="ArrowDownBold" class="menu-button" @click="showMenu" id="menu-button"/>

            <div v-for="(i, idx) in shopMainList.length + commandGoodsList.length"
                 :id="'menu-text-item-' + idx">
              <p :id="'menu-item-name-' + idx" class="menu-text-item" @click="handleRollMenu(idx)">{{
                  i > shopMainList.length
                      ? commandGoodsList[idx - shopMainList.length].name
                      : shopMainList[idx].title
                }}</p>
              <div class="menu-item-bar" :id="'menu-item-bar-' + idx"/>
            </div>
          </el-affix>
        </div>

        <div style="display: flex; justify-content: center">
          <div style="cursor: pointer; width: 96px; " @click="startExplore">
            <p style="color: #999999; margin: 0 0 24px">探索更多</p>
            <el-icon class="start-icon">
              <arrow-down/>
            </el-icon>
          </div>
        </div>
      </div>
    </div>

    <div v-for="(item, idx) in shopMainList">
      <div style="height: 100vh; width: 100%; " id="shop-main">
        <div v-if="(idx % 2) === 0" :id="item.id" style="height: 100%; overflow: hidden;">
          <el-row name="shop-main-row" style="overflow-y: auto; padding-top: 6vh; ">
            <el-col :span="6" :offset="4" style="display: flex; flex-direction: column; justify-content: center">
              <el-image :src="item.image" style="border-radius: 12px"/>
            </el-col>

            <el-col :span="9" :offset="1">
              <div class="goods-content-root-left">
                <p class="shop-item-title-left">{{ item.title }}</p>
                <p class="shop-item-description-left">{{ item.description }}</p>

                <el-scrollbar style="height: 440px; margin-top: 4vh;" always>
                  <div style="display: flex; padding: 12px">
                    <div v-for="item in 12" :key="item">
                      <div style="margin-right: 24px">
                        <router-link :to="{ path: '/goods' }" style="text-decoration: none;">
                          <GoodsCard/>
                        </router-link>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
          </el-row>

          <el-icon style="color: white; font-size: 28px; cursor: pointer; margin-top: 36px"
                   @click="autoScrollDown(item.id)"
                   v-if="item.id !== shopMainList.length">
            <arrow-down/>
          </el-icon>

        </div>
        <div v-else :id="item.id" style="height: 100%; overflow: hidden; ">
          <el-row name="shop-main-row" style="overflow-y: auto; padding-top: 6vh;">
            <el-col :span="10" :offset="3">
              <div class="goods-content-root-right">
                <p class="shop-item-title-right">{{ item.title }}</p>
                <p class="shop-item-description-right">{{ item.description }}</p>

                <el-scrollbar style="height: 440px; margin-top: 4vh" always>
                  <div style="display: flex; padding: 12px">
                    <div v-for="item in 12" :key="item">
                      <div style="margin-right: 24px">
                        <router-link :to="{ path: '/goods' }" style="text-decoration: none;">
                          <GoodsCard/>
                        </router-link>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
            <el-col :span="6" :offset="1" style="display: flex; flex-direction: column; justify-content: center">
              <el-image :src="item.image" style="border-radius: 12px;"/>
            </el-col>
          </el-row>

          <el-icon style="color: white; font-size: 28px; cursor: pointer; margin-top: 36px"
                   @click="autoScrollDown(item.id)"
                   v-if="item.id !== shopMainList.length">
            <arrow-down/>
          </el-icon>
        </div>
      </div>
    </div>

    <div style="display: flex; flex-direction: column; align-items: center; padding-top: 84px"
         v-for="(item, idx) in commandGoodsList">
      <div style="font-size: 36px; display: flex; flex-direction: row" :id="'command-goods-' + idx">
        <el-icon color="#f6eacc" style="transform: rotateY(180deg); margin-top: 14px">
          <cold-drink/>
        </el-icon>

        <p style="font-size: 48px; margin: 0 12px; font-family: 微軟正黑體; font-weight: normal; color: #f6eacc; user-select: none">
          {{ item.name }}</p>

        <el-icon color="#f6eacc" style="margin-top: 14px">
          <cold-drink/>
        </el-icon>
      </div>

      <div style="width: 1150px; display: flex; justify-content: center; margin-top: 24px">
        <el-row justify="space-between" :gutter="24">
          <el-col :span="6" style="display: flex; justify-content: center; margin-top: 24px" v-for="item in 8">
            <router-link :to="{ path: '/goods' }" style="text-decoration: none; ">
              <GoodsCard/>
            </router-link>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script lang="ts">

export default {
  name: "Shop",
}
</script>

<style scoped>
.slogan {
  color: white;
  text-align: left;
  font-size: 48px;
  margin: 24px 0 36px 12vw;
  font-family: 微軟正黑體;
}

.start-icon {
  color: white;
  font-size: 28px;
  cursor: pointer;
  animation: icon-floating 2s infinite;
  animation-timing-function: ease-in-out;
}

@keyframes icon-floating {
  0% {
    transform: translateY(-8px);
  }
  50% {
    transform: translateY(20px);
  }
  100% {
    transform: translateY(-8px);
  }
}

.goods-content-root-left {
  margin-left: 4vw;
  margin-top: 8vh;
}

.goods-content-root-right {
  margin-right: 4vw;
  margin-top: 8vh;
}

.shop-item-title-left,
.shop-item-title-right {
  font-size: 72px;
  font-family: 微軟正黑體;
  font-weight: bold;
  color: white;
  margin: 0;
}

.shop-item-title-left,
.shop-item-description-left {
  text-align: left;
}

.shop-item-title-right,
.shop-item-description-right {
  text-align: right;
}

.shop-item-description-left,
.shop-item-description-right {
  font-size: 36px;
  font-family: 微軟正黑體;
  color: #999999;
}

.visible {
  animation-name: row-top-in;
  animation-duration: 0.8s;
  animation-fill-mode: forwards;
  animation-timing-function: ease-in-out;
}

@keyframes row-top-in {
  0% {
    transform: translateY(240px);
    opacity: 0;
  }
  100% {
    transform: translateY(0px);
    opacity: 1;
  }
}

.menu-button {
  height: 48px;
  width: 48px;
  font-size: 30px;
  margin: auto;
  color: rgba(255, 255, 255, 0.7);
  background: transparent;
  border: none;
  z-index: 10;
  transition: all 0.3s ease-out;
}

.menu-button:hover {
  color: white;
}

.menu-item-name-appear {
  opacity: 0;
  animation-name: menu-item-name-left-in;
  animation-iteration-count: 1;
  animation-delay: 0.2s;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: forwards;
}

.menu-item-name-disappear {
  opacity: 1;
  animation-name: menu-item-name-right-out;
  animation-iteration-count: 1;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: forwards;
}

@keyframes menu-item-name-left-in {
  0% {
    visibility: hidden;
    opacity: 0;
    transform: translateX(40px);
  }
  100% {
    visibility: visible;
    opacity: 1;
    transform: translateX(-48px);
  }
}

@keyframes menu-item-name-right-out {
  0% {
    visibility: visible;
    opacity: 1;
    transform: translateX(-48px);
  }
  100% {
    visibility: hidden;
    opacity: 0;
    transform: translateX(-48px);
  }
}

.menu-item-bar {
  position: absolute;
  top: 12px;
  right: 34px;
  height: 1.5em;
  width: 4px;
  background: linear-gradient(to bottom, #f6eacc, #c1ab85);
  opacity: 0;
  visibility: hidden;

  transition: all 0.2s ease-in;
}

.menu-text-item {
  width: max-content;
  visibility: hidden;
  color: #999999;
  font-size: 18px;
  /*font-weight: bold;*/
  cursor: pointer;
}

.menu-text-item:hover {
  color: #ffffff;
}
</style>
