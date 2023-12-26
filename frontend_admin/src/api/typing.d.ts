

declare namespace API {
    // 用户登录
    type loginForm = {
        username: string,
        password: string,
        uuid: string,
        verifyCode: string
    }
    //  菜单
    type menuForm = {
        menuId:undefined|number,
    type: number,
    menuName: string,
    router: string,
    viewPath: string,
    icon: string,
    keepalive:number,
    isShow:number,
    permission:string,
    title?:string,
    parentId:undefined|number,
    children ?: menuForm[]
    }
}