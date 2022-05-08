<template>
  <div class="input-box">
    <label>
      <input type="checkbox" style="visibility: collapse" @click="$emit('update:modelValue', !modelValue)" :checked="modelValue">
      <span class="yes"></span>
      {{ content }}
    </label>
  </div>
</template>

<script lang="ts">
export default {
  name: "CheckBox",
  props: {
    content: '',
    check: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: Boolean,
      default: false,
    }
  },
  emits: ['update:modelValue']
}
</script>

<style scoped>
.input-box {
  font-family: 微軟正黑體;
  color: #eeeeee;
  width: 20px;
  height: 20px;
  display: inline-block;
}

label {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.input-box label span {
  position: relative;
  display: inline-block;
  margin-right: 16px;

  width: 20px;
  height: 20px;

  transition: 0.5s;
}

.input-box label span::before,
.input-box label span::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  transition: 0.5s;
  background: #b3b3b3;
}
.input-box label span::before {
  width: 20px;
  height: 2px;
  box-shadow: 0 -18px 0 #b3b3b3;
}
.input-box label span::after {
  width: 2px;
  height: 20px;
  box-shadow: 18px 0 0 #b3b3b3;
}

input:checked ~ span.yes::before {
  background: #00ff00;
  box-shadow: none;
}

input:checked ~ span.yes::after {
  background: #00ff00;
  box-shadow: none;
  height: 55%;
}

input:checked ~ span.yes {
  transform: rotate(-45deg) translate(0px, -1px);
}
</style>
