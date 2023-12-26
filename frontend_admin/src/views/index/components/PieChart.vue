<!--  -->
<template>
  <div>
     <div ref="main" style="width: 100%; height: 400px; margin: 15px 0; border:1px solid #333; box-shadow: 5px 5px 5px #888888;"></div>
  </div>
</template>

<script setup lang='ts'>
import * as echarts from 'echarts'
import { onMounted, ref, } from 'vue';
import { pie } from '@/assets/chart';

const main = ref()
const data =ref(pie)
onMounted(()=>{
  init()
})
const init= async ()=>{

  // 1通过dom初始化echarts 2ref虚拟化dom
  let mychart =echarts.init(main.value)
  // 数据源
  var option = {
        title:{
            text:'库存剩余',
            x:'center'
        },
        tooltip:{
            trigger:'item'
        },
        legend:{
            top:'5%',
            left:'center'
        },
        series:[
            {
                name:'库存',
                type:'pie',
                radius:['40%','70%'],
                itemStyle:{
                    borderRadius:10,
                    borderColor:'#fff',
                    borderWidth:2
                },
                label:{
                    show:false,
                    position:'center'
                },
                emphasis:{
                    label:{
                        show:true,
                        fontSize:'40',
                        fontWeight:'bold'
                    }
                },
                data:data.value
            }
        ]
  };

  // 数据源给予
  mychart.setOption(option)
}

</script>
<style lang='scss' scoped>
</style>