<script setup lang="ts">
import { onMounted, ref } from "vue";

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
  <div style="padding-top: 24px; background-color: #222222">
    <el-row
        align="middle"
        justify="center"
        type="flex"
    >
      <el-col :span="7" style="align-items: center; display: flex">
        <el-button type="text" style="max-height: 1em; padding: 0; margin-top: -24px" >
<!--          <img-->
<!--              src="/FrogsoftMall-White.svg"-->
<!--              alt="网站页眉LOGO"-->
<!--              style="max-width: 25vw"-->
<!--          >-->
          <div class="LogoText">
            <p class="LogoTextContent">Frogsoft Mall</p>
          </div>
        </el-button>
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
    return {}
  },

  methods: {}
}
</script>

<style scoped>
.LogoText {
  /*background-color: #111;*/
  justify-content: center;
  align-items: center;

}

.LogoTextContent {
  color: #555555;
  text-transform: uppercase;
  font-size: 52px;
  font-weight: bold;
  font-family: monospace;
  position: relative;
}

.LogoTextContent:after {
  content: "Frogsoft Mall";
  color: transparent;
  position: absolute;
  left: 0;
  background: linear-gradient(to right, #FEAC5E, #C779D0, #4BC0C8);
  background-clip: content-box;
  -webkit-background-clip: text;

  clip-path: circle(144px at 0% 50%);
  animation: light 5s infinite;
}

@keyframes light {
  0% {
    clip-path: circle(64px at 0% 50%);
  }
  50% {
    clip-path: circle(64px at 100% 50%);
  }
  100% {
    clip-path: circle(64px at 0% 50%);
  }
}

</style>
