<script setup lang="ts">
import ProductCard                     from "../components/ProductCard.vue";
import TopSwiper                       from "../components/TopSwiper.vue";
import { onMounted, onUnmounted, ref } from "vue";
import {
  ArrowDown,
  ColdDrink,
  ArrowDownBold,
}                                      from '@element-plus/icons-vue'
import { CDN_URL }                     from "../consts/urls"
import { ProductSimpleInfo }           from "../types/product";
import { getProductDetailsPaging }     from "../services/product";

interface PopularShop {
  image: string
  title: string
  description: string
  productList: ProductSimpleInfo[]
  id: number
}

// 热门商店特殊展示
const popularShopList = ref<PopularShop[]>([
  {
    image: CDN_URL + '/shop-special-example-1.webp',
    title: '大显身手',
    description: '精彩由此开始。',
    productList: [],
    id: 1,
  },
  {
    image: CDN_URL + '/shop-special-example-2.webp',
    title: '帮手在此',
    description: '时时待命应你所需。',
    productList: [],
    id: 2,
  },
])

const getPopularShopList = () => {
  popularShopList.value.forEach(shop => {
    getProductDetailsPaging({shop_id: shop.id}).then(res => {
      shop.productList = res
    })
  })
}

interface ProductCategory {
  categoryName: string
  productList: ProductSimpleInfo[]
}

// 一般分类的商品列表
const categoryList = ref<ProductCategory[]>([
  {
    categoryName: '新品上市',
    productList: [],
  },
  {
    categoryName: '运动健康',
    productList: [],
  },
  {
    categoryName: '智能家居',
    productList: [],
  }
])

const getCategoryList = () => {
  categoryList.value.forEach(list => {
    getProductDetailsPaging({category: list.categoryName}).then(res => {
      list.productList = res
    })
  })
}

const popularShopOnAppear = document.getElementsByName('popular-shop')
const scrollHandler       = () => {
  popularShopOnAppear.forEach((elem) => {
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
  for (let i = 0; i < popularShopList.value.length + categoryList.value.length; i++) {
    let menuItemBar      = document.getElementById('menu-item-bar-' + i)
    let menuItemText     = document.getElementById('menu-item-text-' + i)
    let menuSwitchButton = document.getElementById('menu-button')

    if (!menuItemBar || !menuItemText || !menuSwitchButton) return

    if (menuIsShow.value) {
      menuItemBar.style.transform  = 'translateY(' + (56 + i * 37) + 'px)'
      menuItemBar.style.opacity    = '1'
      menuItemBar.style.visibility = 'visible'
      menuItemText.classList.remove('menu-item-name-disappear')
      menuItemText.classList.add('menu-item-name-appear')
      menuSwitchButton.style.transform = 'rotate(180deg)'
    } else {
      menuItemBar.style.transform  = 'translateY(0)'
      menuItemBar.style.opacity    = '0'
      menuItemBar.style.visibility = 'hidden'
      menuItemText.classList.remove('menu-item-name-appear')
      menuItemText.classList.add('menu-item-name-disappear')
      menuSwitchButton.style.transform = 'rotate(0)'
    }
  }
}

const positioningMenuElements = (idx: number) => {
  let elem

  if (idx < popularShopList.value.length) {
    elem = document.getElementById('popular-shop-' + idx)
  } else {
    elem = document.getElementById('category-' + (idx - popularShopList.value.length))
  }

  if (!elem) return
  elem.scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

onMounted(() => {
  window.addEventListener('scroll', scrollHandler, true)
})

onUnmounted(() => {
  window.removeEventListener('scroll', scrollHandler, true)
})

getPopularShopList()
getCategoryList()
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

            <div v-for="(i, idx) in popularShopList.length + categoryList.length">
              <p :id="'menu-item-text-' + idx" class="menu-text-item" @click="positioningMenuElements(idx)">{{
                  i > popularShopList.length
                      ? categoryList[idx - popularShopList.length].categoryName
                      : popularShopList[idx].title
                }}</p>
              <div class="menu-item-bar" :id="'menu-item-bar-' + idx"/>
            </div>
          </el-affix>
        </div>

        <div style="display: flex; justify-content: center">
          <div style="cursor: pointer; width: 96px; " @click="positioningMenuElements(0)">
            <p style="color: #999999; margin: 0 0 24px">探索更多</p>
            <el-icon class="start-icon">
              <arrow-down/>
            </el-icon>
          </div>
        </div>
      </div>
    </div>

    <div v-for="(shop, idx) in popularShopList">
      <div style="height: 100vh; width: 100%">
        <div v-if="(idx % 2) === 0" style="height: 100%; overflow: hidden">
          <el-row :id="'popular-shop-' + idx" style="overflow-y: auto; padding-top: 60px" name="popular-shop">
            <el-col :span="6" :offset="4" style="display: flex; flex-direction: column; justify-content: center">
              <el-image :src="shop.image" style="border-radius: 12px"/>
            </el-col>

            <el-col :span="9" :offset="1">
              <div class="goods-content-root-left">
                <p class="shop-item-title-left">{{ shop.title }}</p>
                <p class="shop-item-description-left">{{ shop.description }}</p>

                <el-scrollbar style="height: 440px; margin-top: 4vh;" always>
                  <div style="display: flex; padding: 12px">
                    <div v-for="product in shop.productList" :key="product">
                      <div style="margin-right: 24px">
                        <router-link :to="{ name: 'ProductDetailsPage', params: { id: product.productId } }" style="text-decoration: none;">
                          <ProductCard :product="product"/>
                        </router-link>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
          </el-row>

          <el-icon style="color: white; font-size: 28px; cursor: pointer; margin-top: 36px"
                   @click="positioningMenuElements(idx + 1)">
            <arrow-down/>
          </el-icon>

        </div>
        <div v-else style="height: 100%; overflow: hidden">
          <el-row :id="'popular-shop-' + idx" style="overflow-y: auto; padding-top: 60px" name="popular-shop">
            <el-col :span="10" :offset="3">
              <div class="goods-content-root-right">
                <p class="shop-item-title-right">{{ shop.title }}</p>
                <p class="shop-item-description-right">{{ shop.description }}</p>

                <el-scrollbar style="height: 440px; margin-top: 4vh" always>
                  <div style="display: flex; padding: 12px">
                    <div v-for="product in shop.productList" :key="product">
                      <div style="margin-right: 24px">
                        <router-link :to="{ name: 'ProductDetailsPage', params: { id: product.productId } }" style="text-decoration: none;">
                          <ProductCard :product="product"/>
                        </router-link>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </el-col>
            <el-col :span="6" :offset="1" style="display: flex; flex-direction: column; justify-content: center">
              <el-image :src="shop.image" style="border-radius: 12px;"/>
            </el-col>
          </el-row>

          <el-icon style="color: white; font-size: 28px; cursor: pointer; margin-top: 36px"
                   @click="positioningMenuElements(idx + 1)">
            <arrow-down/>
          </el-icon>
        </div>
      </div>
    </div>

    <div style="display: flex; flex-direction: column; align-items: center; padding-top: 84px"
         v-for="(item, idx) in categoryList">
      <div style="font-size: 36px; display: flex; flex-direction: row" :id="'category-' + idx">
        <el-icon color="#f6eacc" style="transform: rotateY(180deg); margin-top: 14px">
          <cold-drink/>
        </el-icon>
        <p style="font-size: 48px; margin: 0 12px; font-family: 微軟正黑體; font-weight: normal; color: #f6eacc; user-select: none">
          {{ item.categoryName }}</p>
        <el-icon color="#f6eacc" style="margin-top: 14px">
          <cold-drink/>
        </el-icon>
      </div>

      <div style="width: 1096px; margin-top: 24px">
        <div>
          <router-link :to="{ name: 'ProductDetailsPage', params: { id: product.productId } }"
                       v-for="product in item.productList" style="text-decoration: none">
            <ProductCard :product="product" style="margin: 12px"/>
          </router-link>
        </div>
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
  45% {
    transform: translateY(20px);
  }
  55% {
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
  height: 1em;
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
  font-size: 16px;
  /*font-weight: bold;*/
  cursor: pointer;
}

.menu-text-item:hover {
  color: #ffffff;
}
</style>
