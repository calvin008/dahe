<template>
  <div class="map">
    <div id="main" style="width: 100%; height: 400px"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { onMounted } from "vue";
export default {
  setup() {
    //methods
    const echartInit = () => {
      var myChart = echarts.init(document.getElementById("main"),'light');
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "ECharts 入门示例",
        },
        tooltip: {},
        legend: {
          data: ["销量","促销"],
        },
        xAxis: {
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
        },
        yAxis: {},
        series: [
          {
            name: "销量",
            type: "bar",
            data: [5, 20, 36, 10, 10, 20],
          },
           {
            name: "促销",
            type: "bar",
            data: [10, 20, 30, 20, 36, 20],
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    };
    //onMounted
    onMounted(() => {
      echartInit();
    });
    //return
    return {
      echartInit,
    };
  },
};
</script>