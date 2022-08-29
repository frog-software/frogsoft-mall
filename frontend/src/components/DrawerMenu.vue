<script setup lang="ts">
import { Component, ref, markRaw, nextTick } from "vue";
import {
  ShoppingCart,
  User,
  Service,
  Close,
  Promotion,
}                                            from '@element-plus/icons-vue'
import { getRobotResponse }        from "../services/chatRobot";
import { ElNotification }          from "element-plus";

const openDrawer = () => {
  let overlay = document.getElementById('robot-overlay')
  let drawer  = document.getElementById('robot-drawer')

  if (!overlay || !drawer) return

  overlay.style.opacity    = drawer.style.opacity = '1'
  drawer.style.transform   = 'translateX(-200px)'
  overlay.style.visibility = drawer.style.visibility = 'visible'
}

const closeDrawer = () => {
  let overlay = document.getElementById('robot-overlay')
  let drawer  = document.getElementById('robot-drawer')

  if (!overlay || !drawer) return

  overlay.style.opacity    = drawer.style.opacity = '0'
  drawer.style.transform   = 'translateX(0)'
  overlay.style.visibility = drawer.style.visibility = 'collapse'
}

interface routerLinkItem {
  pathName: string,
  icon: Component,
  content: string,
  callback: Function,
}

const routerLinkList = ref<routerLinkItem[]>([
  {
    pathName: 'CartPage',
    icon: markRaw(ShoppingCart),
    content: '购物车',
    callback: () => {
    },
  },
  {
    pathName: 'UserPage',
    icon: markRaw(User),
    content: '个人中心',
    callback: () => {
    },
  },
  {
    pathName: '',
    icon: markRaw(Service),
    content: '客服',
    callback: openDrawer,
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

const text = ref<string>('')
const messageList = ref<string[]>([])
const messageDisplayList = ref<{ role: number, content: string }[]>([])

const sendMessage = () => {
  if (!text.value) {
    ElNotification({
      title: '发送失败',
      message: '消息不能为空',
      type: 'error',
    })
    return
  }

  messageList.value.push(text.value)

  messageDisplayList.value.push({
    role: 1,
    content: text.value,
  })

  text.value = ''

  getRobotResponse(messageList.value).then(res => {
    messageDisplayList.value.push({
      role: 0,
      content: res.chatForNow,
    })
  })

  nextTick(() => {
    let elem = document.getElementById('messageBox')
    elem.scrollTop = elem.scrollHeight
  })
}
</script>

<template>
  <div class="menu">
    <input type="checkbox" class="menu-toggle" id="menu-toggle" @click="menuItemSwitch">
    <label for="menu-toggle" style="position: absolute; top: 0; bottom: 0"></label>

    <div>
      <div v-for="(item, idx) in routerLinkList" class="menu-item" :id="'menu-item-' + idx">
        <router-link :to="{ name: item.pathName }">
          <el-tooltip :content="item.content" placement="bottom" :hide-after="0" effect="customized">
            <el-button type="primary" :icon="item.icon" size="large" class="button" @click="item.callback"/>
          </el-tooltip>
        </router-link>
      </div>
    </div>
  </div>

  <el-affix style="position: absolute; left: -12px; top: 0">
    <div class="robot-overlay" id="robot-overlay">
      <div class="robot-drawer" id="robot-drawer">
        <el-button :icon="Close" @click="closeDrawer" class="close-drawer-button" circle size="large"/>
        <p style="color: #222222; text-align: center; font-size: 20px; font-weight: bold">聊天</p>
        <el-divider/>

        <div style="height: 60%; overflow-y: scroll; overflow-x: hidden; " id="messageBox">
          <div v-for="(i) in messageDisplayList" :class="i.role === 0 ? 'left-message' : 'right-message'">
            <div>
              <p style="width: 100%; margin: 0">{{ i.content }}</p>
            </div>
          </div>
        </div>

        <el-divider style="margin-bottom: 8px"/>
        <div style="display: flex; justify-content: center">
          <div style="width: 100%">
            <el-input
                v-model="text"
                type="textarea"
                :autosize="{ minRows: 5, maxRows: 5 }"
                placeholder="来问点什么吧"
                resize="none"
                @keydown.ctrl.enter.native="sendMessage"
            />
            <div style="margin-bottom: 8px" />

            <div style="text-align: right">
              <el-button :icon="Promotion" type="primary" style="width: 80px" @click="sendMessage">发送</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </el-affix>

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
  /*transition: transform 0.5s, top 0.5s;*/
  margin: auto;
  cursor: pointer;
  position: absolute;
  top: 24px;

  transition: all 0.4s;
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

.robot-overlay {
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

.robot-drawer {
  position: absolute;
  top: 10vh;
  right: 0;
  width: 550px;
  height: 80vh;
  padding: 8px 24px;
  border-radius: 24px;
  background: #ffffff;
  font-family: 微軟正黑體;
  overflow-y: auto;

  visibility: collapse;
  z-index: 2;

  transition: all 0.2s ease-in-out;
}

.close-drawer-button {
  position: absolute;
  top: 18px;
  right: 18px;
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

.left-message {
  text-align: left;
  display: flex;
  justify-content: flex-start;
  margin-bottom: 24px;
  margin-left: 8px;
}

.right-message {
  text-align: left;
  display: flex;
  justify-content: flex-end;
  margin-bottom: 24px;
  margin-right: 24px;
}

.left-message div,
.right-message div {
  position: relative;
  width: max-content;
  max-width: 80%;

  padding: 8px 12px;
  border-radius: 8px;

  font-family: 微軟正黑體;
}

.left-message div {
  background: #00B0FF;
  color: #FFFFFF;
  animation: slideFromLeft 0.2s ease-in-out;
}

@keyframes slideFromLeft {
  0% {
    margin-left: -200px;
    opacity: 0;
  }
  100% {
    margin-left: 0;
    opacity: 1;
  }
}

.right-message div {
  background: #ECEFF1;
  color: #222222;
  animation: slideFromRight 0.2s ease-in;
}

@keyframes slideFromRight {
  0% {
    margin-right: -200px;
    opacity: 0;
  }
  100% {
    margin-right: 0;
    opacity: 1;
  }
}

.left-message div:before,
.right-message div:before {
  content: '';

  width: 10px;
  height: 10px;
  display: block;

  transform: rotate(45deg);
  position: absolute;
  top: 14px;
}

.left-message div:before {
  background: #00B0FF;
  left: -5px;
}

.right-message div:before {
  background: #ECEFF1;
  right: -5px;
}
</style>
