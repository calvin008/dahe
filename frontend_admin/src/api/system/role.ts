import axiosInstance from "../axios";

//获取所有角色权限
export function getAllRoles(){
    return axiosInstance({
      url: "/role/list",
      method: "get"
    })
  }

  //获取单条角色
  export function detail (roleId:number){
     return axiosInstance({
      url:"/role/" + roleId,
      method: "get"
     })
  }
  
  // 获取角色所有权限
  export function getPermissionsOfRole(roleId:number){
    return axiosInstance({
      url: "/role/perms/" + roleId,
      method: "get"
    })
  }
  
  // 添加角色
  export function addRole(user:object) {
    return axiosInstance({
      url: "/role/",
      method: "post",
      data: user
    })
  }
  
  // 更新角色
  export function updateRole(role:object) {
    return axiosInstance({
      url: "/role",
      method: "put",
      data: role
    })
  }
  
  // 批量删除角色
  export function deleteRoles(roleIds:number[]) {
    return axiosInstance({
      url: "/role/batch",
      method: "delete",
      data: roleIds
    })
  }
  
  // 修改角色的权限
  export function updateRolePermission(userIdAndPermissions:object){
    return axiosInstance({
      url: "/role/permission",
      method: "put",
      data: userIdAndPermissions
    })
  }
  