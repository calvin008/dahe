<!--  -->
<template>
  <a-menu
    v-model:open-keys="openKeys"
    v-model:selected-keys="selectedKeys"
    mode="inline"
    theme="dark"
    :inline-collapsed="collapsed"
    @click="clickMenuItem"
  >
    <template v-for="item in menus" :key="item.name">
      <menu-item :menu-info="item" />
    </template>
  </a-menu>
</template>

<script>
import { reactive, computed, watch, toRefs } from "vue";
import {useRoute, useRouter} from "vue-router";
import {useStore} from 'vuex'
import MenuItem from "./menu-item.vue";
export default {
  components: {
      MenuItem
  },
  props: {
    collapsed: {
      // 侧边栏菜单是否收起
      type: Boolean,
    },
  },

  setup(props) {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
  },
};
</script>
<style lang='less' scoped>
</style>