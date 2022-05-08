<script setup lang="ts">
import CheckBox from "../components/CheckBox.vue";
import {
  GoodsFilled,
  Minus,
  Plus
} from "@element-plus/icons-vue";
import { ref } from "vue";
import { CartResponseInfo } from "../types/cart";
import { getCartDetails } from "../services/cart";

const e = ref<boolean>(false)
const f = ref<boolean>(false)

const cartInfo = ref<CartResponseInfo>()

// 商品描述内容过长则需要省略
const goodsDescriptionFormat = (str: string) => {
  const MAX_LENGTH = 80;

  if (str.length >= MAX_LENGTH)
    return str.substring(0, MAX_LENGTH) + "..."
  else return str
}

getCartDetails('testUser').then(res => {
  cartInfo.value = res
  console.log(res)
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

      <div v-for="shop in cartInfo?.shops">
        <div style="display: flex; align-items: center; margin-left: 96px; color: #f6eacc">
          <CheckBox/>
          <el-icon :size="24" style="margin-left: 16px">
            <goods-filled/>
          </el-icon>
          <p style="margin: 0 0 0 4px">店铺：{{ shop.shop.shopName }}</p>
        </div>

        <div>
          <div v-for="item in shop.items" style="width: 1140px; margin: 16px 0">
            <div class="card-item">
              <el-row style="width: 100%; align-items: center;">
                <el-col :span="1" style="justify-content: center">
                  <CheckBox id="card-item-checkbox" style="margin-left: 18px"/>
                </el-col>
                <el-col :span="3">
                  <el-image style="width: 100px; height: 100px" :src="item.product.thumb" fit="fill"/>
                </el-col>
                <el-col :span="10" style="text-align: left">
                  <p style="margin: 0; font-weight: bold">{{ item.product.productName }}</p>
                  <p style="margin: 12px 0 0 0; color: #999999">{{
                      goodsDescriptionFormat(item.product.description)
                    }}</p>
                </el-col>
                <el-col :span="3">
                  <p>{{ item.product.price }}</p>
                </el-col>
                <el-col :span="4" >
                  <el-button :icon="Minus" class="num-control-button-left" :disabled="item.number === 1"
                             @click="item.number--"/>
                  <input v-model="item.number" class="num-control"/>
                  <el-button :icon="Plus" class="num-control-button-right" @click="item.number++"/>
                </el-col>
                <el-col :span="3">
                  <el-button type="danger" plain>删除</el-button>
                </el-col>
              </el-row>
            </div>
          </div>
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
}

#card-item-checkbox:checked ~ .card-item {
  background: #f6eacc;
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
</style>
