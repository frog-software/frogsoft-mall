<script setup lang="ts">
import { onMounted, ref } from "vue";

const count = ref<number>(0)
const searchInputIsFocus = ref<boolean>(false)

interface hotSearchWordItem {
  value: string,
  link: string,
}

const searchContent  = ref<string>('')
const hotSearchWords = ref<hotSearchWordItem[]>([])

const queryHotWords = (queryString: string, cb: any) => {
  const results = queryString
    ? hotSearchWords.value.filter(createFilter(queryString))
      : hotSearchWords.value

  cb(results)
}
const createFilter = (queryString: string) => {
  return (hotSearchWord: hotSearchWordItem) => {
    return (
        hotSearchWord.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

const loadAllHotWords = () => {
  return [
    { value: 'AAAAAAA', link: ''},
    { value: 'AAABBBB', link: ''},
    { value: 'CCCDDDD', link: ''},
  ]
}

onMounted(() => {
  hotSearchWords.value = loadAllHotWords()
})

</script>

<template>
  <div :class="{ overlay: searchInputIsFocus === true }" />
    <div style="padding-top: 24px;" class="rootClass">
      <el-row
          align="middle"
          justify="center"
          type="flex"
      >
              <el-col :span="7">
                <router-link to="/">
                  <img
                      src="/FrogsoftMall-White.svg"
                      alt="网站页眉LOGO"
                      style="max-width: 25vw"
                  >
                </router-link>
              </el-col>
        <el-col :span="6" style="margin-bottom: 24px">
          <el-autocomplete
              v-model="searchContent"
              :fetch-suggestions="queryHotWords"
              placeholder="开始搜索"
              style="width: 80%"
              @focus="searchInputIsFocus=true"
              @blur="searchInputIsFocus=false"

          />
        </el-col>
      </el-row>
    </div>
  </template>

<script lang="ts">
export default {
  name: "Header",
  data() {
    return {

    }
  },

  methods: {

  }
}
</script>

<style scoped>
:root {
  --searchInputDownText: 50px,
}

#searchInputDownText {
  width: var(--searchInputDownText);
}


.rootClass {
  background-color: #131313;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10001;
  display:none;
  filter:alpha(opacity=60);
  background-color: #777;
  opacity: 0.5;
  -moz-opacity: 0.5;
}

</style>
