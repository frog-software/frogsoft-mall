<script setup lang="ts">
import { Component, ref } from "vue";
import {
  ShoppingCart,
  User,
}                         from '@element-plus/icons-vue'

interface routerLinkItem {
  path: string,
  icon: Component,
}

const routerLinkList = ref<routerLinkItem[]>([
  {
    path: '/cart',
    icon: ShoppingCart,
  },
  {
    path: '/user',
    icon: User,
  },
])
</script>

<template>
  <nav class="menu">
    <input type="checkbox" class="menu-toggle" id="menu_toggle" >
    <label for="menu_toggle"></label>

    <div>
      <div v-for="item in routerLinkList" class="menu-item">
        <router-link :to="{ path: item.path }">
          <el-button type="primary" :icon="item.icon" circle size="large" class="button"/>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script lang="ts">
export default {
  name: "RotateMenu"
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

.menu-toggle {
  position: absolute;
  display: block;
  top: 0;
  bottom: 0;
  right: 24.1vw;
  width: 32px;
  height: 32px;
  z-index: 2;
  opacity: 0;
  margin: auto;
  cursor: pointer;
}

.menu-toggle:hover + label,
.menu-toggle:hover + label::before,
.menu-toggle:hover + label::after {
  background: white;
}

.menu-toggle:checked + label {
  background: transparent;
}

.menu-toggle:checked + label::before,
.menu-toggle:checked + label::after {
  top: 0;
  width: 36px;
  transform-origin: 50% 50%;
}

/* 上中下三条横杠 */
.menu-toggle:checked + label::before {
  transform: rotate(45deg);
}

.menu-toggle ~ div .menu-item {
  visibility: collapse;
}

.menu-toggle:checked ~ div .menu-item {
  opacity: 1;
  visibility: visible;
}

.menu-toggle:checked + label::after {
  transform: rotate(-45deg);
}


.menu-toggle:checked ~ div .menu-item:nth-child(1) {
  transform: translateX(80px);
}

.menu-toggle:checked ~ div .menu-item:nth-child(2) {
  transform: translateX(160px);
}

.menu-toggle + label {
  width: 36px;
  height: 5px;
  display: block;
  z-index: 6;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.7);
  transition: transform 0.5s, top 0.5s;
  margin: auto;

  position: absolute;
  top: 24px;
  right: 24vw;
  cursor: pointer;
}

.menu-toggle + label::before,
.menu-toggle + label::after {
  width: 36px;
  height: 5px;
  display: block;
  z-index: 6;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.7);
  transition: transform 0.5s, top 0.5s;
  content: '';
  position: absolute;
  left: 0;
}

.menu-toggle + label::before {
  top: 10px;
}

.menu-toggle + label::after {
  top: -10px;
}

.menu-item {
  position: absolute;
  display: block;
  top: 0;
  right: 23.5vw;
  margin: auto;
  opacity: 0;
  transition: all 0.5s;
  z-index: 5;
}

.button {
  height: 56px;
  width: 56px;
  margin: auto;
  font-size: 28px;
  color: #c1ab85;
  background: rgba(255, 255, 255, 0.02);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(255, 255, 255, 0.8);
}

.button:hover {
  background: rgba(255, 255, 255, 0.3);
}

</style>
