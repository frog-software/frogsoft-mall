<script setup lang="ts">

import CheckBox                              from "../components/CheckBox.vue";
import {
  GoodsFilled,
  Minus,
  Plus,
  Delete,
  More,
  InfoFilled
}                                            from "@element-plus/icons-vue";
import { ref }                               from "vue";
import { CartResponseInfo }                  from "../types/cart";
import { getCartDetails, deleteCartProduct } from "../services/cart";
import { ShopResponseInfo }                  from "../types/shop";
import { productDescriptionFormat }          from "../utils/util";
import { ProductSimpleInfo }                 from "../types/product";

interface CartShopItem {
  shopInfo: ShopResponseInfo
  productList: {
    product: ProductSimpleInfo
    addTime: string
    amount: number
    remarks: string
    index: number
    isSelected: boolean
  }[]
  isAllSelected: boolean
}

const cartDetail   = ref<CartResponseInfo>()  // 原数据
const cartShopList = ref<CartShopItem[]>([])  // 用于页面展示
const sumPrice     = ref<number>(0)
const selectedNum  = ref<number>(0)

// 全选某个商店的商品
const selectAllShopItems = (shopName: string) => {
  cartShopList.value.forEach(shop => {
    if (shop.shopInfo.shopName === shopName) {
      shop.isAllSelected = !shop.isAllSelected

      shop.productList.forEach(item => {
        item.isSelected = shop.isAllSelected
        switchCardStyle(item.index, item.isSelected)
      })
    }
  })
  calcNumAndSumPrice()
}

// 选择某一款商品
const selectOneShopItem = (shopName: string, index: number, isSelected: boolean) => {
  switchCardStyle(index, isSelected)

  cartShopList.value.forEach(shop => {
    if (shop.shopInfo.shopName === shopName) {
      if (isSelected) {
        shop.isAllSelected = true
        for (let i = 0; i < shop.productList.length; i++) {
          if (!shop.productList[i].isSelected) {
            shop.isAllSelected = false
            break
          }
        }
      } else {
        shop.isAllSelected = false
      }
    }
  })

  calcNumAndSumPrice()
}

// 更改商品卡片的样式
const switchCardStyle = (index: number, isSelected: boolean) => {
  let elem  = document.getElementById('card-' + index)
  let num   = document.getElementById('num-' + index)
  let left  = document.getElementById('left-' + index)
  let right = document.getElementById('right-' + index)

  if (!elem || !num || !left || !right) return

  if (isSelected) {
    elem.style.background   = 'rgba(246,234,204, 1)'
    elem.style.boxShadow    = '0 0 30px 4px rgba(246,234,204,0.52)'
    elem.style.color        = '#222222'
    num.style.borderColor   = num.style.color = '#222222'
    left.style.borderColor  = left.style.color = '#222222'
    right.style.borderColor = right.style.color = '#222222'
  } else {
    elem.style.background   = 'transparent'
    elem.style.boxShadow    = 'none'
    elem.style.color        = '#ffffff'
    num.style.borderColor   = num.style.color = '#ffffff'
    left.style.borderColor  = left.style.color = '#ffffff'
    right.style.borderColor = right.style.color = '#ffffff'
  }
}

// 统计总数量和总价格
const calcNumAndSumPrice = () => {
  sumPrice.value    = 0
  selectedNum.value = 0

  cartShopList.value.forEach(shop => {
    shop.productList.forEach(item => {
      if (item.isSelected) {
        sumPrice.value += item.amount * item.product.price
        selectedNum.value++
      }
    })
  })
}

// 删除单个商品
const deleteProductInCart = (username: string, index: number) => {
  deleteCartProduct(username, index)

  for (let i = 0; i < cartShopList.value.length; i++) {
    for (let j = 0; j < cartShopList.value[i].productList.length; j++) {
      if (cartShopList.value[i].productList[j].index === index) {
        cartShopList.value[i].productList.splice(j, 1)
        return
      }
    }
  }

  calcNumAndSumPrice()
}

getCartDetails('testUser').then(res => {
  cartDetail.value = res

  console.log(cartDetail.value?.cartItems[0].product.id)

  let tempItemList = res.cartItems.map((i, idx) => ({
    ...i,
    index: idx,
    isSelected: false,
  }))

  let shopList: number[] = []
  tempItemList.forEach(item => {
    if (shopList.findIndex(i => i === item.product.shop.id) === -1) {
      cartShopList.value?.push({
        shopInfo: item.product.shop,
        productList: [item],
        isAllSelected: false,
      })
      shopList.push(item.product.shop.id)
    } else {
      cartShopList.value?.forEach(shop => {
        if (shop.shopInfo.shopName === item.product.shop.shopName) {
          shop.productList.push(item)
        }
      })
    }
  })
})
</script>

<template>
  <div style="display: flex; justify-content: center; min-height: calc(100vh - 252px)">
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

      <div v-if="cartDetail?.itemCount" v-for="shop in cartShopList">
        <div v-if="shop.productList.length"
             style="display: flex; align-items: center; margin-left: 96px; color: #f6eacc">
          <CheckBox @click="selectAllShopItems(shop.shopInfo.shopName)" v-model:model-value="shop.isAllSelected"/>
          <el-icon :size="24" style="margin-left: 16px">
            <goods-filled/>
          </el-icon>
          <p style="margin: 0 12px 0 4px">店铺：{{ shop.shopInfo.shopName }}</p>
          <el-rate
              v-model="shop.shopInfo.rate"
              disabled
              :colors="['#f6eacc', '#f6eacc', '#f6eacc']"
              disabled-void-color="transparent"
          />
        </div>

        <div v-for="item in shop.productList" style="width: 1140px; margin: 16px 0">
          <div class="card-item" :id="'card-' + item.index">
            <el-row style="width: 100%; align-items: center; ">
              <el-col :span="1" style="justify-content: center">
                <CheckBox style="margin-left: 18px" v-model:model-value="item.isSelected"
                          @click="selectOneShopItem(shop.shopInfo.shopName, item.index, item.isSelected)"/>
              </el-col>
              <el-col :span="3">
                <el-image style="width: 100px; height: 100px" :src="item.product.thumb" fit="fill"/>
              </el-col>
              <el-col :span="10" style="text-align: left">
                <p style="margin: 0">{{ item.product.productName }}</p>
                <p style="margin: 12px 0 0 0; color: #999999">{{
                    productDescriptionFormat(item.product.description, 80)
                  }}</p>
              </el-col>
              <el-col :span="3">
                <p>{{ item.product.price.toFixed(2) }}</p>
              </el-col>
              <el-col :span="4">
                <el-button :icon="Minus" class="num-control-button-left" :disabled="item.amount === 1"
                           @click="() => { item.amount--; if (item.isSelected) sumPrice -= item.product.price }" :id="'left-' + item.index"/>
                <input v-model="item.amount" class="num-control" :id="'num-' + item.index"/>
                <el-button :icon="Plus" class="num-control-button-right"
                           @click="() => { item.amount++; if (item.isSelected) sumPrice += item.product.price }"
                           :id="'right-' + item.index"/>
              </el-col>
              <el-col :span="3">
                <router-link :to="{ name: 'ProductDetailsPage', params: { id: item.product.id } }" style="text-decoration: none; color: #ffffff">
                  <el-button type="text" :icon="More" style="color: rgb(30, 144, 255)">详情</el-button>
                </router-link>

                <br/>

                <el-popconfirm title="确定要删除吗？" :icon="InfoFilled" confirmButtonText="确定" cancelButtonText="取消"
                               @confirm="deleteProductInCart(cartDetail?.username, item.index)">
                  <template #reference>
                    <el-button type="text" style="color: #f83e5b" :icon="Delete">删除</el-button>
                  </template>
                </el-popconfirm>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
      <div v-else style="display: flex; justify-content: center; width: 100%">
        <el-empty description="购物车空空如也"/>
      </div>

      <div style="justify-content: center; display: flex">
        <el-divider style="width: 90%"/>
      </div>

      <div style="display: flex; justify-content: center">
        <div style="width: 90%">
          <el-row style="justify-content: right">
            <el-col :span="10" style="display: flex; align-items: center; height: 100%; ">
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
  box-shadow: 0 8px 40px 2px rgba(246, 234, 204, 0.52);
  color: #222222;
  border: 1px solid #f6eacc;
}

.buy-button:hover {
  background: linear-gradient(to right, rgba(246, 234, 204, 0.8), rgba(193, 171, 133, 0.8));
}
</style>
