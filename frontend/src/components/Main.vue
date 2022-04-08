<script setup lang="ts">
import { onMounted, ref } from "vue";
import { StarFilled } from '@element-plus/icons-vue'

const count              = ref<number>(0)
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
  <div>
    <el-row justify="center" style="display: flex; align-items: center">
      <el-col :span="10">
        <div>
          <el-menu
              mode="horizontal"
              text-color="#606266"
              active-text-color="#86A8E7"
              background-color="transparent"
          >
            <el-menu-item index="1">
                首页
            </el-menu-item>
            <el-menu-item index="2">Processing Center</el-menu-item>
            <el-menu-item index="3">Processing Center</el-menu-item>
          </el-menu>
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
            style="width: 100%;"
        >
        </el-autocomplete>
      </el-col>
    </el-row>


  </div>
</template>

<script lang="ts">
export default {
  name: "Main"
}
</script>

<style scoped>

</style>
