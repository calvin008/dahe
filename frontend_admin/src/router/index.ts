
import {createRouter,createWebHashHistory,RouteRecordRaw} from 'vue-router'
import Layout from '@/layout/index.vue'

import {loginByToken} from '@/api/Auth'
//导入进度lib
import NProgress from 'nprogress'; 
import 'nprogress/nprogress.css'
// 引入pinia
import {authStore} from '@/pinia/authStore'

NProgress.configure({ 
   // 动画方式
   easing: "ease",
   // 递增进度条的速度
   speed: 500,
   // 是否显示加载ico
   showSpinner: false,
   // 自动递增间隔
   trickleSpeed: 200,
   // 初始化时的最小百分比
   minimum: 0.3
 }); //progress配置

// 声明meta类型
declare module 'vue-router' {
  interface RouteMeta {
    title:string,
    icon?:string,
    permission?:string,
    isShow:number,
    index?:number,
    keepalive:number
  }
}

const routes:Array<RouteRecordRaw> = [
  // 首页重定向到数据echart展示
  { path:'/',redirect:'/index'},

  {
    path:'/login',
    component: () => import('@/views/login/Login.vue')
}
]

const router = createRouter({
    history:createWebHashHistory(),
    routes:routes
})

router.beforeEach((to,from,next)=>{
  const useAuthStore = authStore()
  const token = localStorage.getItem('token')
  NProgress.start(); 
  if(!useAuthStore.token && !token) {
      if(to.path.startsWith('/login'))
      
      next()
      
      else {
        next('/login')
      }
  } else if(!useAuthStore.token && token) {
    loginByToken(token).then(res=>{
      if(res.data.status) {
       
      //  store.commit('authStore/addUserInfo',res.data)
      //  store.dispatch('menuStore/generateSystemMenus',res.data.permissions)
      //  store.dispatch('buttonStore/generateButtons',res.data.permissions)
      useAuthStore.userInfo = res.data
      useAuthStore.token = res.data.token
      useAuthStore.changePermisson(res.data.permissions,res.data.roleId)
     
     
        next()
      } else{
        next('/login')
      }

    })
    

    

  } else {
    next()
  }
})

router.afterEach(() => {
  NProgress.done();
});

export default router