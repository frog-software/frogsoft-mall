<script setup lang="ts">
import { getUserInformation }               from "../services/user";
import { UserModifyInfo, UserResponseInfo } from "../types/user";
import { ref, watch }                       from "vue";
import {
  Male,
  Female,
  Location,
  Wallet,
  Tickets,
  CreditCard,
  Box,
  ChatDotRound,
  Refresh,
  UserFilled,
  LocationFilled,
  Bottom,
}                                           from "@element-plus/icons-vue";
import { addressFormat }                    from "../utils/util.ts"
import { Address }                          from "../types/logistics";

const userInfo = ref<UserResponseInfo>()
const onEdit   = ref<boolean>(false)

const form = ref<UserModifyInfo>({
  avatar: '',
  nickname: '',
  id: '',
  phoneNum: '',
  gender: 0,
  addressList: [],
  accountList: [],
})

const submitEdit = () => {
  if (!onEdit.value) onEdit.value = true
  else {
    onEdit.value = false
    console.log('提交')
  }
}

const cancelEdit = () => {
  onEdit.value = false
  form.value   = JSON.parse(JSON.stringify(userInfo.value))
  delete form.value.balance
  delete form.value.purchaseHistory
  delete form.value.shopName
}

const creditcardFormat = (n: string) => {
  return "*".repeat(n.length - 4).concat(n.substring(n.length - 4))
}

getUserInformation('test').then(res => {
  userInfo.value = res
  form.value     = JSON.parse(JSON.stringify(userInfo.value))
  delete form.value.balance
  delete form.value.purchaseHistory
  delete form.value.shopName
})

watch(onEdit, (newVal) => {
  let edit   = document.getElementById('edit-button-1')
  let cancel = document.getElementById('edit-button-2')

  if (newVal) {
    edit.style.transform    = 'translateX(-104px)'
    cancel.style.transform  = 'translateX(-104px)'
    cancel.style.visibility = 'visible'
    cancel.style.opacity    = 1
  } else {
    edit.style.transform    = 'translateX(0)'
    cancel.style.transform  = 'translateX(0)'
    cancel.style.visibility = 'hidden'
    cancel.style.opacity    = 0
  }
})
</script>

<template>
  <div style="display: flex; justify-content: center">
    <div style="width: 1000px; font-family: 微軟正黑體; color: #222222">
      <p style="text-align: left; color: #ffffff; font-size: 36px; margin-top: 0">{{ userInfo?.nickname }}，你好</p>

      <el-row
          style="background: linear-gradient(to right, #f6eacc, #c1ab85); width: 100%; border-radius: 16px; height: 200px">
        <el-col :span="3" :offset="2" style="padding-top: 40px">
          <!--          <el-avatar :src="userInfo?.avatar || '/avataaars.svg'" style="width: 96px; height: 96px" />-->
          <el-avatar :src="'/avataaars.svg'" style="width: 96px; height: 96px"/>
        </el-col>
        <el-col :span="4" style="text-align: left; color: #010101; padding-top: 56px">
          <div>
            <p style="font-size: 20px; display: inline-block; margin: 0 8px">{{ userInfo?.nickname }}</p>
            <el-icon v-if="userInfo?.gender === 0" style="color: #1e90ff">
              <male/>
            </el-icon>
            <el-icon v-else-if="userInfo?.gender === 1" style="color: #f83e5b">
              <female/>
            </el-icon>
          </div>
          <div style="margin: 8px 0 0 4px">
            <div
                style="background: rgba(0, 0, 0, 0.1); display: inline-block; border-radius: 12px; padding: 1px 12px">
              <p style="margin: 0">V5会员</p>
            </div>
          </div>
        </el-col>

        <el-col :span="12" :offset="1" style="padding: 30px 0">
          <div style="width: 100%; background: rgba(255, 255, 255, 0.4); height: 100%; border-radius: 12px">
            <el-row style="height: 100%">
              <el-col style="align-items: center; display: flex" :span="6" :offset="4">
                <div style="">
                  <div style="display: flex; align-items: center; justify-content: center; ">
                    <el-icon>
                      <wallet/>
                    </el-icon>
                    <p style="margin: 0; font-size: 18px;">余额</p>
                  </div>
                  <div style="font-size: 24px; margin-top: 8px">
                    {{ userInfo?.balance.toFixed(2) }} RMB
                  </div>
                </div>
              </el-col>
              <el-col style="align-items: center; display: flex" :span="6" :offset="2">
                <div style="">
                  <div style="display: flex; align-items: center; justify-content: center; ">
                    <el-icon>
                      <tickets/>
                    </el-icon>
                    <p style="margin: 0; font-size: 18px;">积分</p>
                  </div>
                  <div style="font-size: 24px; margin-top: 8px">
                    0.00
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>

      <div style="margin-top: 48px">
        <p style="text-align: left; color: #ffffff; margin-left: 16px; font-size: 24px; font-weight: bold">我的订单</p>
        <div
            style="width: 100%; border-radius: 16px; height: 200px; border: 1px solid rgba(255, 255, 255, 0.5); overflow: hidden">
          <el-row style="height: 100%">
            <el-col :span="6" class="order-col">
              <el-icon :size="64">
                <credit-card/>
              </el-icon>
              <p>待支付</p>
            </el-col>
            <el-col :span="6" class="order-col">
              <el-icon :size="64">
                <box/>
              </el-icon>
              <p>待收货</p>
            </el-col>
            <el-col :span="6" class="order-col">
              <el-icon :size="64">
                <chat-dot-round/>
              </el-icon>
              <p>待评价</p>
            </el-col>
            <el-col :span="6" class="order-col" style="position: relative">
              <el-icon :size="64">
                <refresh/>
              </el-icon>
              <span
                  style="position: absolute; top: 63px; left: 117px; font-size: 24px; font-weight: bold; user-select: none">$</span>
              <p>退换退款</p>
            </el-col>
          </el-row>
        </div>
      </div>

      <div style="margin-top: 48px">
        <p style="text-align: left; color: #ffffff; margin-left: 16px; font-size: 24px; font-weight: bold">个人信息</p>
        <div
            style="width: 100%; border-radius: 16px; height: auto;
            border: 1px solid rgba(255, 255, 255, 0.5); overflow: hidden; padding: 16px 0 30px; background: #ffffff">
          <div style="display: flex; justify-content: end; margin-right: -80px">
            <el-button @click="submitEdit" id="edit-button-1">{{ onEdit ? '保存编辑' : '编辑信息' }}
            </el-button>
            <el-button @click="cancelEdit" id="edit-button-2" type="danger">取消编辑
            </el-button>
          </div>
          <el-tabs tab-position="left">
            <el-tab-pane>
              <template #label>
                <span>
                  <el-icon style="vertical-align: middle"><user-filled/></el-icon>
                  <span style="vertical-align: middle; margin-left: 4px">基本信息</span>
                </span>
              </template>

              <div style="margin-left: 24px">
                <el-form :model="form" label-width="auto" label-position="right">
                  <el-form-item label="个人昵称">
                    <el-input v-model="form.nickname" style="width: 240px" :disabled="!onEdit"/>
                  </el-form-item>
                  <el-form-item label="个人手机号">
                    <el-input v-model="form.phoneNum" style="width: 240px" :disabled="!onEdit"/>
                  </el-form-item>
                  <el-form-item label="性别">
                    <el-select v-model="form.gender" style="width: 240px" :disabled="!onEdit">
                      <el-option :key="0" label="男" :value="0"/>
                      <el-option :key="1" label="女" :value="1"/>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="头像">
                    <el-input v-model="form.avatar" style="width: 480px" :disabled="!onEdit"/>
                  </el-form-item>
                  <div style="text-align: left">
                    <el-image :src="form.avatar" style="width: 100px; height: 100px; margin-left: 100px"
                              :preview-src-list="[form.avatar]"
                    />
                    <el-image :src="form.avatar" style="width: 60px; height: 60px; margin-left: 20px"/>
                    <el-image :src="form.avatar" style="width: 30px; height: 30px; margin-left: 20px"/>
                  </div>
                </el-form>
              </div>
            </el-tab-pane>

            <el-tab-pane>
              <template #label>
                <span>
                  <el-icon style="vertical-align: middle"><location-filled/></el-icon>
                  <span style="vertical-align: middle; margin-left: 4px">地址列表</span>
                </span>
              </template>

              <div style="margin-left: 24px">
                <el-form :model="form" label-width="auto" label-position="right">
                  <div v-for="i in form.addressList" class="address-card">
                    <el-form-item label="姓名">
                      <el-input v-model="i.name" style="width: 240px" :disabled="!onEdit"/>
                    </el-form-item>
                    <el-form-item label="手机号码">
                      <el-input v-model="i.phone" style="width: 240px" :disabled="!onEdit"/>
                    </el-form-item>
                    <el-form-item label="详细地址">
                      <el-input v-model="i.address.details" style="width: 480px" :disabled="!onEdit"/>
                    </el-form-item>
                  </div>
                </el-form>
              </div>
            </el-tab-pane>

            <el-tab-pane>
              <template #label>
                <span>
                  <el-icon style="vertical-align: middle"><credit-card/></el-icon>
                  <span style="vertical-align: middle; margin-left: 4px">银行卡包</span>
                </span>
              </template>

              <div v-if="!onEdit" style="padding: 24px">
                <el-carousel type="card" height="200px" :interval="3000">
                  <el-carousel-item v-for="i in form.accountList" :key="i">
                    <div style="position: relative">
                      <el-avatar shape="circle" :size="52" style="position: absolute; left: 30px; top: 20px"/>
                      <h3 style="position: absolute; left: 100px; top: 4px">XX银行</h3>
                      <h1 style="position: absolute; right: 24px; top: 130px">{{ creditcardFormat(i) }}</h1>
                    </div>
                  </el-carousel-item>
                </el-carousel>
              </div>
              <div v-else style="padding: 24px">
                <el-form :model="form">
                  <div v-for="(i, idx) in form.accountList">
                    <el-form-item label="银行卡号">
                      <el-input v-model="form.accountList[idx]" style="width: 240px" :disabled="!onEdit"/>
                      <el-button style="margin-left: 24px" type="danger" @click="() => {form.accountList.splice(idx, 1)}">删除</el-button>
                    </el-form-item>
                  </div>
                  <div style="text-align: left">
                    <el-button type="primary" @click="() => {form.accountList.push('')}">添加银行卡</el-button>
                  </div>
                </el-form>
              </div>

            </el-tab-pane>
          </el-tabs>
        </div>
      </div>

    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "User.vue"
}
</script>

<style scoped>
.order-col {
  color: #ffffff;
  padding-top: 48px;
  height: 100%;
  cursor: pointer;
}

.order-col:hover {
  background: rgba(255, 255, 255, 0.2);
}

.order-col:hover .el-icon,
.order-col:hover span {
  color: #f6eacc;
  transition: all 0.4s linear;
  transform: rotateX(360deg);
}

#edit-button-1,
#edit-button-2 {
  transition: all 0.3s ease-in-out;
}

#edit-button-2 {
  opacity: 0;
  visibility: hidden;
}

.address-card {
  width: min-content;
  border: 1px solid #c1ab85;
  padding: 24px 24px 12px;
  border-radius: 12px;
  margin-bottom: 24px;
}

.el-carousel__item {
  border-radius: 12px;
  color: #ffffff;
  font-family: 微軟正黑體;
}

.el-carousel__item:nth-child(3n) {
  background: linear-gradient(135deg, #f8d845, #f04077, #bf55da);
}

.el-carousel__item:nth-child(3n + 1) {
  background: linear-gradient(135deg, #7F7FD5, #86A8E7, #91EAE4);
}

.el-carousel__item:nth-child(3n + 2) {
  background: linear-gradient(135deg, #03001e, #7303c0, #ec38bc, #fdeff9);
}
</style>
