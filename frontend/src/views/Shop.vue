<script setup lang="ts">

import GoodsCard from "../components/GoodsCard.vue";
import TopSwiper from "../components/TopSwiper.vue";

</script>

<template>
  <div style="justify-content: center; display: flex">
    <TopSwiper />
  </div>

  <el-row style="margin-top: 48px; display: flex; justify-content: center">
    <el-col :span="3">
      <div class="glass" style="height: 500px"></div>
    </el-col>

    <el-col :span="9" :offset="1">
      <div class="glass" style="padding: 24px">
        <el-carousel :interval="4000" type="card" :key="item" height="200px">
          <el-carousel-item v-for="item in 6">
            <h3>{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-col>
  </el-row>

  <el-row style="margin: 60px 18%" justify="space-between">
    <el-col :span="6" v-for="item in 12" :key="item">
      <router-link :to="{ path: '/goods' }" style="text-decoration: none">
        <GoodsCard/>
      </router-link>
    </el-col>
  </el-row>
</template>

<script lang="ts">

export default {
  name: "Shop",
}
</script>

<style scoped>
.glass {
  background: rgba(255, 255, 255, 0.01);
  border-radius: 10px;
  backdrop-filter: blur(20px);
  border-top: 1px solid #f6eacc;
  border-left: 1px solid #f6eacc;
  border-right: 1px solid #777777;
  border-bottom: 1px solid #777777;
  box-shadow: 0 0 4px #f6eacc;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;

}

.el-carousel__item:nth-child(2n) {
  background-color: #f6eacc;
  border-radius: 12px;
  box-shadow: 0 0 8px #333333;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #f6eacc;
  border-radius: 12px;
  box-shadow: 0 0 8px #333333;
}
</style>
