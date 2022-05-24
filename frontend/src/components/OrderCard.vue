<script setup lang="ts">
import { OrderSimpleInfo } from "../types/order";
import {
  contentFormat,
  getDecimal,
  getStatusString,
  getStatusColor
}                          from "../utils/util";
import {
  ArrowRight,
  Shop,
  Location,
  Tickets,
}                          from "@element-plus/icons-vue";

const props = defineProps<{
  order: OrderSimpleInfo
}>()
</script>

<template>
  <div style="color: #010101; font-size: 18px" class="order-card">
    <div style="display: inline-block; width: 100%">
      <div class="shop-name-title">
        <el-icon :size="24">
          <Shop/>
        </el-icon>
        <span style="margin: 0 2px 0 8px">{{ props.order.sellerName }}</span>
        <el-icon style="color: #999999">
          <ArrowRight/>
        </el-icon>
      </div>

      <div style="float: right; margin-top: -24px; font-weight: bold">
        <el-tag effect="dark" :color="getStatusColor(props.order.status)" style="border: none">
          {{ getStatusString(props.order.status) }}
        </el-tag>
      </div>
    </div>

    <div
        style="margin-top: 8px; background: rgba(255, 255, 255, 0.3); border-radius: 12px; padding: 8px 24px; color: #222222">
      <div v-for="(i, idx) in props.order.products">
        <div v-if="idx < 3" style="font-size: 14px">
          <p style="margin: 0; text-align: left; display: inline-block">{{ i.productName }}</p>
          <p style="margin: 0; text-align: right; display: inline-block; float: right">x {{ i.amount }}</p>
        </div>
        <div v-else-if="idx === 3">
          <p style="margin: 0; text-align: left; display: inline-block">...</p>
        </div>
      </div>

    </div>

    <div style="position: absolute; bottom: 12px; left: 18px; right: 18px">
      <span style="position: absolute; bottom: 26px; color: rgba(255, 255, 255, 0.5); font-size: 14px">{{ props.order.id }}</span>

      <span style="position: absolute; bottom: 1px; color: #222222; font-size: 14px">{{
          contentFormat(props.order.logisticsAddress.detailAddress, 12)
        }}</span>

      <p style="text-align: right; margin: 0; position: absolute; bottom: 0; right: 0; color: #222222; font-size: 12px">
        总金额
        <span style="font-size: 14px; margin-left: 4px">RMB</span>
        <span style="font-size: 24px; margin-left: 8px">{{ props.order.totalPrice.toFixed(0) }}</span>
        <span style="font-size: 20px">.{{ getDecimal(props.order.totalPrice) }}</span>
      </p>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "OrderCard"
}
</script>

<style scoped>
.order-card {
  width: 480px;
  height: 180px;
  border-radius: 12px;
  background: linear-gradient(150deg, #f6eacc, #c1ab85);
  margin: 12px;
  text-align: left;
  padding: 12px;

  display: block;
  float: left;

  cursor: pointer;

  transition: all 0.2s;

  animation-name: order-card-top-in;
  animation-duration: 0.2s;
  animation-timing-function: ease-in-out;
  animation-iteration-count: 1;
  animation-fill-mode: forwards;
}

.order-card:hover {
  transition: all 0.1s;
  box-shadow: 0 0 15px 5px rgba(246, 234, 204, 0.75);
}

@keyframes order-card-top-in {
  0% {
    transform: translateY(100px);
  }
  100% {
    transform: translateY(0);
  }
}

.shop-name-title {
  font-size: 16px;
  display: flex;
  align-items: center;
  width: max-content;
  border-radius: 8px;
}

.shop-name-title:hover {
  background: rgba(255, 255, 255, 0.5);
}
</style>
