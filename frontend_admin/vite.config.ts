import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import path from "path";
// element plus插件
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import viteCompression from 'vite-plugin-compression';
import OptimizationPersist from 'vite-plugin-optimize-persist'
import PkgConfig from 'vite-plugin-package-config'

// https://vitejs.dev/config/
export default defineConfig({
  // 解决i18n报错
  define: {
    __VUE_I18N_FULL_INSTALL__: true,
    __VUE_I18N_LEGACY_API__: false,
    __INTLIFY_PROD_DEVTOOLS__: false,
},
  server:{
    
    port:3050,
    proxy:{
      '/api': {
        
        target: 'http://127.0.0.1:8101/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  },
  resolve: {
    alias: {
      
      "@": path.resolve(__dirname, "src"),
      "com": path.resolve(__dirname, "src/components")
    }
  },
  css:{
    //css预处理
    preprocessorOptions:{
      scss:{
        //引入varibles.scss全局预定义变量
        additionalData:`@import "./src/styles/variables.scss";`,
      }
    }
  },
  plugins: [
    vue(),
    // giz压缩
    viteCompression(),
     // element plus自动导入
     AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
    //vite启动之初对资源预打包
    PkgConfig(),
    OptimizationPersist()
  ]
})