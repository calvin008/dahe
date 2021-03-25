import {createRouter, createWebHashHistory} from 'vue-router'
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    name: 'Index',
    meta: {
      title: '首页',
      affix: true,
    },
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/index/index.vue'),
        meta: {
          title: '首页',
          affix: true,
        },
      },
    ],
  },
  {
    path: '/demo',
    component: Layout,
    name: 'Demo',
    redirect: '/demo/table',
    meta: {
      title: '组件',
      icon: 'apps-line',
      roles: ['admin', 'editor']
    },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/demo/table/index.vue'),
        meta: {
          title: '表格',
          icon: 'table-2',
        },
      },
      {
        path: 'icon',
        name: 'Icon',
        component: () => import('@/views/demo/icon/index.vue'),
        meta: {
          title: '图标',
          icon: 'remixicon-line',
        },
      },
    ],
  },

  

  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/Login.vue'),
    hidden: true,
  },
  // {
  //   path: '/403',
  //   name: '403',
  //   component: () => import('@/views/error/403.vue'),
  //   hidden: true,
  // },
  // {
  //   path: '/404',
  //   name: '404',
  //   component: () => import('@/views/error/404.vue'),
  //   hidden: true,
  // },
]

export const asyncRoutes = [
  
 
  {
    path: '/error',
    name: 'Error',
    component: Layout,
    redirect: '/error/403',
    meta: {
      title: '错误页',
      icon: 'error-warning-line',
    },
    children: [
      {
        path: '403',
        name: 'Error403',
        component: () => import('@/views/error/403.vue'),
        meta: {
          title: '403',
          icon: 'error-warning-line',
        },
      },
      {
        path: '404',
        name: 'Error404',
        component: () => import('@/views/error/404.vue'),
        meta: {
          title: '404',
          icon: 'error-warning-line',
        },
      },
    ],
  },
  {
    path: '/echart',
    component: Layout,
    name:'echart',
    redirect: '/echart/bar',
    meta: {
      title: '动态路由测试',
      icon: 'test-tube-line',
      roles: ['admin','editor']
    },
    children: [
      {
        path: 'bar',
        name: 'Bar',
        component: () => import('@/views/echart/bar/index.vue'),
        meta: {
          title: '路由测试1',
          icon: 'test-tube-line',
          roles: ['admin','editor']
        },
      },
      {
        path: 'line',
        name: 'Line',
        component: () => import('@/views/echart/line/index.vue'),
        meta: {
          title: '路由测试2',
          icon: 'test-tube-line',
          roles: ['admin','editor']
        },
      },
      {
        path: 'pie',
        name: 'Pie',
        component: () => import('@/views/echart/pie/index.vue'),
        meta: {
          title: '路由测试3',
          icon: 'test-tube-line',
        },
      },
    ],
  },
  {
    path:'/system',
    name:'system',
    component: Layout,
    redirect: '/system/account/index.vue',
    meta:{
      title:'system',
      icon:'system',
      roles: ['admin','editor']
    },
    children:[
      {
        path:'account',
        name:'account',
        component: ()=> import('@/views/system/account/index.vue'),
        meta:{
          title:'account',
          icon:'account',
          roles: ['editor']
        }

      },
      {
        path:'group',
        name:'group',
        component: ()=> import('@/views/system/group/index.vue'),
        meta:{
          title:'group',
          icon:'group',
          roles: ['admin']
        }
      }
    ]

  }
]


const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
})



export default router