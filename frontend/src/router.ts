import Vue from 'vue';
import Router from 'vue-router';
import Layout from '@/views/layout/Layout.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  scrollBehavior: (to, from, savedPosition) => {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  },
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/login',
      component: () => import(/* webpackChunkName: "login" */ '@/views/login/index.vue'),
      meta: { hidden: true },
    },
    {
      path: '/404',
      component: () => import(/* webpackChunkName: "404" */ '@/views/404.vue') ,
      meta: { hidden: true },
    },
    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      name: 'Dashboard',
      meta: { title: '主页', icon: 'zhuye'},
      children: [{
        path: 'dashboard',
        component: () => import(/* webpackChunkName: "Dashboard" */ '@/views/dashboard/index.vue'),
      }],
    },
    {
      path: '/search',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'Search',
          component: () => import(/* webpackChunkName: "form" */ '@/views/search/index.vue'),
          meta: { title: '达人搜索', icon: 'tubiaozhizuomoban-x-' },
        },
      ],
    },
    {
      path: '/example',
      component: Layout,
      redirect: '/example/table',
      name: 'Example',
      meta: { title: '成长榜单', icon: 'chengchang' },
      children: [
        {
          path: 'table',
          name: 'Table',
          component: () => import(/* webpackChunkName: "table" */ '@/views/table/index.vue'),
          meta: { title: 'Table', icon: 'table' },
        },
        {
          path: 'tree',
          name: 'Tree',
          component: () => import(/* webpackChunkName: "tree" */ '@/views/tree/index.vue'),
          meta: { title: 'Tree', icon: 'tree' },
        },
      ],
    },
    
    {
      path: '/nested',
      component: Layout,
      redirect: '/nested/menu1',
      name: 'Nested',
      meta: { title: '热门榜单', icon: 'baojifuben2' },
      children: [
        {
          path: 'menu1',
          component: () => import(/* webpackChunkName: "menu1" */ '@/views/nested/menu1/index.vue'),
          name: 'Menu1',
          meta: { title: 'Menu1' },
          children: [
            {
              path: 'menu1-1',
              component: () => import(/* webpackChunkName: "menu1-1" */ '@/views/nested/menu1/menu1-1/index.vue'),
              name: 'Menu1-1',
              meta: { title: 'Menu1-1' },
            },
            {
              path: 'menu1-2',
              component: () => import(/* webpackChunkName: "menu1-2" */ '@/views/nested/menu1/menu1-2/index.vue'),
              name: 'Menu1-2',
              meta: { title: 'Menu1-2' },
              children: [
                {
                  path: 'menu1-2-1',
                  component: () => import(/* webpackChunkName: "menu1-2-1" */ '@/views/nested/menu1/menu1-2/menu1-2-1/index.vue'),
                  name: 'Menu1-2-1',
                  meta: { title: 'Menu1-2-1' },
                },
                {
                  path: 'menu1-2-2',
                  component: () => import(/* webpackChunkName: "menu1-2-2" */ '@/views/nested/menu1/menu1-2/menu1-2-2/index.vue'),
                  name: 'Menu1-2-2',
                  meta: { title: 'Menu1-2-2' },
                },
              ],
            },
            {
              path: 'menu1-3',
              component: () => import(/* webpackChunkName: "menu1-3" */ '@/views/nested/menu1/menu1-3/index.vue'),
              name: 'Menu1-3',
              meta: { title: 'Menu1-3' },
            },
          ],
        },
        {
          path: 'menu2',
          component: () => import(/* webpackChunkName: "menu2" */ '@/views/nested/menu2/index.vue'),
          name: 'Menu2',
          meta: { title: 'Menu2' },
        },
      ],
    },
    
    {
      path: '/form',
      component: Layout,
      children: [
        {
          path: 'index',
          name: 'Form',
          component: () => import(/* webpackChunkName: "form" */ '@/views/form/index.vue'),
          meta: { title: '充值中心', icon: 'chongzhi' },
        },
      ],
    },

    {
      path: 'external-link',
      component: Layout,
      children: [
        {
          path: 'https://github.com/calvin008/vue3-admin',
          meta: { title: '个人中心', icon: 'wodedangxuan' },
        },
      ],
    },
    {
      path: '*',
      redirect: '/404',
      meta: { hidden: true },
    },
  ],
});
