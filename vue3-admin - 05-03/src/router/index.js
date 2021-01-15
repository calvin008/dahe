import {createRouter, createWebHashHistory} from 'vue-router'
import Layout from '@/layout'

export const constantRoutes = [

  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/Login.vue'),
    hidden: true,
  },
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/error/403.vue'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/error/404.vue'),
    hidden: true,
  },
]

export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    meta: {
      title: '首页',
      affix: true,
    },
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/index'),
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
        component: () => import('@/views/demo/table'),
        meta: {
          title: '表格',
          icon: 'table-2',
        },
      },
      {
        path: 'icon',
        name: 'Icon',
        component: () => import('@/views/demo/icon'),
        meta: {
          title: '图标',
          icon: 'remixicon-line',
        },
      },
    ],
  },
  {
    path: '/test',
    component: Layout,
    redirect: '/test/test',
    meta: {
      title: '动态路由测试',
      icon: 'test-tube-line',
    },
    children: [
      {
        path: 'test1',
        name: 'Test1',
        component: () => import('@/views/test1'),
        meta: {
          title: '路由测试1',
          icon: 'test-tube-line',
        },
      },
      {
        path: 'test2',
        name: 'Test2',
        component: () => import('@/views/test2'),
        meta: {
          title: '路由测试2',
          icon: 'test-tube-line',
        },
      },
      {
        path: 'test3',
        name: 'Test3',
        component: () => import('@/views/test3'),
        meta: {
          title: '路由测试3',
          icon: 'test-tube-line',
        },
      },
    ],
  },
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
        component: () => import('@/views/error/403'),
        meta: {
          title: '403',
          icon: 'error-warning-line',
        },
      },
      {
        path: '404',
        name: 'Error404',
        component: () => import('@/views/error/404'),
        meta: {
          title: '404',
          icon: 'error-warning-line',
        },
      },
    ],
  },

  {
    path:'/system',
    name:'system',
    component: Layout,
    redirect: '/system/account',
    meta:{
      title:'system',
      icon:'system',
      roles: ['admin']
    },
    children:[
      {
        path:'account',
        name:'account',
        component: ()=> import('@/views/system/account'),
        meta:{
          title:'account',
          icon:'account'
        }

      },
      {
        path:'group',
        name:'group',
        component: ()=> import('@/views/system/group'),
        meta:{
          title:'group',
          icon:'group'
        }
      }
    ]

  }
]


const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
})

router.beforeEach((to, from, next) => {
  if(to.path === '/login') {
    next()
  } else {
    let token = localStorage.getItem('token')
    if(!token) {
      next('/login')
    } else {
      next()
    }
  }
  
})

export default router