<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from "vue";
import {
  getActionAnalysis,
  getAgeAnalysis,
  getCategoryAnalysis,
  getGenderAnalysis, getPredictionAnalysis,
  getProvinceAnalysis,
  changePrefix,
  runActionAnalysis, runAgeAnalysis, runCategoryAnalysis, runGenderAnalysis, runProvinceAnalysis
}                                          from "../services/statistics";
import * as echarts                        from 'echarts';
import themeJSON           from '../consts/frogsoft-mall-theme.project.json'
import { convertData }     from "../consts/geoCoordMap";
import 'echarts/extension-src/bmap/bmap'
import { PredictPostInfo } from "../types/statistics";
import { ElNotification }  from "element-plus";

const initActionChart = () => {
  getActionAnalysis().then(res => {
    const actionChart = echarts.init(document.getElementById('actionChart') as HTMLElement, 'frogsoft-mall-theme');

    actionChart.setOption({
      title: {
        text: '用户行为分析',
        textStyle: {
          color: '#FFFFFF',
          fontStyle: 'normal',
          fontWeight: 'bold',
          fontFamily: '微軟正黑體',
          fontSize: 20,
        }
      },
      tooltip: {},
      xAxis: {
        data: ['点击商品', '添加至购物车', '购买商品', '订阅商品'],
        axisLabel: {
          show: true,
          textStyle: {
            color: '#cccccc'
          }
        }
      },
      yAxis: {},
      series: [
        {
          name: '次数',
          type: 'bar',
          data: [res.click, res.add, res.purchase, res.subscribe],
        }
      ]
    });
  })
}

const initAgeChart = () => {
  getAgeAnalysis().then(res => {
    const data = ref<number[][]>(
        new Array(3).fill(0).map(i =>
            new Array(8).fill(0)
        )
    )

    res.forEach(i => {
      data.value[i.gender][i.ageRange] += i.count
    })

    const ageChart = echarts.init(document.getElementById('ageChart') as HTMLElement, 'frogsoft-mall-theme')

    ageChart.setOption({
      title: {
        text: '用户年龄分析',
        textStyle: {
          color: '#FFFFFF',
          fontStyle: 'normal',
          fontWeight: 'bold',
          fontFamily: '微軟正黑體',
          fontSize: 20,
        }
      },
      tooltip: {},
      xAxis: {
        data: ['年龄未知', '0-18', '19-24', '25-29', '30-34', '35-39', '40-49', '50+'],
        axisLabel: {
          show: true,
          textStyle: {
            color: '#cccccc'
          }
        }
      },
      yAxis: {},
      series: new Array(3).fill({}).map((i, idx) => ({
        data: data.value[idx],
        type: 'bar',
        stack: 'age',
        name: idx ? idx % 2 ? '男' : '女' : '性别未知'
      }))
    })
  })
}

const initCategoryChart = () => {
  getCategoryAnalysis().then(res => {
    const categoryChart = echarts.init(document.getElementById('categoryChart') as HTMLElement, 'frogsoft-mall-theme');

    categoryChart.setOption({
      title: {
        text: '商品类别分析',
        textStyle: {
          color: '#FFFFFF',
          fontStyle: 'normal',
          fontWeight: 'bold',
          fontFamily: '微軟正黑體',
          fontSize: 20,
        }
      },
      tooltip: {},
      series: [
        {
          type: 'pie',
          data: res.map(i => ({
            value: i.count,
            name: '类别：' + i.category,
          }))
        }
      ]
    })
  })
}

const initGenderChart = () => {
  getGenderAnalysis().then(res => {
    const genderChart = echarts.init(document.getElementById('genderChart') as HTMLElement, 'frogsoft-mall-theme');

    genderChart.setOption({
      title: {
        text: '用户性别分析',
        textStyle: {
          color: '#FFFFFF',
          fontStyle: 'normal',
          fontWeight: 'bold',
          fontFamily: '微軟正黑體',
          fontSize: 20,
        }
      },
      tooltip: {},
      series: [
        {
          type: 'pie',
          data: [
            {
              value: res.male,
              name: '男性'
            },
            {
              value: res.female,
              name: '女性'
            },
            {
              value: res.unknown,
              name: '性别未知'
            },
          ]
        }
      ]
    })
  })
}

const initProvinceChart = () => {
  getProvinceAnalysis().then(res => {
    const provinceChart = echarts.init(document.getElementById('provinceChart') as HTMLElement)

    provinceChart.setOption({
      title: {
        text: '收货地址省份分析',
        left: 'center'
      },
      tooltip: {},
      bmap: {
        center: [120.31, -17],
        // roam: true,
        mapStyle: {
          styleJson: [
            {
              featureType: 'land',
              elementType: 'all',
              stylers: {
                color: '#f3f3f3'
              }
            },
            {
              featureType: 'boundary',
              elementType: 'all',
              stylers: {
                color: '#fefefe'
              }
            },
            {
              featureType: 'label',
              elementType: 'labels.text.fill',
              stylers: {
                color: '#999999'
              }
            }
          ]
        }
      },
      series: [
        {
          name: '个数',
          type: 'scatter',
          coordinateSystem: 'bmap',
          data: convertData(res),
          symbolSize: function (val: number[]) {
            return val[2] / 80;
          },
          encode: {
            value: 2
          },
          label: {
            formatter: '{b}',
            position: 'right',
            show: false
          },
          emphasis: {
            label: {
              show: true
            }
          }
        },
        {
          name: 'Top 5',
          type: 'effectScatter',
          coordinateSystem: 'bmap',
          data: convertData(
              res.sort(function (a, b) {
                return b.count - a.count;
              }).slice(0, 6)
          ),
          symbolSize: function (val: number[]) {
            return val[2] / 80;
          },
          encode: {
            value: 2
          },
          showEffectOn: 'render',
          rippleEffect: {
            brushType: 'stroke'
          },
          label: {
            formatter: '{b}',
            position: 'right',
            show: true
          },
          itemStyle: {
            shadowBlur: 10,
            shadowColor: '#333'
          },
          emphasis: {
            scale: true
          },
          zlevel: 1
        }
      ]
    })

  })


}

const user_id = ref<string>('')
const merchant_id = ref<string>('')

const predict = (data: PredictPostInfo) => {
  if (!user_id.value || !merchant_id.value) {
    ElNotification({
      title: '预测失败',
      message: '预测条件输入不完整',
      type: 'error',
    })
    return
  }

  getPredictionAnalysis(data).then(res => {
    if (res.prediction) {
      ElNotification({
        message: '该用户是回头客',
        type: 'success',
      })
    } else {
      ElNotification({
        message: '该用户不是回头客',
        type: 'warning',
      })
    }
  })
}

onMounted(() => {
  echarts.registerTheme('frogsoft-mall-theme', themeJSON.theme)

  initActionChart()
  initAgeChart()
  initCategoryChart()
  initGenderChart()
  initProvinceChart()
});

onBeforeUnmount(() => {
  echarts.dispose(document.getElementById('actionChart') as HTMLElement)
  echarts.dispose(document.getElementById('ageChart') as HTMLElement)
  echarts.dispose(document.getElementById('categoryChart') as HTMLElement)
  echarts.dispose(document.getElementById('genderChart') as HTMLElement)
  echarts.dispose(document.getElementById('provinceChart') as HTMLElement)
})

const restartActionAnalysis = async (id: string) => {
  let elem = document.getElementById(id)
  if (elem) {
    elem.style.background = '#73717C'
    elem.style.color = '#cccccc'
  }

  switch (id) {
    case 'restart-action':
      await runActionAnalysis()
      initActionChart()
      break
    case 'restart-age':
      await runAgeAnalysis()
      initAgeChart()
      break
    case 'restart-category':
      await runCategoryAnalysis()
      initCategoryChart()
      break
    case 'restart-gender':
      await runGenderAnalysis()
      initGenderChart()
      break
    case 'restart-province':
      await runProvinceAnalysis()
      // initProvinceChart()
      break
  }

  if (elem) {
    elem.style.background = '#DCD6F7'
    elem.style.color = '#444444'
  }
}
</script>

<template>
  <div style="display: flex; justify-content: center; color: #FFFFFF">
    <div style="width: 80%; padding-top: 32px">
      <el-row justify="center">
        <el-col :span="10">
          <div id="actionChart" style="width: 600px; height: 400px"/>
          <el-button style="background: #DCD6F7; width: 180px; height: 48px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #444444; transition: all 0.3s"
              @click="restartActionAnalysis('restart-action')" id="restart-action">重新预测</el-button>
        </el-col>
        <el-col :span="10" :offset="2">
          <div id="ageChart" style="width: 660px; height: 400px"/>
          <el-button style="background: #DCD6F7; width: 180px; height: 48px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #444444; transition: all 0.3s"
                     @click="restartActionAnalysis('restart-age')" id="restart-age">重新预测</el-button>
        </el-col>
      </el-row>

      <el-row justify="center" style="margin-top: 64px">
        <el-col :span="10">
          <div id="categoryChart" style="width: 600px; height: 400px"/>
          <el-button style="background: #DCD6F7; width: 180px; height: 48px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #444444; transition: all 0.3s; margin-top: 32px"
                     @click="restartActionAnalysis('restart-category')" id="restart-category">重新预测</el-button>
        </el-col>
        <el-col :span="10" :offset="2">
          <div id="genderChart" style="width: 600px; height: 400px"/>
          <el-button style="background: #DCD6F7; width: 180px; height: 48px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #444444; transition: all 0.3s; margin-top: 32px"
                     @click="restartActionAnalysis('restart-gender')" id="restart-gender">重新预测</el-button>
        </el-col>
      </el-row>

      <div style="margin-top: 64px; border: 1px solid #DCD6F7; padding: 32px 0; border-radius: 24px">
        <el-row justify="center">
          <el-col :span="12">
            <div>
              <span style="font-family: 微軟正黑體; font-weight: bold; font-size: 18px; margin-right: 32px">用户ID</span>
              <el-input v-model="user_id" placeholder="请输入用户ID" style=" width: 480px"/>
            </div>
            <div style="margin-top: 12px">
              <span style="font-family: 微軟正黑體; font-weight: bold; font-size: 18px; margin-right: 32px">商家ID</span>
              <el-input v-model="merchant_id" placeholder="请输入商家ID" style="width: 480px"/>
            </div>
          </el-col>
          <el-col :span="6">
            <div style="height: 100%; display: flex; align-items: center">
              <el-button style="background: linear-gradient(120deg, #DCD6F7, #7474B9); width: 180px; height: 48px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #eeeeee"
              @click="predict({ user_id: user_id, merchant_id: merchant_id})">预测</el-button>
            </div>
          </el-col>
        </el-row>
      </div>

      <div id="provinceChart" style="width: 100%; height: 800px; border-radius: 24px;
      overflow: hidden; margin-top: 96px; margin-bottom: 48px"/>
      <el-button style="background: #DCD6F7; width: 180px; height: 48px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #444444; transition: all 0.3s"
                 @click="restartActionAnalysis('restart-province')" id="restart-province">重新预测</el-button>
      <el-button style="background: #FFFFFF11; width: 20px; height: 30px;
              border: none; font-size: 18px; font-family: 微軟正黑體; font-weight: bold; color: #444444"
              @click="changePrefix();initActionChart();initAgeChart();initCategoryChart();initGenderChart();initProvinceChart()"></el-button>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "StatisticPage"
}
</script>

<style scoped>

</style>
