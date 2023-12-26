import axiosInstance from "../axios";

export function getAllMenus () {
    return axiosInstance({
        url:'/sysMenu/list',
        method:'get'
    })
}

export function getLevelMenus () {
    return axiosInstance({
        url:'/sysMenu/listLevel',
        method:'get'
    })
}

export function addMenu(menuObj:API.menuForm) {
    return axiosInstance({
        url:'/sysMenu/',
        method:'post',
        data:menuObj
    })
}

export function deleteMenu(menuIds:number[]) {
    return axiosInstance({
        url:'/sysMenu/batch',
        method: "delete",
        data: menuIds
    })
}

export function updateMenu(menuObj:API.menuForm) {
    return axiosInstance({
        url:'/sysMenu/updateMenu',
        method:'put',
        data:menuObj
    })
}

// 获取用户菜单
export function selectUserMenu(roleId:number) {
    return axiosInstance({
        url:'/sysMenu/selectUserMenu/' + roleId,
        method:'get'
    })
}