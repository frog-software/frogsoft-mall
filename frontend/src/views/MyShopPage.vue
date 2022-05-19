<script setup lang="ts">

import { ref, watch }                           from "vue";
import { addShop, getShopInfo, updateShopInfo } from "../services/shop";
import { ShopPostInfo, ShopResponseInfo }       from "../types/shop";
import { ElNotification }                 from "element-plus";

const form = ref<ShopPostInfo>({
  shopName: '',
  shopImage: '',
})

const onEdit = ref<boolean>(false)

const cancelShopInfoEdit = () => {
  onEdit.value         = false
  form.value.shopName  = shopDetail.value?.shopName || ''
  form.value.shopImage = shopDetail.value?.shopImage || ''
}

const postShopInfo = () => {
  if (!shopDetail.value?.id) {
    ElNotification({
      title: '修改失败',
      type: 'error',
    })
    return
  }

  updateShopInfo(shopDetail.value?.id, form.value).then(res => {
    ElNotification({
      title: '修改成功',
      type: 'success',
    })

    onEdit.value = false
  })
}

const registerShop = () => {
  if (!shopDetail.value) {
    if (!form.value.shopName || !form.value.shopImage) {
      ElNotification({
        message: '注册信息不能为空！',
        type: 'error',
      })
    } else {
      addShop(form.value).then(res => {
        ElNotification({
          title: '注册成功',
          type: 'success',
        })
        getShopInfo(res.id).then(res => {
          shopDetail.value = res
        })
      }).catch(res => {
        ElNotification({
          title: '注册失败',
          type: 'error',
        })
      })
    }
  }
}

const shopDetail = ref<ShopResponseInfo>()

getShopInfo(123).then(res => {
  shopDetail.value = res

  form.value.shopName  = res.shopName
  form.value.shopImage = res.shopImage

  console.log(res)
}).catch(res => {
  console.log(res)
})

watch(onEdit, (newVal: boolean) => {
  let card         = document.getElementById('shopInfoCard')
  let elem         = document.getElementById('shopInfoForm')
  let buttonGroup  = document.getElementById('edit-button-group')
  let cancelButton = document.getElementById('cancel-edit-button')

  if (!elem || !card || !buttonGroup || !cancelButton) return

  if (newVal) {
    elem.style.transform  = buttonGroup.style.transform = 'translateX(0)'
    elem.style.opacity    = cancelButton.style.opacity = '1'
    elem.style.visibility = cancelButton.style.visibility = 'visible'
    card.style.height     = '300px'
  } else {
    buttonGroup.style.transform = 'translateX(110px)'
    elem.style.transform        = 'translateX(200px)'
    elem.style.opacity          = cancelButton.style.opacity = '0'
    elem.style.visibility       = cancelButton.style.visibility = 'collapse'
    card.style.height           = '200px'
  }
})
</script>

<template>
  <div style="min-height: calc(100vh - 252px)">
    <div style="display: flex; justify-content: center">
      <div style="width: 1000px">
        <p style="text-align: left; color: #ffffff; margin-left: 16px; font-size: 24px; font-weight: bold">商店信息</p>
        <div style="width: 100%; background: #ffffff; border-radius: 12px">
          <div style="padding: 32px 0; height: 200px; transition: all 0.2s" id="shopInfoCard" v-if="shopDetail">
            <el-row>
              <el-col :span="6" :offset="1">
                <el-image :src="shopDetail?.shopImage" style="width: 200px; height: 200px"/>
              </el-col>
              <el-col :span="15" :offset="1" style="text-align: left; padding-top: 12px">
                <el-descriptions border :column="1">
                  <el-descriptions-item label="商店名称">
                    {{ shopDetail?.shopName }}
                  </el-descriptions-item>
                  <el-descriptions-item label="商店评分">
                    <div style="margin-top: 8px">
                      <el-rate
                          :model-value="shopDetail?.rate"
                          disabled
                          allow-half
                          :colors="['#f6eacc', '#f6eacc', '#f6eacc']"
                          disabled-void-color="transparent"
                      />
                    </div>
                  </el-descriptions-item>
                  <el-descriptions-item label="商店销量">
                    {{ shopDetail?.tradeQuantity }}
                  </el-descriptions-item>
                </el-descriptions>

                <div style="margin-top: 16px; transform: translateX(110px); transition: all 0.2s ease-in-out"
                     id="edit-button-group">
                  <el-button style="float: right; visibility: collapse; opacity: 0; transition: all 0.2s ease-in-out"
                             @click="cancelShopInfoEdit" id="cancel-edit-button" type="danger">取消编辑
                  </el-button>
                  <el-button style="float: right; margin-right: 24px"
                             @click="() => {onEdit ? postShopInfo() : onEdit = true}">{{
                      onEdit ? '保存编辑' : '编辑信息'
                    }}
                  </el-button>
                </div>

                <div style="margin-top: 64px">
                  <el-form :model="form" label-width="240" id="shopInfoForm"
                           style="transition: all 0.2s ease-in-out; visibility: collapse; opacity: 0; transform: translateX(200px)">
                    <el-form-item label="商店名称">
                      <el-input v-model="form.shopName" style="width: 480px"/>
                    </el-form-item>
                    <el-form-item label="商店封面">
                      <el-input v-model="form.shopImage" style="width: 480px"/>
                    </el-form-item>
                  </el-form>
                </div>
              </el-col>
            </el-row>
          </div>
          <div v-else style="text-align: left; padding: 32px">
            <p style="margin: 0; font-size: 24px">还没有自己的商店？现在注册！</p>
            <el-row style="margin-top: 24px">
              <el-col :span="10" :offset="1" style="padding-top: 16px">
                <el-form :model="form">
                  <el-form-item label="商店名称">
                    <el-input v-model="form.shopName" style="width: 360px"/>
                  </el-form-item>
                  <el-form-item label="商店头像">
                    <el-input v-model="form.shopImage" style="width: 360px"/>
                  </el-form-item>
                </el-form>
              </el-col>
              <el-col :span="12">
                <el-image :src="form.shopImage" style="width: 100px; height: 100px; margin-left: 100px"
                          :preview-src-list="[form.shopImage]"
                />
                <el-image :src="form.shopImage" style="width: 60px; height: 60px; margin-left: 20px"/>
                <el-image :src="form.shopImage" style="width: 30px; height: 30px; margin-left: 20px"/>
              </el-col>
            </el-row>

            <el-button style="margin-left: 108px; width: 80px" type="primary" @click="registerShop">注册</el-button>
          </div>
        </div>

        <div style="width: 100%; height: 100px; margin-top: 64px; background: #d52342">

        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "MyShopPage"
}
</script>

<style scoped>

</style>
