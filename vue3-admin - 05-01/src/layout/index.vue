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
      <a-menu theme="dark" mode="inline" v-model:selectedKeys="selectedKeys">
        <a-menu-item key="1">
          <icon-font type="icon-voice-fill" />
          <span>nav 1</span>
        </a-menu-item>
        <a-menu-item key="2">
          <video-camera-outlined />
          <span>nav 2</span>
        </a-menu-item>
        <a-menu-item key="3">
          <upload-outlined />
          <span>nav 3</span>
        </a-menu-item>
      </a-menu>
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
  VideoCameraOutlined,
  UploadOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from "@ant-design/icons-vue";
import { createFromIconfontCN } from "@ant-design/icons-vue";
import AppHeader from "./header/index.vue";
import AppLogo from "./logo/index.vue"
const IconFont = createFromIconfontCN({
  scriptUrl: "//at.alicdn.com/t/font_1617479_lw8fprdjcs.js",
});
import { reactive, toRefs } from "vue";
export default {
  components: {
    IconFont,
    AppHeader,
    AppLogo,
    VideoCameraOutlined,
    UploadOutlined,
    MenuUnfoldOutlined,
    MenuFoldOutlined,
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
      justify-content: space-between;
      padding: 0 16px;
    }
  }
}
</style>