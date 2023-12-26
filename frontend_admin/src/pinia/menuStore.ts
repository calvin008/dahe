import { defineStore } from 'pinia'

import { type RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'
import router from '@/router';
import { selectUserMenu } from '@/api/system/menu'
import { ElLink } from 'element-plus';

const modules = import.meta.glob('../views/**/*.vue')

export interface MenuState {
    menuList: RouteRecordRaw[]
}
// 确认权限
function hasPermission(perms: string[], needPermission: string) {
    for (let i = 0; i < perms.length; i++) {
        if (perms[i].startsWith(needPermission)) {
            return true
        }
    }
    return false
}

// 过滤路由
// function filterRouter(routes: RouteRecordRaw[], perms: string[]) {
//     const res: RouteRecordRaw[] = []
//     routes.forEach(route => {
//         if (route.children) {
//             route.children = filterRouter(route.children, perms)
//             if (route.children.length > 0) {
//                 res.push(route)
//             }
//         } else {
//             if (route.meta!.permission) {
//                 if (hasPermission(perms, route.meta!.permission)) {
//                     res.push(route)
//                 }
//             } else {
//                 res.push(route)
//             }
//         }


//     })
//         return res
// }



export const initMenu = (menu: any) => {

    menu.forEach((el: any) => {

        if (el.component === 'Layout') {

            el.component = Layout


        } else {



            el.component = modules[`../views/${el.component}.vue`]

        }

        if (el.children != null && el.children.length) {

            initMenu(el.children)

        }

    })

    return menu

}





export const menuStore = defineStore('menu', {
    state: (): MenuState => ({
        menuList: []
    }),

    getters: {
        getMenus: state => state.menuList
    },

    actions: {
        async generateSystemMenus(roleId: number) {
            let routers: RouteRecordRaw[] = []


            await selectUserMenu(roleId).then(res => {
                console.log(res.data)
                routers = initMenu(res.data)
                routers.forEach(route => {
                    // 二级menu跳成一级menu
                    if (route.children?.length == 1) {
                        route.redirect =   route.children[0].path
                        route.meta = route.children[0].meta
                    }
                    router.addRoute(route)
                })

                // 添加动态菜单
                this.menuList = routers
                router.replace(router.currentRoute.value.fullPath)
                console.log(this.menuList);

            })


        }
    }
})