<!--  -->
<template>
    <div>
        <div
            ref="main"
            style="width: 100%; height: 815px; margin: 15px 0; border:1px solid #333; box-shadow: 5px 5px 5px #888888;"
        ></div>
    </div>
</template>

<script setup lang='ts'>
import * as echarts from 'echarts'
import { onMounted, ref, } from 'vue';
import { chinaMap } from '../../../assets/map/china'
import { map } from '@/assets/chart';

const main = ref()
const data = ref(map)
onMounted(() => {
    init()
})
const init = async () => {
   
    // 1通过dom初始化echarts 2ref虚拟化dom
    let mychart = echarts.init(main.value)
    //   注册地图数据
    echarts.registerMap('chinaMap', chinaMap as any)
    // 数据源
    var option = {
        title: {
            text: '门店城市销量排行',
            x: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        geo: {
            map: 'chinaMap',
            type: 'map',
            zoom: 1.2,
            itemStyle: {
                areaColor: '#009fcc',
                borderColor: '#0ffff',
                shadowColor: 'rgba(230,130,70,0.5)',
                shadowBlur: 30,
            },
            emphasis: {
                focus: 'self',
                areaColor: 'yellow',
                shadowBlur: 20,
                shadowColor: 'rgba(0,0,0,0.5)'
            }

        },
        visualMap: {
            left: 'right',
            min: 100,
            max: 500,
            inRange: {
                color: [
                    '#313695',
                    '#4575b4',
                    '#74add1',
                    '#abd9e9',
                    '#e0f3f8',
                    '#ffffbf',
                    '#fee090',
                    '#fdae61',
                    '#f46d43',
                    '#d73027',
                    '#a50026'
                ]
            },
            text:['高销量','低销量'],
            calculable:true
        },
        series: [
            {
                type: 'scatter',//散点图
                coordinateSystem: 'geo',
                itemStyle: {
                    borderColor: '#06b9d1',
                    color: 'red'
                },
                data: data.value,
                encode: {
                    value: 2
                }

            }
        ]
    };

    // 数据源给予
    mychart.setOption(option)
}

</script>
<style lang='scss' scoped>
</style>