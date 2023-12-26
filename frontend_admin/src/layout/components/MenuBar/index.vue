<template>
  <el-menu
   
    active-text-color="#409EFF"
    text-color="#fff"
    class="el-menu"
    :collapse="collapsed"
     :default-active="activeMenu"
  >
    
    <menu-item :menus="menus"></menu-item>
  </el-menu>
</template>

<script lang="ts" setup>

import MenuItem from './MenuItem.vue'
import {computed} from 'vue'
import {menuStore} from '@/pinia/menuStore'
import {useRoute} from 'vue-router'

const route = useRoute()

defineProps({
  collapsed: {
    type: Boolean
  }
})


// const menus = store.getters['menuStore/getMenus']
const useMenuStore = menuStore()
const menus = useMenuStore.getMenus
console.log(useMenuStore.getMenus)
const activeMenu = computed(() => {
  const { meta, path } = route;
  
  if (meta.activeMenu) {
    return meta.activeMenu;
  }
  return path;
})



</script>

<style lang="scss">
.el-menu {
  background-color: $menuBg;
  border: none;
  // 菜单触碰高亮背景色
   .el-menu-item:hover {
      background: #112e42 !important;
     
    }
     // 多级菜单触碰高亮背景色
   .el-sub-menu__title:hover{
   background-color: rgb(3, 19, 33) !important; 

}
  .el-sub-menu {
     // 子菜单触碰高亮背景色
    .el-menu-item:hover {
      background: #112e42 !important;
      color: #fff !important;
    }
    .el-menu-item {
      background-color: $subMenuBg;
    }
  }
}
</style>
