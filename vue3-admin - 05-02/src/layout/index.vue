<!--  -->
<template>
  <a-layout class="app-wapper">
    <!-- 左sider -->
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      class="app-sider"
    >
      <!-- logo部分 -->
      <app-logo :collapsed="collapsed" />
      <!-- MenuList -->
      <app-menu :collapsed="collapsed" />
    </a-layout-sider>
    <!-- 右main -->
    <a-layout>
      <!-- header -->
      <a-layout-header class="app-header">
        <a-row>
          <a-col>
            <menu-unfold-outlined
              v-if="collapsed"
              class="trigger"
              @click="() => (collapsed = !collapsed)"
            />
            <menu-fold-outlined
              v-else
              class="trigger"
              @click="() => (collapsed = !collapsed)"
            />
          </a-col>
          <a-col>
            <!-- header右侧头像信息 -->
            <app-header />
          </a-col>
        </a-row>
        
      </a-layout-header>
      <!-- content -->
      <a-layout-content> </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import {
 
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from "@ant-design/icons-vue";

import AppHeader from "./header/index.vue";
import AppLogo from "./logo/index.vue"
import AppMenu from "./menu/menu.vue"
import { ref } from "vue";
export default {
  components: {
    AppHeader,
    AppLogo,
    AppMenu,
    MenuUnfoldOutlined,
    MenuFoldOutlined,
  },

  setup() {
    const collapsed = ref(false) 

    return {
      collapsed
    };
  },
};
</script>
<style lang="less">
.app-wapper {
  .app-sider {
    left: 0;
    height: 100vh;
    overflow: auto;
  }
  .app-header {
    padding: 0;
    background: #fff;
    .trigger {
      font-size: 18px;
      line-height: 64px;
      padding: 0 24px;
      cursor: pointer;
      transition: color 0.3s;
    }
    .ant-row {
      display: flex;
      justify-content: space-between;
      padding: 0 16px;
    }
  }
}
</style>