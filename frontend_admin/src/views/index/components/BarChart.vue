<!--  -->
<template>
  <div>
     <div ref="main" style="width: 100%; height: 400px; margin: 15px 0; border:1px solid #333; box-shadow: 5px 5px 5px #888888;"></div>
  </div>
</template>

<script setup lang='ts'>
import * as echarts from 'echarts'
import { onMounted, reactive, ref, } from 'vue';
import { bar } from '@/assets/chart';

const main = ref()
const state =reactive({
    xdata:bar.xdata,
    small:bar.small,
    medium:bar.medium,
    large:bar.large
})
onMounted(()=>{
  init()
})
const init= async ()=>{
   
  // 1通过dom初始化echarts 2ref虚拟化dom
  let mychart =echarts.init(main.value)
  // 数据源
  var option = {
      title:{
          text:'SKU销售周报',
          x:'center'
      },
      tooltip:{
          trigger:'item'
      },
      legend:{
          show:true,
          top:'10%'
      },
      xAxis:{
          type:'category',
          data:state.xdata
      },
      yAxis:{

      },
      series:[
          {
              name:'小小份',
              type:'bar',
              stack:'销量',
              barWidth:'60%',
              data:state.small

          },{
              name:'中份',
              type:'bar',
              stack:'销量',
              barWidth:'60%',
              data:state.medium

          },{
              name:'大份',
              type:'bar',
              stack:'销量',
              barWidth:'60%',
              data:state.large

          }
      ]

  };

  // 数据源给予
  mychart.setOption(option)
}

</script>
<style lang='scss' scoped>
</style>