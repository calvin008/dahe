<!--  -->
<template>
  <div>
    <el-row :gutter="20"
    justify ="start" 
    align ="middle"
    style="width:100%;"
    >
      <el-col :xs="0" :sm="12" :md="12" :lg="18" :xl="18" > 
        <!-- 面包屑 -->
        <bread-crumb />
      </el-col>
      <el-col :xs="0" :sm="3" :md="3" :lg="1" :xl="1">
      <!-- 全屏 -->
        <screen-full></screen-full>
      </el-col>
      <el-col :xs="8" :sm="3" :md="3" :lg="1" :xl="1"> 
      <!-- 中英文切换 -->
        <language-bar />
      </el-col>
      <el-col :xs="8" :sm="3" :md="3" :lg="1" :xl="1">
           <!-- 通知 -->
       <Notice  />
      </el-col>
      
      <el-col :xs="8" :sm="3" :md="3" :lg="3" :xl="3">
        <!-- 用户头像 -->
        <user-bar />
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import Notice from "../notice/index.vue";
import BreadCrumb from './BreadCrumb.vue'
import UserBar from './UserBar.vue'
import LanguageBar from './LanguageBar.vue'
import ScreenFull from './ScreenFull.vue';

</script>
<style lang='scss' scoped>
</style>