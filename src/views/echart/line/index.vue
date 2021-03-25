<!--  -->
<template>
  <div>
    <div id="main" style="width:100%; height:400px"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { onMounted } from 'vue'
export default {
  setup() {
    const echartInit = () =>{
      var myChart = echarts.init(document.getElementById("main"),'dark')

      var option = {
         title: {
          text: "ECharts 入门示例",
        },
        tooltip: {
          
        },
        legend: {
          data: ["销量","促销"],
        },
        xAxis: {
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
        },
        yAxis: {},
        dataset:{
            source:[
              ["衬衫",5,20,"王大合",30],
              ["羊毛衫",20,36,"王小河",30],
              ["雪纺衫",36,10,"王小军",40],
              ["裤子",10,5],
              ["高跟鞋",10,10],
              ["袜子",20,30]
            ]
        },
        series: [
          {
            name: "销量",
            type: "line",
            encode:{x:0,y:2}
          },
           {
            name: "促销",
            type: "line",
            encode:{x:0,y:1}
          },
           
          
        ],
      }

      myChart.setOption(option)
    }

    onMounted(()=>{
      echartInit()
    })

    return {
      echartInit
    }
  }
}

</script>
