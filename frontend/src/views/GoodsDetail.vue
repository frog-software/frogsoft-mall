<script setup lang="ts">
import { ref }   from "vue";
import GoodsCard from "../components/GoodsCard.vue";

interface goods {
  name: string,
  image: string[],
  description: string,
  prize: number,
  category: string,
}

const testGoods = ref<goods>({
  name: 'iPad Pro',
  image: [
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',
    'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/ipad-pro-12-11-select-202104_GEO_CN?wid=545&hei=550&fmt=jpeg&qlt=95&.v=1617864714000',

  ],
  description: '先进的显示屏，两款尺寸各有精彩。11 英寸显示屏灵巧便携，令人沉浸。12.9 英寸 XDR 显示屏宽大绚丽，能尽显 HDR 内容的精彩。',
  prize: 123,
  category: '电子产品'
})

const test = (idx: number) => {
  console.log(idx)
}
</script>

<template>
  <div>
    <el-breadcrumb separator=">" style="font-size: 14px; margin-left: 16vw; ">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ testGoods.name }}</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row style="margin-top: 24px">
      <el-col :span="7" :offset="5" style="display: flex; justify-content: center">
        <div>
          <el-image style="border-radius: 12px; width: 90%" :src="testGoods.image[0]"/>

<!--          <el-scrollbar style="height: 130px; width: 30vw; margin-top: 4px">-->
<!--            <div style="display: flex">-->
<!--                <el-image-->
<!--                    v-for="src in testGoods.image"-->
<!--                    :key="src" :src="src" fit="contain"-->
<!--                    class="scrollbar-demo-item"-->
<!--                    @click.native="() => { console.log('nbnb') }"/>-->
<!--            </div>-->
<!--          </el-scrollbar>-->

          <el-scrollbar style="height: 132px; margin-top: 4px; overflow: hidden" always>
            <div style="display: flex; width: 15vw; padding: 12px 24px; ">
              <div v-for="(src, idx) in testGoods.image" :key="src" @click="test(idx)">
                <el-image :src="src" fit="contain" class="image-thumb"/>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </el-col>

      <el-col :span="8" :offset="1" style="font-family: 微軟正黑體; color: white; text-align: left">
        <div>
          <p style="font-size: 52px; margin-bottom: 0">{{ testGoods.name }}</p>
          <p style="margin-top: 24px">{{ testGoods.description }}</p>
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
  width: 80px;
  margin-top: 12px;
  margin-right: 20px;
  border-radius: 4px;
  cursor: pointer;
}

.image-thumb:hover {
  box-shadow: 0 0 30px 5px rgba(246,234,204,0.52);
}

</style>
