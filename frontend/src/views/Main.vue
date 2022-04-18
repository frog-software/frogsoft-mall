<script setup lang="ts">
import { onMounted, ref } from "vue";
import NavigatorBar       from "../components/NavigatorBar.vue";
import TopSwiper from "../components/TopSwiper.vue";

const searchInputIsFocus = ref<boolean>(false)

// 搜索栏可自动补全的热词
interface hotSearchWordItem {
  content: string,
  link: string,
}

// 用户搜索的内容
const searchContent  = ref<string>('')
// 热词数组
const hotSearchWords = ref<hotSearchWordItem[]>([])

const queryHotWords = (currentSearchContent: string, callback: any) => {
  const results = currentSearchContent
      ? hotSearchWords.value.filter(createFilter(currentSearchContent))
      : hotSearchWords.value

  callback(results)
}
const createFilter  = (queryString: string) => {
  return (hotSearchWord: hotSearchWordItem) => {
    return (
        hotSearchWord.content.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

const loadAllHotWords = () => {
  return [
    {value: 'AAAAAAA', link: ''},
    {value: 'AAABBBB', link: ''},
    {value: 'CCCDDDD', link: ''},
  ]
}

onMounted(() => {
  hotSearchWords.value = loadAllHotWords()
})

</script>

<template>


  <el-row justify="center" style="display: flex; align-items: center; margin-top: 32px">
    <el-col :span="10">
      <div>
        <NavigatorBar/>
      </div>
    </el-col>
    <el-col :span="4" :offset="1">
      <el-autocomplete
          v-model="searchContent"
          :fetch-suggestions="queryHotWords"
          placeholder="开始搜索"
          @focus="searchInputIsFocus=true"
          @blur="searchInputIsFocus=false"
          size="large"
          style="width: 100%; background-color: transparent"
      >
      </el-autocomplete>
    </el-col>
  </el-row>

  <router-view/>


</template>

<script lang="ts">
export default {
  name: "Main"
}
</script>

<style scoped>

</style>
