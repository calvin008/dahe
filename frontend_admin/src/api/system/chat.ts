import axiosInstance from "../axios";

// 添加角色
export function getChatList(openId:string) {
    return axiosInstance({
      url: "/chat/list/" + openId,
      method: "get",
    
    })
  }