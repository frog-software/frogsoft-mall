<script setup lang="ts">
import GoodsCard                       from "../components/GoodsCard.vue";
import TopSwiper                       from "../components/TopSwiper.vue";
import { onMounted, onUnmounted, ref } from "vue";
import {
  ArrowDown,
  ColdDrink
}                  from '@element-plus/icons-vue'
import { CDN_URL } from "../consts/urls"

interface goods {
  name: string,
  image: string,
}

interface shopMainItem {
  image: string,
  title: string,
  description: string,
  // goodsList: goods[],
  id: number,
}

const testGoodsList = ref<goods[]>()

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

const handleScrollDown = (id: number) => {
  let rowList = document.getElementsByName('shop-main-row')

  rowList[id].scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

const rowAppear = document.getElementsByName('shop-main-row')

const scrollHandler = () => {
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

onMounted(() => {
  window.addEventListener('scroll', scrollHandler, true)
})

onUnmounted(() => {
  window.removeEventListener('scroll', scrollHandler, true)
})

</script>

<template>
  <div>
    <div style="height: calc(100vh - 100px)">
      <div style="justify-content: center; display: flex">
        <TopSwiper/>
      </div>

      <div>
        <p class="slogan">
          <span style="color: #c1ab85; font-weight: bold">这个商店</span>，产品都称心，体验更如意。
        </p>

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
                   @click="handleScrollDown(item.id)"
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
                   @click="handleScrollDown(item.id)"
                   v-if="item.id !== shopMainList.length">
            <arrow-down/>
          </el-icon>
        </div>
      </div>
    </div>

    <div style="display: flex; flex-direction: column; align-items: center; padding-top: 84px" v-for="item in commandGoodsList">
      <div style="font-size: 36px; display: flex; flex-direction: row">
        <el-icon color="#f6eacc" style="transform: rotateY(180deg); margin-top: 14px">
          <cold-drink/>
        </el-icon>

        <p style="font-size: 48px; margin: 0 12px; font-family: 微軟正黑體; font-weight: normal; color: #f6eacc; user-select: none">{{ item.name }}</p>

        <el-icon color="#f6eacc" style="margin-top: 14px">
          <cold-drink/>
        </el-icon>
      </div>

      <div style="width: 60%; display: flex; justify-content: center; margin-top: 24px">
        <el-row justify="space-between" gutter="24px">
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
  animation: icon-floating 1s infinite;
  animation-timing-function: ease-out;
}

@keyframes icon-floating {
  0% {
    transform: translateY(-8px);
  }
  100% {
    transform: translateY(20px);
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


</style>
