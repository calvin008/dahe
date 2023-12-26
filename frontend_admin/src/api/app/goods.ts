import axiosInstance from "../axios";
import type { goodsType,goodsPropertyType } from "@/views/goods/goodType";



// 分页查询
export function getGoodsAdmins(pageNo:number, pageSize:number){
  let url = "/goodsAdmin/page?pageNo=";
  url = url + (pageNo ? pageNo : "");
  url = url + "&pageSize=" + (pageSize ? pageSize : "");
  return axiosInstance({
    url: url,
    method: "get"
  })
}


// 根据商品id查询
export function getGoodsAdminById(goodsId:number){
  return axiosInstance({
    url: "/goodsAdmin/" +goodsId,
    method: "get"
  })
}

// 添加
export function addGoodsAdmin(goodsAdmin:goodsType) {
  return axiosInstance({
    url: "/goodsAdmin",
    method: "post",
    data: goodsAdmin
  })
}

// 更新
export function updateGoodsAdmin(goodsAdmin:goodsType) {
  return axiosInstance({
    url: "/goodsAdmin",
    method: "put",
    data: goodsAdmin
  })
}

// 上架或下架商品
export function updateSellStatus(goodsId:number) {
  return axiosInstance({
    url: "/goodsAdmin/updateSellStatus/" + goodsId,
    method: "put"
  })
}

// 批量删除
export function deleteGoodsAdmins(goodsAdminIdList:number[]) {
  return axiosInstance({
    url: "/goodsAdmin/batch",
    method: "delete",
    data: goodsAdminIdList
  })
}


// ************ 商品属性 ************* //
// 设置商品的默认属性
export function setDefaultPropertyOfGoods(goodsPropertyId:number){
  return axiosInstance({
    url: "/goodsPropertyAdmin/setDefault/" + goodsPropertyId,
    method: "put"
  })
}

// 添加商品属性
export function addGoodsProperty(goodsProperty:goodsPropertyType) {
  return axiosInstance({
    url: "/goodsPropertyAdmin",
    method: "post",
    data: goodsProperty
  })
}

// 更新商品属性
export function updateGoodsProperty(goodsProperty:goodsPropertyType) {
  return axiosInstance({
    url: "/goodsPropertyAdmin",
    method: "put",
    data: goodsProperty
  })
}


// 批量商品属性
export function deleteGoodsProperty(goodsPropertyId:number) {
  return axiosInstance({
    url: "/goodsPropertyAdmin/" + goodsPropertyId,
    method: "delete"
  })
}

//更新商品图片
export function updateGoodsAdminImage(formData:FormData) {
  return axiosInstance({
    url: "/goodsAdmin/image",
    method: "post",
    data:formData,
    headers: {
      'Content-Type': 'multipart/form-data',
      'token': localStorage.getItem('token') || '0'
    }
  })
}
