<!--  -->
<template>
  <a-menu
    theme="dark"
    mode="inline"
    v-model:selectedKeys="selectedKeys"
    :inline-collapsed="collapsed"
  >
    <template v-for="items in menus" :key="items.name">
      <!-- 存在children子菜单 -->
      <a-sub-menu
        v-if="items.children && items.children.length > 1"
        :key="items.name"
      >
        <template v-slot:title>
          <span>
            <icon-font style="font-size: 24px" :type="items.meta.icon" />
            <span>{{ items.meta.title }}</span>
          </span>
        </template>
        <!-- 子菜单 -->
        <a-menu-item
          v-for="item in items.children"
          :key="item.name"
          @click="clickMenuItem(item.name)"
        >
          <icon-font style="font-size: 24px" :type="item.meta.icon" />
          <span>{{ item.meta.title }}</span>
        </a-menu-item>
      </a-sub-menu>

      <!-- 单独菜单 -->
      <a-menu-item v-else :key="items.name"
       @click="clickMenuItem(items.name)">
        <icon-font style="font-size: 24px" :type="items.meta.icon" />
        <span>{{ items.meta.title }}</span>
      </a-menu-item>
    </template>
  </a-menu>
</template>

<script>
import{ref} from 'vue'
import IconFont from "@/assets/iconfont/icon";
import {computed} from "vue"
import {useStore} from "vuex"
import {useRouter} from "vue-router";
export default {
  components: {
    IconFont,
  },

  props: {
    collapsed: {
      type: Boolean,
    },
  },

  setup(){
    const store = useStore()
    const router = useRouter()
    const menus = computed(()=> store.getters.menus)
     const selectedKeys = ref(['1']);
    const clickMenuItem = (key) =>{
      if(router.hasRoute(key)){
        router.push({name:key})
      } else {
        router.push({name:'Error403'})
      }
      
    }

    return {
      menus,
      selectedKeys,
      clickMenuItem
    }
  }
};
</script>
<style lang='less' scoped>
.icon {
  font-size: 24px;
}
</style>