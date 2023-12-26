import axiosInstance from "../axios";
import type { categoryType } from "@/views/goods/categoryType";
// 所有商品类别
export function getAllGoodsCategoryAdmins(){
    return axiosInstance({
      url: "/goodsCategoryAdmin/list",
      method: "get"
    })
  }
  
  // 添加
  export function addGoodsCategoryAdmin(goodsCategoryAdmin: categoryType) {
    return axiosInstance({
      url: "/goodsCategoryAdmin",
      method: "post",
      data: goodsCategoryAdmin
    })
  }
  
  // 更新
  export function updateGoodsCategoryAdmin(oldName:string, goodsCategoryAdmin: categoryType) {
    return axiosInstance({
      url: "/goodsCategoryAdmin/" + oldName,
      method: "put",
      data: goodsCategoryAdmin
    })
  }
  
  
  // 显示或隐藏该类商品
  export function updateGoodsCategoryShowStatus(name:string) {
    return axiosInstance({
      url: "/goodsCategoryAdmin/showStatus/" + name,
      method: "put"
    })
  }
  
  // 批量删除
  export function deleteGoodsCategoryAdmins(id:string) {
    return axiosInstance({
      url: "/goodsCategoryAdmin/" + id,
      method: "delete"
    })
  }
  
