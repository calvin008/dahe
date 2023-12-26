import axiosInstance from "../axios";
import type { storeForm ,storeFullType} from "@/views/storesLocation/storeType";


// 查询
export function getStoreList() {
  return axiosInstance({
    url:"/storeInfoAdmin/query",
    method:'get'
  })
}

// 分页查询
export function getStoreAdmins(pageNo:number, pageSize:number){
    let url = "/storeInfoAdmin/page?pageNo=";
    url = url + (pageNo ? pageNo : "");
    url = url + "&pageSize=" + (pageSize ? pageSize : "");
    return axiosInstance({
      url: url,
      method: "get"
    })
  }

// 添加
export function addStoreAdmin(storeAdmin:storeForm) {
    return axiosInstance({
      url: "/storeInfoAdmin",
      method: "post",
      data: storeAdmin
    })
  }

// 修改
export function updateStoreInfo(storeId:number,obj:storeFullType) {
  return axiosInstance({
    url:"/storeInfoAdmin/update/" + storeId,
    method: "put",
    data:obj
  })
}

// 删除
export function deleteStoreInfo(storeId:number) {
  return axiosInstance({
    url:"/storeInfoAdmin/delete/" + storeId,
    method:'DELETE'
  })
}