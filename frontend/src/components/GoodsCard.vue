<script setup lang="ts">
import { ref }                  from "vue";
import { CDN_URL } from "../consts/urls"
import {productDescriptionFormat} from "../utils/util";

const image = ref<string>(CDN_URL + '/goodscard-example.webp')

</script>

<template>
  <div class="card">
    <el-image
        style="width: 100%"
        fit="cover"
        :src=image
    />

    <div>
      <div class="goods-detail">
        <p style="font-weight: bold; font-size: 20px; margin: 0">{{ '这是一个商品名' }}</p>
        <p style="font-size: 12px; color: #666666">{{
            productDescriptionFormat('这是一个商品描述这是一个商品描述这是一个商品描述这是一个商品描述这是一个商品描述这是一个商品描述这是一个商品描述这是一个商品描述', 43)
          }}</p>

        <el-row style="display: flex; align-items: center">
          <el-col :span="12">
            <p style="font-size: 14px; margin-top: 8px; color: #000000; font-weight: bold">RMB <span>{{ '22' }}</span></p>
          </el-col>
          <el-col :span="8" :offset="3">
            <div style="width: 84px; background: linear-gradient(to right, #d52342, #f83e5b); border-radius: 6px; text-align: center">
              <el-tag color="transparent" plain effect="dark" style="border: none">
                新品上市
              </el-tag>
            </div>
          </el-col>
        </el-row>
      </div>


    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "GoodsCard"
}
</script>

<style scoped>
.card {
  width: 256px;
  height: 400px;
  background: #f6f5f8;
  overflow: hidden;
  border-radius: 8px;
  user-select: none;
}

.card:hover {
  box-shadow: 0 0 15px 3px rgba(246,234,204,0.75);
}

.goods-detail {
  text-align: left;
  color: #111111;
  font-family: 微軟正黑體;
  padding: 0 24px;
}

</style>
