<script setup lang="ts">
import { ref }                               from "vue";
import { CDN_URL }                           from "../consts/urls";
import { ProductDetails, ProductSimpleInfo } from "../types/product";
import { ShopResponseInfo }                  from "../types/shop";
import { CommentDetails }                    from "../types/comment";
import { CustomerSimpleInfo }                from "../types/user";
import { Minus, Plus }                 from '@element-plus/icons-vue'
import { getProductDetails }                 from "../services/product";

const buyNum = ref<number>(1)
const currentImage = ref<number>(0)
const commentContent = ref<string>('')
const currentGoods = ref<ProductDetails>()

const switchImage = (idx: number) => {
  currentImage.value = idx
}

const getDecimal = (n: number) => {
  return String(n.toFixed(2)).split('.')[1]
}

const addGoodsToCart = () => {
  console.log('nb')
}

const submitComment = () => {
  console.log('nbnb')
}

const testGoods = ref<ProductDetails>({
  id: 'AAAA',
  category: '电子产品',
  brand: 'APPLE',
  productName: 'iPad Pro',
  price: 123.456,
  shop: {
    rate: 3.7,
    shopName: 'APPLE',
  },
  description: '先进的显示屏，两款尺寸各有精彩。11 英寸显示屏灵巧便携，令人沉浸。12.9 英寸 XDR 显示屏宽大绚丽，能尽显 HDR 内容的精彩。',
  commentList: [
    {
      type: 1,
      content: '你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好',
      customer: {
        avatar: '/avataaars.svg',
        nickname: '这是一个测试名',
        id: '1',
      },
      product: null,
      commentTime: new Date(),
      parentId: 123,
    },
    {
      type: 1,
      content: '你好你好a ',
      customer: {
        avatar: '/avataaars.svg',
        nickname: '这是另一个测试名',
        id: '2',
      },
      product: null,
      commentTime: new Date(),
      parentId: 123,
    },
  ],
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

getProductDetails(1).then(res => {
  // currentGoods.value = testGoods.value
  currentGoods.value = res
  console.log(res)
})


</script>

<template>
  <div style="min-width: 1400px; ">
    <el-breadcrumb separator=">" style="font-size: 14px; margin-left: 320px;">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ currentGoods?.productName }}</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin-top: 24px">
      <el-row style="height: 680px">
        <el-col :span="8" :offset="4" style="display: flex; justify-content: center">
          <div>
            <el-image style="border-radius: 12px; width: 400px" :src="currentGoods?.imageList[currentImage]"/>

            <el-scrollbar style="height: 150px; margin-top: 4px; overflow: hidden" always>
              <div style="display: flex; width: 15vw; padding: 12px 24px; ">
                <div v-for="(src, idx) in currentGoods?.imageList" :key="src" @click="switchImage(idx)">
                  <el-image :src="src" fit="contain" class="image-thumb"/>
                </div>
              </div>
            </el-scrollbar>
          </div>
        </el-col>

        <el-col :span="8" :offset="0" style="font-family: 微軟正黑體; color: white; text-align: left; ">
          <div>
            <p style="font-size: 52px; margin-bottom: 0; color: #f6eacc;">{{ currentGoods?.productName }}</p>
            <p style="margin-top: 24px; width: 90%">{{ currentGoods?.description }}</p>
          </div>

          <div style="margin-top: 56px">
            <el-row>
              <el-col :span="9">
                <p>
                  <span style="font-size: 36px">{{ (currentGoods?.price * buyNum).toFixed(0) }}</span>
                  <span style="font-size: 24px">{{ ' . ' + getDecimal(currentGoods?.price * buyNum) }}</span>
                  <span style="font-size: 24px"> RMB</span>
                </p>
              </el-col>
              <el-col :span="6" style="display: flex; align-items: center; margin-top: 4px">
                <el-button :icon="Minus" class="num-control-button-left" :disabled="buyNum === 1" @click="buyNum--"/>
                <input v-model="buyNum" class="num-control"/>
                <el-button :icon="Plus" class="num-control-button-right" @click="buyNum++"/>
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
              <el-col :span="4" v-if="currentGoods?.shop.rate">
                <el-rate
                    v-model="currentGoods.shop.rate"
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

    <div style="display: flex; justify-content: center; font-family: 微軟正黑體; margin-top: 64px; margin-bottom: 64px">
      <div style="width: 50%; ">
        <p style="color: white; text-align: left; font-size: 24px; margin: 0">商品评论</p>

        <div style="margin: 16px 0">
          <el-input
              v-model="commentContent"
              :rows="4"
              type="textarea"
              placeholder="发一条友善的评论"
              maxlength="150"
              input-style="border-radius: 12px; background: transparent; height: 120px; color: #eeeeee"
          />

          <div>
            <el-button class="comment-summit" @click="submitComment">发表评论</el-button>
          </div>
        </div>

        <div>
          <div style="width: 100%; margin-top: 48px; padding-top: 8px">
            <div v-for="item in currentGoods?.commentList">
              <div style="margin-top: 32px">
                <el-row>
                  <el-col :span="1" :offset="1">
                    <el-avatar :src="item.customer.avatar" :size="48"/>
                  </el-col>
                  <el-col :span="21" :offset="1" style="text-align: left; ">
                    <div style="color: #eeeeee">
                      <p style="margin: 0; font-weight: bold">{{ item.customer.nickname }}</p>
                      <p style="color: #999999">{{ item.content }}</p>

                      <p style="text-align: right; color: #999999; margin-right: 16px; margin-top: 0">
                        {{ item.commentTime.getFullYear() + '年 ' + (item.commentTime.getMonth() + 1) + '月' + item.commentTime.getDate() + '日 ' + item.commentTime.getHours() + ':' + (item.commentTime.getMinutes() < 10 ? '0' + item.commentTime.getMinutes() : item.commentTime.getMinutes()) }}
                      </p>
                    </div>


                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script lang="ts">
export default {
  name: "GoodsDetail"
}
</script>

<style scoped>

.image-thumb {
  width: 72px;
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
  width: 40%;
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

.num-control {
  border-radius: 0;
  border: none;
  outline: none;
  text-align: center;
  height: 28px;
  background: transparent;
  color: white;
  border-top: 1px solid white;
  border-bottom: 1px solid white;
  width: 3em;
  margin: 0;
}

.num-control-button-left,
.num-control-button-right {
  background: #010101;
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

.comment-summit {
  background: transparent;
  border: 1px solid #a8abb2;
  float: right;
  color: #a8abb2;
  margin: 16px;
}

.comment-summit:hover {
  background: linear-gradient(to right, #f6eacc, #c1ab85);
  color: #010101;
}
</style>
