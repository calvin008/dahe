import axiosInstance from "../axios";

interface UserInfo {
    username:string
}

export function getAllSysUsers(){
    return axiosInstance({
      url: "/sysUser/list",
      method: "get"
    })
  }
  
  // 添加管理员
  export function addSysUser(user:UserInfo) {
    return axiosInstance({
      url: "/sysUser/",
      method: "post",
      data: user
    })
  }
  
  // 更新管理员
  export function updateSysUser(user:UserInfo) {
    return axiosInstance({
      url: "/sysUser/",
      method: "put",
      data: user
    })
  }
  
  // 删除管理员
  export function deleteSysUsers(sysUserIds:number) {
    return axiosInstance({
      url: "/sysUser/batch",
      method: "delete",
      data: sysUserIds
    })
  }
  
  // 更新账号激活状态
  export function updateStatus(sysUserId:number, status:boolean) {
    return axiosInstance({
      url: "/sysUser/status/" + sysUserId + "/" + status,
      method: "put"
    })
  }
  
  // 重置密码
  export function resetPassword(sysUserId:number){
    return axiosInstance({
      url: "/sysUser/resetPassword/" + sysUserId,
      method: "put"
    })
  }
  
  
  // 设置角色
  export function setRole(userId:number, roleId:number){
    return axiosInstance({
      url: "/sysUser/role/" + userId + "/" + roleId,
      method: "put"
    })
  }

  //  部门列表
  export function getDepartment() {
    return axiosInstance({
      url:"/sysUser/department",
      method:"get"
    })
  }

  //根据部门id查询用户
  export function getDepUsers(depId:number) {
    return axiosInstance({
      url:"/sysUser/depUsers/" + depId,
      method:'get'
    })
  }
  