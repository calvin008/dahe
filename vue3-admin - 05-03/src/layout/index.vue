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
      <!-- logo -->
      <app-logo :collapsed="collapsed" />
      <!-- menu -->
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


import AppHeader from "./header/index.vue";
import AppLogo from "./logo/logo.vue";
import AppMenu from "./menu/menu.vue";
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
 
} from '@ant-design/icons-vue';
import { reactive, toRefs } from "vue";
export default {
  components: {
    
    AppHeader,
    AppLogo ,
    AppMenu,
     MenuFoldOutlined,
  MenuUnfoldOutlined,
  },

  setup() {
    const state = reactive({
      collapsed: false,
      selectedKeys: ["1"],
    });

    return {
      ...toRefs(state),
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
      flex-wrap: nowrap;
      overflow:hidden;
      justify-content: space-between;
      padding: 0 16px;
    }
  }
}
</style>