<!--  -->
<template>
      
     <template v-for="menu in menus" :key="menu.path">
      <!-- 二级 -->
      <el-sub-menu v-if="menu.children && menu.children.length >1 "
       v-show="menu.meta.isShow == 0" :index="menu.path">
        <template #title>
          <el-icon>
            <component :is="menu.meta.icon" />
          </el-icon>
          <span>{{$t(menu.meta.title)}}</span>
        </template>
        <menu-item :menus="menu.children"></menu-item>
      </el-sub-menu>
      <!-- 一级 -->
      <el-menu-item v-else @click="toPath(menu.name)" 
      v-show="menu.meta.isShow == 0" :index = "menu.path">
        <el-icon>
          <component :is="menu.meta.icon" />
        </el-icon>
         <span>{{$t(menu.meta.title)}}</span>
      </el-menu-item>
    </template>
</template>

<script setup lang='ts'>
    
import {useRouter} from 'vue-router'
    defineProps(['menus'])
    const router = useRouter()
    const toPath = (item:string) =>{
        router.push({name:item})
    }

 
</script>
<style lang='scss' scoped>

</style>