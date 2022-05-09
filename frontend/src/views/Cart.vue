<script setup lang="ts">
import CheckBox                  from "../components/CheckBox.vue";
import {
  GoodsFilled,
  Minus,
  Plus,
  Delete,
  More
}                                from "@element-plus/icons-vue";
import { onMounted, ref, watch } from "vue";
import { CartResponseInfo }      from "../types/cart";
import { getCartDetails }        from "../services/cart";

const cartInfo     = ref<CartResponseInfo>()
const selectedShop = ref<boolean[]>()
const selectedItem = ref<boolean[][]>([])

const sumPrice = ref<number>(0)
const selectedNum = ref<number>(0)

// 商品描述内容过长则需要省略
const goodsDescriptionFormat = (str: string) => {
  const MAX_LENGTH = 80;

  if (str.length >= MAX_LENGTH)
    return str.substring(0, MAX_LENGTH) + "..."
  else return str
}

// 全选某个商店的商品
const selectAllShopItems = (shopIndex: number) => {
  selectedShop.value[shopIndex] = !selectedShop.value[shopIndex]

  for (let i = 0; i < selectedItem.value[shopIndex].length; i++)
    selectedItem.value[shopIndex][i] = selectedShop.value[shopIndex]
}

getCartDetails('testUser').then(res => {
  cartInfo.value = res

  selectedShop.value = new Array(cartInfo.value?.shops.length).fill(false)

  for (let i = 0; i < cartInfo.value?.shops.length; i++)
    selectedItem.value?.push(new Array(cartInfo.value?.shops[i].items.length).fill(false))
})

onMounted(() => {
  watch(selectedItem.value, (value) => {
    sumPrice.value = selectedNum.value = 0

    for (let i = 0; i < value.length; i++) {
      let allSelected = true

      for (let j = 0; j < value[i].length; j++) {
        if (value[i][j] === false) allSelected = false

        let elem  = document.getElementById('card' + i + '-' + j)
        let num   = document.getElementById('num' + i + '-' + j)
        let left  = document.getElementById('left' + i + '-' + j)
        let right = document.getElementById('right' + i + '-' + j)

        if (!elem || !num || !left || !right) break
        if (value[i][j] === true) {
          sumPrice.value += cartInfo.value?.shops[i].items[j].product.price * cartInfo.value?.shops[i].items[j].number
          selectedNum.value++

          elem.style.background = 'rgba(246,234,204, 1)'
          elem.style.boxShadow  = '0 0 30px 4px rgba(246,234,204,0.52)'
          elem.style.color      = '#222222'
          num.style.borderColor = num.style.color = '#222222'
          left.style.borderColor = left.style.color = '#222222'
          right.style.borderColor = right.style.color = '#222222'

        } else {
          elem.style.background = 'transparent'
          elem.style.boxShadow  = 'none'
          elem.style.color      = '#ffffff'
          num.style.borderColor = num.style.color = '#ffffff'
          left.style.borderColor = left.style.color = '#ffffff'
          right.style.borderColor = right.style.color = '#ffffff'
        }
      }

      selectedShop.value[i] = allSelected;
    }
  }, {deep: true})
})
</script>

<template>
  <div style="display: flex; justify-content: center">
    <div style="width: 1200px; color: #ffffff; font-family: 微軟正黑體; font-weight: bold">
      <div>
        <el-row style="align-items: center; font-size: 18px">
          <el-col :span="3" :offset="1">
            <p>全选</p>
          </el-col>
          <el-col :span="9" :offset="1" style="text-align: left">
            <p style="margin-left: 20px">商品信息</p>
          </el-col>
          <el-col :span="3">
            <p>单价</p>
          </el-col>
          <el-col :span="3">
            <p>数量</p>
          </el-col>
          <el-col :span="3">
            <p>操作</p>
          </el-col>
        </el-row>
      </div>

      <div style="justify-content: center; display: flex; margin-top: -12px">
        <el-divider style="width: 90%"/>
      </div>

      <div v-if="cartInfo?.shops" v-for="(shop, shopIndex) in cartInfo?.shops">
        <div style="display: flex; align-items: center; margin-left: 96px; color: #f6eacc">
          <CheckBox @click="selectAllShopItems(shopIndex)" v-model:model-value="selectedShop[shopIndex]"/>
          <el-icon :size="24" style="margin-left: 16px">
            <goods-filled/>
          </el-icon>
          <p style="margin: 0 12px 0 4px">店铺：{{ shop.shop.shopName }}</p>
          <el-rate
              v-model="shop.shop.rate"
              disabled
              :colors="['#f6eacc', '#f6eacc', '#f6eacc']"
              disabled-void-color="transparent"
          />
        </div>

        <div>
          <div v-for="(item, itemIndex) in shop.items" style="width: 1140px; margin: 16px 0">
            <div class="card-item" :id="'card' + shopIndex + '-' + itemIndex">
              <el-row style="width: 100%; align-items: center; ">
                <el-col :span="1" style="justify-content: center">
                  <CheckBox style="margin-left: 18px" v-model:model-value="selectedItem[shopIndex][itemIndex]"/>
                </el-col>
                <el-col :span="3">
                  <el-image style="width: 100px; height: 100px" :src="item.product.thumb" fit="fill"/>
                </el-col>
                <el-col :span="10" style="text-align: left">
                  <p style="margin: 0">{{ item.product.productName }}</p>
                  <p style="margin: 12px 0 0 0; color: #999999">{{
                      goodsDescriptionFormat(item.product.description)
                    }}</p>
                </el-col>
                <el-col :span="3">
                  <p>{{ item.product.price.toFixed(2) }}</p>
                </el-col>
                <el-col :span="4">
                  <el-button :icon="Minus" class="num-control-button-left" :disabled="item.number === 1"
                             @click="item.number--" :id="'left' + shopIndex + '-' + itemIndex"/>
                  <input v-model="item.number" class="num-control" :id="'num' + shopIndex + '-' + itemIndex"/>
                  <el-button :icon="Plus" class="num-control-button-right" @click="item.number++"
                             :id="'right' + shopIndex + '-' + itemIndex"/>
                </el-col>
                <el-col :span="3">
                  <!-- TODO: 这里路由改一改，写成带有商品ID参数的那种 -->
                  <router-link :to="{ path: '/goods' }" style="text-decoration: none; color: #ffffff">
                    <el-button type="text" :icon="More" style="color: rgb(30, 144, 255)">详情</el-button>
                  </router-link>
                  <br/>
                  <el-button type="text" style="color: rgb(248, 62, 91)" :icon="Delete">删除</el-button>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </div>
      <div v-else style="display: flex; justify-content: center; width: 100%">
        <el-empty description="购物车空空如也"/>
      </div>

      <div style="justify-content: center; display: flex; margin-top: -12px">
        <el-divider style="width: 90%"/>
      </div>

      <div style="display: flex; justify-content: center">
        <div style="width: 90%">
          <el-row style="justify-content: right">
            <el-col :span="4" style="display: flex; align-items: center; height: 100%">
              <div style="width: 100%; text-align: right; ">
                <p style="margin: 0">总计：
                  <span style="color: #f6eacc; font-size: 24px; margin: 0">¥ {{ sumPrice.toFixed(2) }}</span>
                </p>
                <p style="margin: 12px 0 0 0">已选择
                  <span style="color: #f6eacc; font-size: 18px; margin: 0">{{ selectedNum.toFixed(0) }}</span>
                  件商品</p>
              </div>
            </el-col>
            <el-col :span="5" :offset="1" style="">
              <el-button class="buy-button">立即结算</el-button>
            </el-col>
          </el-row>
        </div>
      </div>

    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "Cart"
}
</script>

<style scoped>
.card-item {
  border: 1px solid rgba(255, 255, 255, 0.5);
  margin-left: 100px;
  display: flex;
  padding: 12px 0;
  border-radius: 12px;

  transition: all 0.2s ease-out;
}

.num-control {
  border-radius: 0;
  border: none;
  outline: none;
  text-align: center;
  color: #ffffff;
  height: 28px;
  background: transparent;
  border-top: 1px solid white;
  border-bottom: 1px solid white;
  width: 3em;
  margin: 0;
  transition: all 0.2s ease-out;
}

.num-control-button-left,
.num-control-button-right {
  transition: all 0.2s ease-out;
  background: transparent;
  color: #ffffff;
  border-radius: 0;
}

.num-control-button-left {
  border-top: 1px solid white;
  border-left: 1px solid white;
  border-bottom: 1px solid white;
  border-right: none;
  border-top-left-radius: 12px;
  border-bottom-left-radius: 12px;
}

.num-control-button-right {
  border-top: 1px solid white;
  border-left: none;
  border-bottom: 1px solid white;
  border-right: 1px solid white;
  border-top-right-radius: 12px;
  border-bottom-right-radius: 12px;
}

.num-control-button-left:hover,
.num-control-button-right:hover {
  background: rgba(255, 255, 255, 0.3);
}

.buy-button {
  height: 100%;
  width: 100%;
  background: linear-gradient(to right, #f6eacc, #c1ab85);
  box-shadow: 0 8px 40px 2px rgba(246,234,204,0.52);
  color: #222222;
  border: 1px solid #f6eacc;
}

.buy-button:hover {
  background: linear-gradient(to right, rgba(246,234,204, 0.8), rgba(193,171,133, 0.8));
}
</style>
