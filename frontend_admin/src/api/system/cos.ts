import axiosInstance from "../axios";

 // 添加角色
 export function cosUpload(user:object) {
    return axiosInstance({
      url: "/cos/upload",
      method: "post",
      data: user
    })
  }