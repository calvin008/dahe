<!--  -->
<template>
  <div @click="onToggle">
  <img v-if="isFullscreen" src="../../../assets/images/notFull.png"
      style="width: 25px; height: 25px; ;" >    
      <img v-else src="../../../assets/images/isFull.png"
      style="width: 25px; height: 25px; ;" >
       
  </div>
</template>

<script setup lang='ts'>
import screenfull from 'screenfull'
import { onMounted, onUnmounted, ref } from 'vue';

const isFullscreen = ref(false)

const change =()=>{
    isFullscreen.value = screenfull.isFullscreen
}
const onToggle = ()=>{
        screenfull.toggle()
}

// 设置监听器
onMounted(()=>{
    screenfull.on('change',change)
})

// 删除监听
onUnmounted(()=>{
    screenfull.off('change',change)
})
</script>
<style lang='scss' scoped>
</style>
