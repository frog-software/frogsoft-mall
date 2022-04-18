<script setup lang="ts">

// 商品描述内容过长则需要省略
const goodsDescriptionFormat = (str: string) => {
  const MAX_LENGTH = 55;

  if (str.length >= MAX_LENGTH)
    return str.substring(0, MAX_LENGTH) + "..."
  else return str
}

</script>

<template>
  <div class="card">
    <div class="photo">
      <el-image
          style="width: 100%; height: 100%"
          fit="cover"
          src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fy.zdmimg.com%2F202111%2F19%2F6197a5b7a9bac538.jpg_d250.jpg&refer=http%3A%2F%2Fy.zdmimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652883296&t=86b2ce25d59b5edf0b002e322d646ea4"
      />
    </div>

    <div style="margin-top: 140px">
      <h1 style="padding: 0 16px">这是一个商品名</h1>
      <p style="word-break: break-all; word-wrap: break-word; text-align: left; padding: 0 12px">
        {{ goodsDescriptionFormat('这是一个介绍这是一个介绍这是一个介绍这是一个介绍这是一个介绍这是一个介绍这是一个介绍这是一个介绍这是一个介绍这是一个介绍') }}
      </p>
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
  margin-left: 8px;
  margin-right: 8px;
  margin-bottom: 24px;
  background: rgba(255, 255, 255, 0.01);
  border-radius: 8px;
  backdrop-filter: blur(20px);
  border-top: 1px solid #f6eacc;
  border-left: 1px solid #f6eacc;
  border-right: 1px solid #777777;
  border-bottom: 1px solid #777777;
  box-shadow: 0 0 4px #f6eacc;

  height: 320px;
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: white;
}

.card .photo {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  transition: 0.5s;
}

.card:hover .photo {
  top: 20px;
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

</style>
