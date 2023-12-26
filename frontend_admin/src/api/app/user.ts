import axiosInstance from "../axios";

//


// 分页查询
export function getUserAdmins(pageNo:number, pageSize:number){
  let url = "/userAdmin/page?pageNo=";
  url = url + (pageNo ? pageNo : "");
  url = url + "&pageSize=" + (pageSize ? pageSize : "");
  return axiosInstance({
    url: url,
    method: "get"
  })
}

// 添加
export function addUserAdmin(userAdmin:object) {
  return axiosInstance({
    url: "/userAdmin",
    method: "post",
    data: userAdmin
  })
}

// 更新
export function updateUserAdmin(userAdmin:object) {
  return axiosInstance({
    url: "/userAdmin",
    method: "put",
    data: userAdmin
  })
}

// 批量删除
export function deleteUserAdmins(userAdminId:string[]) {
  return axiosInstance({
    url: "/userAdmin/" + userAdminId,
    method: "delete",
    data: userAdminId
  })
}
