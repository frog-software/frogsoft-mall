<script setup lang="ts">
import { Component, ref, markRaw } from "vue";
import {
  ShoppingCart,
  User,
}                                  from '@element-plus/icons-vue'

interface routerLinkItem {
  path: string,
  icon: Component,
  content: string,
}

const routerLinkList = ref<routerLinkItem[]>([
  {
    path: '/cart',
    icon: markRaw(ShoppingCart),
    content: '购物车',
  },
  {
    path: '/user',
    icon: markRaw(User),
    content: '个人中心',
  },
])

const menuItemSwitch = () => {
  if ((document.getElementById('menu-toggle') as HTMLInputElement).checked) {
    for (let i = 0; i < routerLinkList.value.length; i++) {
      let elem = document.getElementById('menu-item-' + i)
      if (!elem) continue
      elem.style.transform = 'translateX(' + 60 * (i + 1) + 'px)'
    }
  } else {
    for (let i = 0; i < routerLinkList.value.length; i++) {
      let elem = document.getElementById('menu-item-' + i)
      if (!elem) continue
      elem.style.transform = ''
    }
  }
}
</script>

<template>
  <div class="menu" style="background: #42b983">
    <input type="checkbox" class="menu-toggle" id="menu-toggle" @click="menuItemSwitch">
    <label for="menu-toggle" style="position: absolute; top: 0; bottom: 0"></label>

    <div>
      <div v-for="(item, idx) in routerLinkList" class="menu-item" :id="'menu-item-' + idx">
        <router-link :to="{ path: item.path }">
          <el-tooltip :content="item.content" placement="bottom" :hide-after="0" effect="customized">
            <el-button type="primary" :icon="item.icon" size="large" class="button"/>
          </el-tooltip>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "RotateMenu"
}
</script>

<style scoped>

/* 触发框 */
.menu-toggle {
  position: absolute;
  top: 0;
  bottom: 0;

  /* 只能以这种方式设置不可见 */
  display: block;
  opacity: 0;

  width: 32px;
  height: 32px;
  z-index: 2;

  cursor: pointer;
  margin: auto;
}

/* 中杠 */
.menu-toggle + label::before,
.menu-toggle + label,
.menu-toggle + label::after {
  width: 32px;
  height: 3px;
  display: block;
  z-index: 1;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.7);
  transition: transform 0.5s, top 0.5s;
  margin: auto;
  cursor: pointer;
  position: absolute;
  top: 24px;
}

/* 上下杠公用 */
.menu-toggle + label::before,
.menu-toggle + label::after {
  width: 32px;
  height: 3px;
  display: block;
  z-index: 1;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.7);
  transition: transform 0.5s, top 0.5s;
  content: '';
  position: absolute;
}

/* 横杠悬浮 */
.menu-toggle:hover + label,
.menu-toggle:hover + label::before,
.menu-toggle:hover + label::after {
  background: white;
}

.menu-toggle:checked + label::before,
.menu-toggle:checked + label::after {
  top: 0;
  width: 32px;
  transform-origin: 50% 50%;
}

/* 横杠触发 */
.menu-toggle:checked + label::before {
  background: white;
  transform: rotate(45deg);
}

.menu-toggle:checked + label {
  background: transparent;
}

.menu-toggle:checked + label::after {
  background: white;
  transform: rotate(-45deg);
}

/* 关闭时按钮禁用 */
.menu-toggle ~ div .menu-item {
  opacity: 0;
  visibility: collapse;
}

.menu-toggle:checked ~ div .menu-item {
  opacity: 1;
  visibility: visible;
}

/* 上下杠位置 */
.menu-toggle + label::before {
  top: 10px;
}

.menu-toggle + label::after {
  top: -10px;
}

.menu-item {
  position: absolute;
  display: block;
  top: 6px;
  bottom: 0;
  margin: auto;
  visibility: collapse;
  transition: all 0.5s;
  z-index: 1;
}

.button {
  height: 48px;
  width: 48px;
  font-size: 30px;
  margin: auto;
  color: rgba(255, 255, 255, 0.7);
  background: transparent;
  border: none;
}

.button:hover {
  color: white;
}
</style>
