<script setup lang="ts">
import { ref }                               from "vue";
import { CDN_URL }                           from "../consts/urls";
import { ProductDetails, ProductSimpleInfo } from "../types/product";
import { ShopResponseInfo }                  from "../types/shop";
import { CommentDetails }                    from "../types/comment";
import { CustomerSimpleInfo }                from "../types/user";
import { Star }                  from '@element-plus/icons-vue'

const currentImage = ref<number>(0)

const testShop = ref<ShopResponseInfo>({
  rate: 2.7,
  shopName: 'APPLE',
})

const testCommentList = ref<CommentDetails[]>()

const testGoods = ref<ProductDetails>({
  id: 'AAAA',
  category: '电子产品',
  brand: 'APPLE',
  productName: 'iPad Pro',
  price: 123.456,
  shop: testShop,
  description: '先进的显示屏，两款尺寸各有精彩。11 英寸显示屏灵巧便携，令人沉浸。12.9 英寸 XDR 显示屏宽大绚丽，能尽显 HDR 内容的精彩。',
  commentList: testCommentList,
  imageList: [
    `${CDN_URL}/goodsdetail-example-1.png`,
    `${CDN_URL}/goodsdetail-example-2.png`,
    `${CDN_URL}/goodsdetail-example-3.png`,
    `${CDN_URL}/goodsdetail-example-4.png`,
    `${CDN_URL}/goodsdetail-example-1.png`,
    `${CDN_URL}/goodsdetail-example-2.png`,
    `${CDN_URL}/goodsdetail-example-3.png`,
    `${CDN_URL}/goodsdetail-example-4.png`,
  ],
  thumb: `${CDN_URL}/goodsdetail-example-1.png`,
})

const switchImage = (idx: number) => {
  currentImage.value = idx
}

const getDecimal = (n: number) => {
  return String(n.toFixed(2)).split('.')[1]
}

const buyNum = ref<number>(1)

const addGoodsToCart = () => {
  console.log('nb')
}

</script>

<template>
  <div style="min-height: calc(100vh - 200px)">
    <el-breadcrumb separator=">" style="font-size: 14px; margin-left: 16vw; ">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ testGoods.productName }}</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row style="margin-top: 24px">
      <el-col :span="8" :offset="4" style="display: flex; justify-content: center">
        <div>
          <el-image style="border-radius: 12px; width: 90%" :src="testGoods.imageList[currentImage]"/>

          <el-scrollbar style="height: 150px; margin-top: 4px; overflow: hidden" always>
            <div style="display: flex; width: 15vw; padding: 12px 24px; ">
              <div v-for="(src, idx) in testGoods.imageList" :key="src" @click="switchImage(idx)">
                <el-image :src="src" fit="contain" class="image-thumb"/>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </el-col>

      <el-col :span="8" :offset="0" style="font-family: 微軟正黑體; color: white; text-align: left">
        <div>
          <p style="font-size: 52px; margin-bottom: 0; color: #f6eacc;">{{ testGoods.productName }}</p>
          <p style="margin-top: 24px">{{ testGoods.description }}</p>
        </div>

        <div style="margin-top: 56px">
          <el-row>
            <el-col :span="9">
              <p>
                <span style="font-size: 36px">{{ (testGoods.price * buyNum).toFixed(0) }}</span>
                <span style="font-size: 24px">{{ ' . ' + getDecimal(testGoods.price * buyNum) }}</span>
                <span style="font-size: 24px"> RMB</span>
              </p>
            </el-col>
            <el-col :span="6" style="display: flex; align-items: center; margin-top: 4px">
              <el-input-number v-model="buyNum" :min="1" />
            </el-col>
          </el-row>

          <div>
            <el-button class="cart-button" @click="addGoodsToCart">加入购物车</el-button>
            <el-button class="buy-button">立即购买</el-button>
          </div>
        </div>

        <div style="width: 90%; margin-top: 56px; border-top: 1px solid #f6eacc; color: white">
          <el-row style="display: flex; align-items: center; ">
            <el-col :span="4" >
              <p>商店评分</p>
            </el-col>
            <el-col :span="4" >
              <el-rate
                  v-model="testGoods.shop.rate"
                  disabled
                  :colors="['#f6eacc', '#f6eacc', '#f6eacc']"
                  disabled-void-color="transparent"
              />
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script lang="ts">
export default {
  name: "GoodsDetail"
}
</script>

<style scoped>

.image-thumb {
  width: 4vw;
  margin-top: 12px;
  margin-right: 20px;
  border-radius: 4px;
  cursor: pointer;
}

.image-thumb:hover {
  box-shadow: 0 0 30px 5px rgba(246,234,204,0.52);
}

.cart-button,
.buy-button {
  width: 240px;
  height: 56px;
  font-size: 16px;
  font-weight: normal;
  margin: 8px 0 0 0;
  box-shadow: 0 8px 40px 2px rgba(246,234,204,0.52);
}

.cart-button {
  background: #010101;
  color: #f6eacc;
  border: 1px solid #f6eacc;
  border-radius: 16px 0 0 16px;
}

.cart-button:hover {
  background: rgba(80, 80, 80, 0.5);
}

.buy-button {
  background: linear-gradient(to right, #f6eacc, #c1ab85);
  border: 1px solid #f6eacc;
  border-left: none;
  color: #010101;
  border-radius: 0 16px 16px 0;
}

.buy-button:hover {
  background: linear-gradient(to right, rgba(246,234,204, 0.8), rgba(193,171,133, 0.8));
}

</style>
