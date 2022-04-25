<script setup lang="ts">
import { ref }   from "vue";
import GoodsCard from "../components/GoodsCard.vue";

interface goods {
  name: string,
  image: string[],
  description: string,
  price: number,
  category: string,
}

const currentImage = ref<number>(0)

const testGoods = ref<goods>({
  name: 'iPad Pro',
  image: [
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-air-select-202203?wid=1765&hei=2000&fmt=jpeg&qlt=95&.v=1645665079887',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-air-select-wifi-spacegray-202203?wid=470&hei=556&fmt=png-alpha&.v=1645066742664',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-air-select-wifi-pink-202203?wid=470&hei=556&fmt=png-alpha&.v=1645066399526',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-air-select-wifi-purple-202203?wid=470&hei=556&fmt=png-alpha&.v=1645066730601',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-air-select-wifi-blue-202203?wid=470&hei=556&fmt=png-alpha&.v=1645065732688',
  ],
  description: '先进的显示屏，两款尺寸各有精彩。11 英寸显示屏灵巧便携，令人沉浸。12.9 英寸 XDR 显示屏宽大绚丽，能尽显 HDR 内容的精彩。',
  price: 123.456,
  category: '电子产品'
})

const switchImage = (idx: number) => {
  currentImage.value = idx
}

const getDecimal = (n: number) => {
  return String(n.toFixed(2)).split('.')[1]
}

const buyNum = ref<number>(1)


</script>

<template>
  <div style="min-height: calc(100vh - 200px)">
    <el-breadcrumb separator=">" style="font-size: 14px; margin-left: 16vw; ">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ testGoods.name }}</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row style="margin-top: 24px">
      <el-col :span="7" :offset="5" style="display: flex; justify-content: center">
        <div>
          <el-image style="border-radius: 12px; width: 90%" :src="testGoods.image[currentImage]"/>

<!--          <el-scrollbar style="height: 130px; width: 30vw; margin-top: 4px">-->
<!--            <div style="display: flex">-->
<!--                <el-image-->
<!--                    v-for="src in testGoods.image"-->
<!--                    :key="src" :src="src" fit="contain"-->
<!--                    class="scrollbar-demo-item"-->
<!--                    @click.native="() => { console.log('nbnb') }"/>-->
<!--            </div>-->
<!--          </el-scrollbar>-->

          <el-scrollbar style="height: 150px; margin-top: 4px; overflow: hidden" always>
            <div style="display: flex; width: 15vw; padding: 12px 24px; ">
              <div v-for="(src, idx) in testGoods.image" :key="src" @click="switchImage(idx)">
                <el-image :src="src" fit="contain" class="image-thumb"/>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </el-col>

      <el-col :span="8" :offset="1" style="font-family: 微軟正黑體; color: white; text-align: left">
        <div>
          <p style="font-size: 52px; margin-bottom: 0; color: #f6eacc;">{{ testGoods.name }}</p>
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




          <el-button class="cart-button" style="border-radius: 16px 0 0 16px;">加入购物车</el-button>
          <el-button class="buy-button" style="border-radius: 0 16px 16px 0;">立即购买</el-button>
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
  font-weight: bolder;
  margin: 8px 0 0 0;
}

.cart-button {
  background: linear-gradient(to right, #c1ab85, #f6eacc);
  border: 1px solid #f6eacc;
  color: transparent;
  -webkit-background-clip: text;
}

.cart-button:hover {
  background: rgba(1,1,1, 0.7);
}

.buy-button {
  background: linear-gradient(to right, #f6eacc, #c1ab85);
  border: 1px solid #f6eacc;
  border-left: none;
  color: #010101;
}

</style>
