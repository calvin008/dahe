<!--  -->
<template>
  <a-menu theme="dark" mode="inline" v-model:selectedKeys="selectedKeys"
  :inline-collapsed="collapsed">
    <a-menu-item key="1">
      <icon-font style="font-size:32px" type="icon-voice-fill" />
      <span>nav 1</span>
    </a-menu-item>
    <a-menu-item key="2">
       <icon-font style="font-size:32px" type="icon-voice-fill" />
      <span>nav 2</span>
    </a-menu-item>
    <a-menu-item key="3">
       <icon-font style="font-size:32px" type="icon-voice-fill" />
      <span>nav 3</span>
    </a-menu-item>
  </a-menu>
</template>

<script>
import IconFont from '@/assets/iconfont/icon'
export default {
    components:{
        IconFont
    },

    props: {
    collapsed: {
      type: Boolean
    }
  }

};
</script>
<style lang='less' scoped>

    .icon {
        font-size:24px
    }
</style>