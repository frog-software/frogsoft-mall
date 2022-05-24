<script setup lang="ts">
import { getAllOrdersCustomer }               from "../services/order/customer";
import { Component, markRaw, onMounted, ref } from "vue";
import { OrderSimpleInfo }                    from "../types/order";
import {
  Box,
  ChatDotRound,
  CreditCard,
  ArrowRight,
  Ship,
  Close,
}                                             from "@element-plus/icons-vue";
import {
  getStatusString,
  getStatusColor,
  getDecimal,
  contentFormat
}                                             from "../utils/util";
import OrderCard                              from "../components/OrderCard.vue";

const props      = defineProps<{
  tabIndex: string
}>()
const currentTab = ref<number>(Number(props.tabIndex) || 0)

interface TabItem {
  icon: Component
  name: string
}

const tabItemList = ref<TabItem[]>([
  {
    icon: markRaw(Ship),
    name: '全部',
  },
  {
    icon: markRaw(CreditCard),
    name: '待支付',
  },
  {
    icon: markRaw(Box),
    name: '待收货',
  },
  {
    icon: markRaw(ChatDotRound),
    name: '待评价',
  },
])

const switchTabUnderline = (index: number) => {
  let tabUnderline = document.getElementById('underline')

  if (!tabUnderline) return
  tabUnderline.style.left = (62 + index * 224) + 'px'

  for (let i = 0; i < tabItemList.value.length; i++) {
    let tabText = document.getElementById('tab-root-' + i)
    let tabIcon = document.getElementById('tab-icon-' + i)

    if (!tabText || !tabIcon) continue
    if (i === index) {
      tabText.style.color = tabIcon.style.color = '#f6eacc'
    } else {
      tabText.style.color = tabIcon.style.color = '#777777'
    }
  }

  currentTab.value = index
}

const currentOrderDetail = ref<OrderSimpleInfo>()

const openDrawer = (i: OrderSimpleInfo) => {
  currentOrderDetail.value = i

  let overlay = document.getElementById('overlay')
  let drawer  = document.getElementById('drawer')

  if (!overlay || !drawer) return

  overlay.style.opacity    = drawer.style.opacity = '1'
  drawer.style.transform   = 'translateX(-200px)'
  overlay.style.visibility = drawer.style.visibility = 'visible'
}

const closeDrawer = () => {
  let overlay = document.getElementById('overlay')
  let drawer  = document.getElementById('drawer')

  if (!overlay || !drawer) return

  overlay.style.opacity    = drawer.style.opacity = '0'
  drawer.style.transform   = 'translateX(0)'
  overlay.style.visibility = drawer.style.visibility = 'collapse'
}

const orderDetailsList = ref<OrderSimpleInfo[]>([])
const orderListOne = ref<OrderSimpleInfo[]>([])
const orderListTwo = ref<OrderSimpleInfo[]>([])
const orderListThree = ref<OrderSimpleInfo[]>([])
const orderListFour = ref<OrderSimpleInfo[]>([])
const orderListFive = ref<OrderSimpleInfo[]>([])

getAllOrdersCustomer().then(res => {
  let idx   = 0
  let timer = setInterval(() => {
    orderDetailsList.value?.push(res[idx])

    switch (res[idx].status) {
      case -1:  // 已取消
        orderListOne.value.push(res[idx])
        break
      case 1:   // 已提交
        orderListTwo.value.push(res[idx])
        break
      case 2:   // 已支付
        orderListThree.value.push(res[idx])
        break
      case 3:   // 已发货
        orderListFour.value.push(res[idx])
        break
      case 4:   // 已完成
        orderListFive.value.push(res[idx])
        break
    }

    idx++

    if (idx === res.length) clearInterval(timer)
  }, 100)
})

onMounted(() => {
  switchTabUnderline(currentTab.value)
})
</script>

<template>
  <div style="min-height: calc(100vh - 252px); display: flex; justify-content: center; width: 100%">
    <div style="width: 1200px; color: #ffffff">
      <div v-for="(i, idx) in tabItemList" class="tab-root" @click="switchTabUnderline(idx)" :id="'tab-root-' + idx">
        <div style="display: flex; align-items: center; justify-content: center">
          <el-button :icon="i.icon" class="tab-icon" :id="'tab-icon-' + idx"/>
          <p style="margin: 8px">{{ i.name }}</p>
        </div>
      </div>
      <div class="tab-underline-fixed"/>
      <div class="tab-underline" id="underline"/>

      <div style="padding: 0 60px; margin-top: 80px">
        <div v-if="currentTab === 0">
          <OrderCard v-for="i in orderDetailsList" @click="openDrawer(i)" :order="i"/>
          <div v-if="!orderDetailsList.length">
            <el-empty description="空空如也"/>
          </div>
        </div>
        <div v-else-if="currentTab === 1">
          <OrderCard v-for="i in orderListTwo" @click="openDrawer(i)" :order="i"/>
          <div v-if="!orderListTwo.length">
            <el-empty description="空空如也"/>
          </div>
        </div>
        <div v-else-if="currentTab === 2">
          <OrderCard v-for="i in orderListThree" @click="openDrawer(i)" :order="i"/>
          <div v-if="!orderListThree.length">
            <el-empty description="空空如也"/>
          </div>
        </div>
        <div v-else-if="currentTab === 3">
          <OrderCard v-for="i in orderListFive" @click="openDrawer(i)" :order="i"/>
          <div v-if="!orderListFive.length">
            <el-empty description="空空如也"/>
          </div>
        </div>
      </div>
    </div>

    <el-affix style="position: absolute; left: 0; top: 0">
      <div class="overlay" id="overlay">
        <div class="drawer" id="drawer">
          <el-button :icon="Close" @click="closeDrawer" class="close-drawer-button" circle size="large"/>
          <p style="color: #222222; text-align: center; font-size: 20px; font-weight: bold">订单详情</p>
          <el-divider/>

          <el-descriptions
              title="订单信息"
              :column="1"
              border
          >
            <template #extra>
              <el-tag effect="dark" :color="getStatusColor(currentOrderDetail?.status)" style="border: none">
                {{ getStatusString(currentOrderDetail?.status) }}
              </el-tag>
            </template>
            <el-descriptions-item label="店家名称">
              {{ currentOrderDetail?.sellerName }}
            </el-descriptions-item>
            <el-descriptions-item label="买家名称">
              {{ currentOrderDetail?.buyerName }}
            </el-descriptions-item>
            <el-descriptions-item label="订单号">
              {{ currentOrderDetail?.id }}
            </el-descriptions-item>
            <el-descriptions-item label="下单时间">
              {{ currentOrderDetail?.orderTime }}
            </el-descriptions-item>
            <el-descriptions-item label="订单备注">
              {{ currentOrderDetail?.remarks }}
            </el-descriptions-item>
            <el-descriptions-item label="目的地址">
              {{ currentOrderDetail?.logisticsAddress.detailAddress }}
            </el-descriptions-item>
            <el-descriptions-item label="物流单号">
              {{ currentOrderDetail?.logisticsNumber }}
            </el-descriptions-item>
          </el-descriptions>

          <p style="font-size: 16px; font-weight: bold; text-align: left; margin-top: 30px">购物详情</p>
          <div v-for="i in currentOrderDetail?.products">
            <el-row>
              <el-col :span="6">
                <el-image :src="i.thumb"
                          style="width: 100px; height: 100px; border: 1px solid #dddddd; border-radius: 8px"
                          fit="contain"/>
              </el-col>
              <el-col :span="18" style="color: #222222; padding: 8px">
                <div>
                  <p style="margin: 0; display: inline-block; float: left; font-weight: bold">{{ i.productName }}</p>
                  <p style="margin: 0; display: inline-block; float: right; color: #ff5400; font-weight: bold">
                    <span style="font-size: 12px">RMB</span>
                    <span style="margin-left: 4px; font-size: 18px">{{ i.orderPrice }}</span>
                    <span style="font-size: 16px">.{{ getDecimal(i.orderPrice) }}</span>
                  </p>
                </div>

                <div style="margin-top: 24px">
                  <p style="margin: 0; font-size: 12px; color: #999999; display: inline-block; float: left; text-align: left">
                    {{ i.id }}</p>
                  <p style="margin: 0; font-size: 14px; color: #444444; display: inline-block; float: right; text-align: right">
                    x {{ i.amount }}</p>
                </div>
              </el-col>
            </el-row>
          </div>

          <div style="font-weight: bold; margin-top: 8px; margin-bottom: 64px">
            <p style="display: inline-block; float: left">总金额</p>
            <p style="display: inline-block; float: right; color: #ff5400; margin-top: 10px">
              <span style="font-size: 16px">RMB</span>
              <span style="margin-left: 4px; font-size: 22px">{{ currentOrderDetail?.totalPrice }}</span>
              <span style="font-size: 18px">.{{ getDecimal(currentOrderDetail?.totalPrice) }}</span>
            </p>
          </div>
        </div>
      </div>
    </el-affix>
  </div>
</template>

<script lang="ts">
export default {
  name: "OrderDetails"
}
</script>

<style scoped>
.tab-root {
  display: inline-block;
  float: left;
  width: 200px;
  font-size: 20px;
  color: #777777;
  cursor: pointer;
  margin: 0 12px;
  border-radius: 12px;
  user-select: none;
  transition: all 0.2s;
}

.tab-root:hover {
  background: rgba(255, 255, 255, 0.15);
}

.tab-icon {
  border: none;
  background: transparent;
  width: 1em;
  font-size: 24px;
  color: #999999;
  margin: 0;
  transition: all 0.2s;
}

.tab-underline-fixed {
  position: relative;
  top: 64px;
  left: 36px;
  width: 1000px;
  height: 4px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 2px;
}

.tab-underline {
  position: relative;
  top: 60px;
  left: 62px;
  width: 100px;
  height: 4px;
  background: linear-gradient(to right, #f6eacc, #c1ab85);
  transition: all 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.5);
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: calc(99vw + 2px);
  background: rgba(0, 0, 0, 0.6);
  height: 100vh;
  z-index: 2;
  visibility: collapse;

  transition: all 0.2s ease-in-out;
}

.drawer {
  position: absolute;
  top: 10vh;
  right: 0;
  width: 550px;
  height: 80vh;
  padding: 8px 24px;
  border-top-left-radius: 24px;
  border-bottom-left-radius: 24px;
  background: #ffffff;
  font-family: 微軟正黑體;
  overflow-y: scroll;

  visibility: collapse;
  z-index: 2;

  transition: all 0.2s ease-in-out;
}

.close-drawer-button {
  margin: 12px 4px 0 0;
  width: 48px;
  height: 48px;
  font-size: 24px;
  float: right;
  border: none;
}

.close-drawer-button:hover {
  color: #7b7c80;
  background: rgba(0, 0, 0, 0.1);
}
</style>
