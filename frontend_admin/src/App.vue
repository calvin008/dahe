<script setup lang="ts">
import { reactive } from 'vue';
 import OrderMessage from "./components/orderMessage/OrderMessage.vue"

const state = reactive({
  theDivShow: true
})


</script>

<template>
  <!--悬浮小广告样式的提示信息-->
  <div class="thediv" ref="thediv" v-show="state.theDivShow" >
    <div style="position: absolute;cursor: pointer;font-size: 12px;color: #999999;" @click="state.theDivShow = false">
      关闭
    </div>
    <a href="https://item.taobao.com/item.htm?ft=t&id=684569303364">
      
      <img src="https://vkceyugu.cdn.bspapp.com/VKCEYUGU-c4156a34-94b2-4302-969f-e96f6277625a/07484904-f46e-4f80-aed6-f549cf1047fc.png"
         />
    </a>
  </div>

  <OrderMessage style="position: fixed; z-index: 1000"></OrderMessage>
  <router-view />

</template>

<style lang="scss">
#nprogress .bar {
  background: #ff0000 !important;
}

html,
body,
#app {

  height: 100%;

}

.thediv {
  z-index: 100;
  position: absolute;
  top: 300px;
  right: 2px;
  height: 100px;
  width: 180px;

  img {
    width: 100%;
    height: 100%;
  }

}



</style>
