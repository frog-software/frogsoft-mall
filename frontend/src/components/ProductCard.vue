<script setup lang="ts">
import { ref }                      from "vue";
import { CDN_URL }           from "../consts/urls"
import { contentFormat }     from "../utils/util";
import { ProductSimpleInfo } from "../types/product";
import { getDecimal }                 from "../utils/util";

const image = ref<string>(CDN_URL + '/goodscard-example.webp')

const props = defineProps<{
  product: ProductSimpleInfo
}>()
</script>

<template>
  <div class="card">
    <div style="height: 240px">
      <el-image
          style="width: 100%; height: 240px"
          fit="cover"
          :src="props.product.thumb"
      />
    </div>

    <div class="product-card-detail">
      <div style="padding-top: 16px">
        <p style="font-weight: bold; font-size: 20px; margin: 0">{{ props.product.productName || '商品名未定义' }}</p>
        <p style="font-size: 12px; color: #666666">{{
            contentFormat(props.product.description, 60)
          }}</p>
      </div>

      <div style="position: absolute; bottom: 0; left: 0; width: 100%">
        <p style="font-size: 12px; font-weight: bold; margin-left: 16px">RMB
          <span style="font-size: 18px; margin-left: 4px">{{ props.product.price.toFixed(0) }}</span>
          <span style="font-size: 15px">.{{ getDecimal(props.product.price) }}</span>
        </p>

        <div class="product-card-tag">
          <el-tag color="transparent" plain effect="dark" style="border: none">
            新品上市
          </el-tag>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ProductSimpleInfo } from "../types/product";

export default {
  name: "GoodsCard",
}
</script>

<style scoped>
.card {
  width: 250px;
  height: 400px;
  background: #f6f5f8;
  overflow: hidden;
  border-radius: 8px;
  user-select: none;

  display: inline-block;
  float: left;
}

.card:hover {
  box-shadow: 0 0 15px 3px rgba(246, 234, 204, 0.75);
}

.product-card-detail {
  position: relative;
  height: 160px;
  text-align: left;
  color: #111111;
  font-family: 微軟正黑體;
  padding: 0 24px;
}

.product-card-tag {
  width: 84px;
  background: linear-gradient(to right, #d52342, #f83e5b);
  border-radius: 6px;
  text-align: center;
  position: absolute;
  bottom: 12px;
  right: 12px;
}
</style>
