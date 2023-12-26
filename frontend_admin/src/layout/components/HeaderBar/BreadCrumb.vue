<!--  -->
<template>
    <el-breadcrumb >
        <el-breadcrumb-item>{{$t('menus.wIndex')}}</el-breadcrumb-item>
    <el-breadcrumb-item v-for="(item,index) in breadcrumb" :key="index" >{{$t(item.meta.title)}}</el-breadcrumb-item>
  
  </el-breadcrumb>
</template>

<script setup lang="ts">
import { Ref,ref,onMounted,watch } from 'vue'
import {RouteLocationMatched, useRoute} from 'vue-router'
const route = useRoute()

const breadcrumb:Ref<RouteLocationMatched[]> = ref([])
const getBreadCrumb = ()=> {
    let matched =  route.matched.filter((item)=> item.meta && item.meta.title && item.children.length !==1)
   

    const frist = matched[0]
    if(frist.path ==='/index') {
        matched = []
    }
    breadcrumb.value = matched
}
// 初始化加载面包屑
onMounted(() => {
    getBreadCrumb()
})

// 监控路由变化,面包屑发生变化
watch(()=>route.path,() => {
    getBreadCrumb()
})



</script>
<style lang='scss' scoped>
</style>