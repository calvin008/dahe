<!--  -->
<template>
  <div>
     <div ref="main" style="width: 100%; height: 400px; margin: 15px 0; border:1px solid #333; box-shadow: 5px 5px 5px #888888;"></div>
  </div>
</template>

<script setup lang='ts'>
import * as echarts from 'echarts'
import { onMounted, ref, } from 'vue';

const main = ref()
onMounted(()=>{
  init()
})
const init=()=>{
  // 1通过dom初始化echarts 2ref虚拟化dom
  let mychart =echarts.init(main.value)
  // 数据源
  var option = {
 
  };

  // 数据源给予
  mychart.setOption(option)
}

</script>
<style lang='scss' scoped>
</style>