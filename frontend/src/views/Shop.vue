<script setup lang="ts">
import GoodsCard          from "../components/GoodsCard.vue";
import TopSwiper          from "../components/TopSwiper.vue";
import { onMounted, ref } from "vue";
import {
  ArrowDown,
}                         from '@element-plus/icons-vue'

interface goods {
  name: string,
  image: string,
}

interface shopMainItem {
  image: string,
  title: string,
  description: string,
  // goodsList: goods[],
  id: string,
}

const testGoodsList = ref<goods[]>()

const shopMainList = ref<shopMainItem[]>([
  {
    image: 'https://images.pexels.com/photos/2690323/pexels-photo-2690323.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500',
    title: '拒绝被定义',
    description: '你应该有自己的想法与个性。',
    // goodsList: testGoodsList,
    id: '1',
  },
  {
    image: 'https://images.pexels.com/photos/39561/solar-flare-sun-eruption-energy-39561.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500',
    title: '帮手在此',
    description: '时时待命应你所需。',
    // goodsList: testGoodsList,
    id: '2',
  },
])

const startExplore = () => {
  document.getElementById('shop-main').scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

const handleScrollDown = (id: string) => {
  let t = document.getElementsByName('shop-main-row')
  t[<number>id].scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

onMounted(() => {
  // window.addEventListener('scroll', handleScroll)
})

</script>

<template>
  <div style="justify-content: center; display: flex">
    <TopSwiper/>
  </div>

  <!--  <el-row style="margin-top: 48px; display: flex; justify-content: center">-->
  <!--    <el-col :span="3">-->
  <!--      <div class="glass" style="height: 500px"></div>-->
  <!--    </el-col>-->

  <!--      <el-col :span="9" :offset="1">-->
  <!--        <div class="glass" style="padding: 24px">-->
  <!--          <el-carousel :interval="4000" type="card" :key="item" height="200px">-->
  <!--            <el-carousel-item v-for="item in 6">-->
  <!--              <h3>{{ item }}</h3>-->
  <!--            </el-carousel-item>-->
  <!--          </el-carousel>-->
  <!--        </div>-->
  <!--      </el-col>-->
  <!--    </el-row>-->

  <div style="margin-bottom: 13vh">
    <p class="slogan">
      <span style="color: #c1ab85">这个商店</span>，产品都称心，体验更如意。
    </p>

    <div style="display: flex; justify-content: center">
      <div style="cursor: pointer; width: 4vw;" @click="startExplore">
        <p style="color: #999999; margin: 0 0 24px">探索更多</p>
        <el-icon class="start-icon">
          <arrow-down/>
        </el-icon>
      </div>
    </div>
  </div>

  <div v-for="(item, idx) in shopMainList">
    <div style="height: calc(100vh - 100px); width: 100%;" id="shop-main">
      <div v-if="(idx % 2) === 0" style="height: 100%">
        <el-row name="shop-main-row">
          <el-col :span="6" :offset="4">
            <el-image :src="item.image" style="border-radius: 12px; margin-top: 15vh"/>
          </el-col>

          <el-col :span="10" :offset="1">
            <div class="goods-content-root-left">
              <p class="shop-item-title-left">{{ item.title }}</p>
              <p class="shop-item-description-left">{{ item.description }}</p>

              <el-scrollbar style="height: 350px; margin-top: 6vh">
                <div style="display: flex;">
                  <div v-for="item in 12" :key="item">
                    <router-link :to="{ path: '/goods' }" style="text-decoration: none">
                      <GoodsCard/>
                    </router-link>
                  </div>
                </div>
              </el-scrollbar>
            </div>
          </el-col>
        </el-row>

        <el-icon style="color: white; font-size: 28px; cursor: pointer; margin-top: 36px"
                 @click="handleScrollDown(item.id)">
          <arrow-down/>
        </el-icon>

      </div>
      <div v-else :id="item.id" style="height: 100%">
        <el-row name="shop-main-row">
          <el-col :span="10" :offset="3">
            <div class="goods-content-root-right">
              <p class="shop-item-title-right">{{ item.title }}</p>
              <p class="shop-item-description-right">{{ item.description }}</p>

              <el-scrollbar style="height: 350px; margin-top: 6vh">
                <div style="display: flex;">
                  <div v-for="item in 12" :key="item">
                    <router-link :to="{ path: '/goods' }" style="text-decoration: none">
                      <GoodsCard/>
                    </router-link>
                  </div>
                </div>
              </el-scrollbar>
            </div>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-image :src="item.image" style="border-radius: 12px; margin-top: 15vh"/>
          </el-col>
        </el-row>

        <el-icon style="color: white; font-size: 28px; cursor: pointer; margin-top: 36px"
                 @click="handleScrollDown(item.id)">
          <arrow-down/>
        </el-icon>
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

.glass {
  background: rgba(255, 255, 255, 0.01);
  border-radius: 10px;
  backdrop-filter: blur(20px);
  border-top: 1px solid #f6eacc;
  border-left: 1px solid #f6eacc;
  border-right: 1px solid #777777;
  border-bottom: 1px solid #777777;
  box-shadow: 0 0 4px #f6eacc;
}

.goods-content-root-left {
  margin-left: 4vw;
  margin-top: 10vh;
}

.goods-content-root-right {
  margin-right: 4vw;
  margin-top: 10vh;
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
</style>
