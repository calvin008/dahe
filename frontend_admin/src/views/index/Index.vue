<!--  -->
<template>
  <div  class="charts">
   
    <!-- 移动端展示 -->
    <el-row v-if="mobileShow" :gutter="4">
        <el-col :span="24">
            <line-chart />
        </el-col >
        <el-col :span="24">
           <bar-chart />
        </el-col>
        <el-col :span="24">
           <map-chart />
        </el-col >
        <el-col :span="24">
          <pie-chart />
        </el-col>
        <el-col :span="24">
          <BarCharTo />
        </el-col>
    </el-row>
    <!-- pc端展示 -->
      <el-row v-else :gutter="4">
        <el-col :span="8">
         <line-chart />
         <bar-chart />
        </el-col>
        <el-col :span="12" >
         <map-chart />
        </el-col>
        <el-col :span="4" >
          <pie-chart />
          <BarCharTo />
        </el-col>
      </el-row>
  </div>
</template>

<script setup lang='ts'>
import PieChart from './components/PieChart.vue'
import LineChart from './components/LineChart.vue'
import BarChart from './components/BarChart.vue';
import BarCharTo from './components/BarChartTo.vue';
import MapChart from './components/MapChart.vue';
import { isMobile } from '../../utils/isMobile';
import { ref } from 'vue';

const mobileShow = ref<boolean>(false)

if (isMobile()) {
  mobileShow.value = true
}
</script>
<style lang='scss' scoped>
.charts {
  width: 100%;
  height: 100%;
}
</style>