<template>
  <div class="page-container">
    <div>
      <h1>403</h1>
      <h1>当前帐号没有操作角色,请联系管理员</h1>
      <router-link to="/" class="ant-btn ant-btn-primary">回到首页</router-link>
    </div>
    <img src="~@/assets/images/error/403.png" alt="">
  </div>
</template>

<script >
import {defineComponent} from 'vue'

export default defineComponent({
  name: "404",
})
</script>

<style lang="less" scoped>
.page-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
}
</style>