<script setup lang="ts">

import { ref, watch }                                         from "vue";
import { addShop, getShopInfo, updateShopInfo }               from "../services/shop";
import { ShopPostInfo, ShopResponseInfo }                     from "../types/shop";
import { ElNotification }                                                    from "element-plus";
import { addProduct, deleteProduct, getProductDetailsPaging, updateProduct } from "../services/product";
import { ProductDetails, ProductPostInfo }                                   from "../types/product";
import { getDecimal }                                         from "../utils/util";
import {
  Delete,
  More,
  InfoFilled,
  Close,
}                                                             from "@element-plus/icons-vue";

const shopInfoForm = ref<ShopPostInfo>({
  shopName: '',
  shopImage: '',
})
const productForm  = ref<ProductPostInfo>({
  shopId: 0,
  category: '',
  brand: '',
  productName: '',
  price: 0,
  description: '',
  imageList: [''],
})

const categoryList = ref<{
  label: string,
}[]>([
  {
    label: '运动健康'
  },
  {
    label: '智能家居'
  },
])

const clearProductForm = () => {
  productForm.value.shopId      = shopDetail.value?.id || 0
  productForm.value.category    = ''
  productForm.value.brand       = ''
  productForm.value.productName = ''
  productForm.value.price       = 0
  productForm.value.description = ''
  productForm.value.imageList   = ['']
}

const onEdit = ref<boolean>(false)

const cancelShopInfoEdit = () => {
  onEdit.value                 = false
  shopInfoForm.value.shopName  = shopDetail.value?.shopName || ''
  shopInfoForm.value.shopImage = shopDetail.value?.shopImage || ''
}

const postShopInfo = () => {
  if (!shopDetail.value?.id) {
    ElNotification({
      title: '修改失败',
      type: 'error',
    })
    return
  }

  updateShopInfo(shopDetail.value?.id, shopInfoForm.value).then(res => {
    ElNotification({
      title: '修改成功',
      type: 'success',
    })

    onEdit.value = false
  })
}

const registerShop = () => {
  if (!shopDetail.value) {
    if (!shopInfoForm.value.shopName || !shopInfoForm.value.shopImage) {
      ElNotification({
        message: '注册信息不能为空！',
        type: 'error',
      })
    } else {
      addShop(shopInfoForm.value).then(res => {
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

const shopDetail  = ref<ShopResponseInfo>()
const productList = ref<ProductDetails[]>([])
const currentPage = ref<number>(1)
const pageSize    = ref<number>(16)

const handleCurrentChange = () => {
  getProductDetailsPaging({
    shop_id: shopDetail.value?.id,
    page: currentPage.value,
    size: pageSize.value,
  }).then(resInner => {
    productList.value = resInner
    console.log(resInner)
  })
}

const deleteProductInMyShop = (product: ProductDetails) => {
  deleteProduct(product.productId).then(res => {
    ElNotification({
      title: '删除成功',
      message: '"' + product.productName + '"' + ' 已从商店移出',
      type: 'success',
    })
    getProductDetailsPaging({
      shop_id: shopDetail.value?.id,
      page: currentPage.value,
      size: pageSize.value,
    }).then(resInner => {
      productList.value = resInner
    })
  }).catch(res => {
    ElNotification({
      title: '删除失败',
      type: 'error',
    })
  })
}

const deleteProductImage = (imageList: string[], idx: number) => {
  if (imageList.length === 1) {
    ElNotification({
      title: '删除图片失败',
      message: '商品至少需要一张图片',
      type: 'error',
    })
  } else {
    imageList.splice(idx, 1)
  }
}

// TODO: 表单校验需优化（为空、格式要求）
const submitProduct = () => {
  for (let i = 0; i < productForm.value.imageList.length; i++) {
    if (!productForm.value.imageList[i]) {
      ElNotification({
        title: '添加失败',
        message: '商品资料不完整',
        type: 'error',
      })
      return
    }
  }

  if (!productForm.value.productName || !productForm.value.description || !productForm.value.brand || !productForm.value.category) {
    ElNotification({
      title: '添加失败',
      message: '商品资料不完整',
      type: 'error',
    })
    return
  } else {
    console.log('yes')
    addProduct(productForm.value).then(res => {
      ElNotification({
        title: '添加成功',
        message: '"' + productForm.value.productName + '"' + '成功添加到商店中',
        type: 'success',
      })
      clearProductForm()
    })
  }
}

const currentProductId = ref<number>(0)
const currentProductDetail = ref<ProductPostInfo>({
  shopId: 0,
  category: '',
  brand: '',
  productName: '',
  price: 0,
  description: '',
  imageList: [''],
})

const openDrawer = (i: ProductDetails) => {
  currentProductId.value = i.productId

  currentProductDetail.value.shopId      = i.shop.id
  currentProductDetail.value.category    = i.category
  currentProductDetail.value.brand       = i.brand
  currentProductDetail.value.productName = i.productName
  currentProductDetail.value.price       = i.price
  currentProductDetail.value.description = i.description
  currentProductDetail.value.imageList   = i.imageList

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

const onEditProductDetail = ref<boolean>(false)

const editProductDetail = () => {
  if (onEditProductDetail.value) {
    // TODO: 表单校验需优化（为空、格式要求）

    updateProduct(currentProductId.value, currentProductDetail.value).then(res => {
      ElNotification({
        title: '修改成功',
        type: 'success',
      })
      onEditProductDetail.value = false
    })
  }
}

getShopInfo(123).then(res => {
  shopDetail.value = res

  shopInfoForm.value.shopName  = res.shopName
  shopInfoForm.value.shopImage = res.shopImage

  getProductDetailsPaging({
    shop_id: shopDetail.value?.id,
    page: currentPage.value,
    size: pageSize.value,
  }).then(resInner => {
    productList.value = resInner
    console.log(resInner)
  })
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

watch(onEditProductDetail, (newVal: boolean) => {
  let cancelButton = document.getElementById('cancelEditProductDetail')

  if (!cancelButton) return

  if (newVal) {
    cancelButton.style.opacity    = '1'
    cancelButton.style.visibility = 'visible'
  } else {
    cancelButton.style.opacity    = '0'
    cancelButton.style.visibility = 'collapse'
  }
})
</script>

<template>
  <div style="min-height: calc(100vh - 252px); width: 100%">
    <div style="display: flex; justify-content: center">
      <div style="width: 1000px">
        <div>
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
                    <el-form :model="shopInfoForm" label-width="240" id="shopInfoForm"
                             style="transition: all 0.2s ease-in-out; visibility: collapse; opacity: 0; transform: translateX(200px)">
                      <el-form-item label="商店名称">
                        <el-input v-model="shopInfoForm.shopName" style="width: 480px"/>
                      </el-form-item>
                      <el-form-item label="商店封面">
                        <el-input v-model="shopInfoForm.shopImage" style="width: 480px"/>
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
                  <el-form :model="shopInfoForm">
                    <el-form-item label="商店名称">
                      <el-input v-model="shopInfoForm.shopName" style="width: 360px"/>
                    </el-form-item>
                    <el-form-item label="商店头像">
                      <el-input v-model="shopInfoForm.shopImage" style="width: 360px"/>
                    </el-form-item>
                  </el-form>
                </el-col>
                <el-col :span="12">
                  <el-image :src="shopInfoForm.shopImage" style="width: 100px; height: 100px; margin-left: 100px"
                            :preview-src-list="[shopInfoForm.shopImage]"
                  />
                  <el-image :src="shopInfoForm.shopImage" style="width: 60px; height: 60px; margin-left: 20px"/>
                  <el-image :src="shopInfoForm.shopImage" style="width: 30px; height: 30px; margin-left: 20px"/>
                </el-col>
              </el-row>

              <el-button style="margin-left: 108px; width: 80px" type="primary" @click="registerShop">注册</el-button>
            </div>
          </div>
        </div>

        <div style="margin-top: 40px">
          <p style="text-align: left; color: #ffffff; margin-left: 16px; font-size: 24px; font-weight: bold">添加商品</p>
          <div style="width: 100%; background: #ffffff; border-radius: 12px; padding: 24px 0">
            <el-row>
              <el-col :span="8" :offset="1">
                <el-form :model="productForm" style="margin-left: 24px">
                  <el-form-item label="商品名称">
                    <el-input v-model="productForm.productName" style="width: 240px" placeholder="请输入商品名称"/>
                  </el-form-item>
                  <el-form-item label="商品类别">
                    <el-select v-model="productForm.category" filterable style="width: 240px" placeholder="输入或选择商品类别">
                      <el-option
                          v-for="item in categoryList"
                          :key="item.label"
                          :label="item.label"
                          :value="item.label"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="商品品牌">
                    <el-input v-model="productForm.brand" style="width: 240px" placeholder="请输入商品品牌"/>
                  </el-form-item>
                  <el-form-item label="商品价格">
                    <el-input-number v-model="productForm.price" :step="1" step-strictly :min="0.01" :precision="2"
                                     style="width: 240px"/>
                  </el-form-item>
                </el-form>
              </el-col>
              <el-col :span="12" :offset="1">
                <el-form :model="productForm" style="margin-left: 24px">
                  <el-form-item label="商品描述">
                    <el-input
                        v-model="productForm.description"
                        :autosize="{ minRows: 6, maxRows: 6 }"
                        type="textarea"
                        placeholder="请输入商品描述"
                        style="width: 480px"
                    />
                  </el-form-item>
                </el-form>
              </el-col>
            </el-row>
            <el-form :model="productForm" style="margin-left: 66px">
              <el-form-item label="商品图片">
                <div v-for="(i, idx) in productForm.imageList" style="margin-bottom: 16px">
                  <el-input v-model="productForm.imageList[idx]" style="width: 480px" placeholder="请输入商品图片链接"/>
                  <el-button style="margin-left: 24px" type="danger"
                             @click="deleteProductImage(productForm.imageList, idx)">删除
                  </el-button>
                </div>
              </el-form-item>
              <div style="text-align: left; margin-left: 428px; margin-top: -16px">
                <el-button style="width: 120px" type="primary" @click="() => productForm.imageList.push('')">添加图片
                </el-button>
              </div>
            </el-form>

            <div style="text-align: left; margin-left: 144px; margin-top: 20px">
              <el-button style="width: 120px; margin-top: -28px" @click="submitProduct">提交</el-button>
            </div>
          </div>
        </div>

        <div style="width: 100%; height: 100px; margin-top: 42px">
          <p style="text-align: left; color: #ffffff; margin-left: 16px; font-size: 24px; font-weight: bold">商品信息</p>
          <div style="width: 100%; background: #ffffff; border-radius: 12px">
            <el-row v-if="productList.length">
              <el-col :span="6" v-for="product in productList" style="display: flex; justify-content: center">
                <div
                    style="width: 80%; text-align: left; padding: 8px; border-radius: 8px; margin: 12px 0; box-shadow: 0 0 15px 5px rgba(0, 0, 0, 0.2);">
                  <el-row>
                    <el-col :span="10">
                      <el-image :src="product.thumb" style="width: 80px; height: 80px"/>
                    </el-col>
                    <el-col :span="14" style="color: #222222">
                      <p style="margin: 4px 0 0 6px; font-size: 12px; font-weight: bold">{{ product.productName }}</p>
                      <p style="font-size: 6px; font-weight: bold; margin-left: 6px; color: #ff5400">RMB
                        <span style="font-size: 14px; margin-left: 4px">{{ product.price.toFixed(0) }}</span>
                        <span style="font-size: 10px">.{{ getDecimal(product.price) }}</span>
                      </p>
                      <el-button type="text" :icon="More" style="color: rgb(30, 144, 255); margin-left: 4px"
                                 @click="openDrawer(product)">详情
                      </el-button>
                      <el-popconfirm title="确定要删除吗？" :icon="InfoFilled" confirmButtonText="确定" cancelButtonText="取消"
                                     @confirm="deleteProductInMyShop(product)">
                        <template #reference>
                          <el-button type="text" style="color: #f83e5b" :icon="Delete">删除</el-button>
                        </template>
                      </el-popconfirm>
                    </el-col>
                  </el-row>
                </div>
              </el-col>
            </el-row>
            <div v-else style="display: flex; justify-content: center; width: 100%">
              <el-empty description="商店空空如也"/>
            </div>

            <el-pagination
                v-model:currentPage="currentPage"
                v-model:page-size="pageSize"
                layout="prev, pager, next, jumper"
                :total="80"
                :hide-on-single-page="true"
                @current-change="handleCurrentChange"
                style="float: right; margin-top: 24px"
            />
          </div>
        </div>
      </div>
    </div>

    <el-affix style="position: absolute; left: 0; top: 0">
      <div class="overlay" id="overlay">
        <div class="drawer" id="drawer">
          <el-button :icon="Close" @click="closeDrawer" class="close-drawer-button" circle size="large"/>
          <p style="color: #222222; text-align: center; font-size: 20px; font-weight: bold">商品详情</p>
          <el-divider/>

          <el-form :model="currentProductDetail">
            <el-form-item label="商品名称">
              <el-input v-model="currentProductDetail.productName" style="width: 240px" placeholder="请输入商品名称"
                        :disabled="!onEditProductDetail"/>
            </el-form-item>
            <el-form-item label="商品类别">
              <el-select v-model="currentProductDetail.category" filterable style="width: 240px" placeholder="输入或选择商品类别"
                         :disabled="!onEditProductDetail">
                <el-option
                    v-for="item in categoryList"
                    :key="item.label"
                    :label="item.label"
                    :value="item.label"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="商品品牌">
              <el-input v-model="currentProductDetail.brand" style="width: 240px" placeholder="请输入商品品牌"
                        :disabled="!onEditProductDetail"/>
            </el-form-item>
            <el-form-item label="商品价格">
              <el-input-number v-model="currentProductDetail.price" :step="1" step-strictly :min="0.01" :precision="2"
                               style="width: 240px" :disabled="!onEditProductDetail"/>
            </el-form-item>
            <el-form-item label="商品图片">
              <div v-for="(i, idx) in currentProductDetail.imageList" style="margin-bottom: 16px">
                <el-input v-model="currentProductDetail.imageList[idx]" style="width: 360px" placeholder="请输入商品图片链接"
                          :disabled="!onEditProductDetail"/>
                <el-button style="margin-left: 24px" type="danger"
                           @click="deleteProductImage(currentProductDetail.imageList, idx)"
                           :disabled="!onEditProductDetail">删除
                </el-button>
              </div>
            </el-form-item>
            <div style="text-align: left; margin-left: 68px; margin-top: -16px">
              <el-button style="width: 120px" type="primary" @click="() => currentProductDetail.imageList.push('')"
                         :disabled="!onEditProductDetail">添加图片
              </el-button>
            </div>
            <el-form-item label="商品描述" style="margin-top: 24px">
              <el-input
                  v-model="currentProductDetail.description"
                  :autosize="{ minRows: 6, maxRows: 6 }"
                  type="textarea"
                  placeholder="请输入商品描述"
                  style="width: 480px"
                  :disabled="!onEditProductDetail"
              />
            </el-form-item>
          </el-form>

          <div style="text-align: left; margin-left: 68px; margin-bottom: 64px">
            <el-button @click="() => onEditProductDetail ? editProductDetail() : onEditProductDetail = true"
                       type="primary">{{ onEditProductDetail ? '保存编辑' : '编辑信息' }}
            </el-button>
            <el-button @click="() => onEditProductDetail = false"
                       style="opacity: 0; visibility: collapse; transition: all 0.3s" id="cancelEditProductDetail">取消编辑
            </el-button>
          </div>
        </div>
      </div>
    </el-affix>
  </div>
</template>

<script lang="ts">
export default {
  name: "MyShopPage"
}
</script>

<style scoped>
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
