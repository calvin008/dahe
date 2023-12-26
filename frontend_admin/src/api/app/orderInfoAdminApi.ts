import axiosInstance from "../axios";

export type OrderType = {
  orderNo: null | string,
  orderStatus: null | string,
  storeId: null | number,
  createTime:null | Date,
  finishTime:null | Date
}

// 精准查询
export function getOrderInfoList(pageNo: number, pageSize: number,orderObj:OrderType) {
    let url = "/orderInfoAdmin/query?pageNo=";
  url = url + (pageNo ? pageNo : "");
  url = url + "&pageSize=" + (pageSize ? pageSize : "");
  return axiosInstance({
    url:url,
    method:'post',
    data:orderObj
  })
}

// 分页查询
export function getOrderInfoAdmins(pageNo: number, pageSize: number, orderStatus: string, storeId: number) {
  let url = "/orderInfoAdmin/page?pageNo=";
  url = url + (pageNo ? pageNo : "");
  url = url + "&pageSize=" + (pageSize ? pageSize : "");
  if (orderStatus)
    url += "&orderStatus=" + orderStatus
  if (storeId)
    url += "&storeId=" + storeId
  return axiosInstance({
    url: url,
    method: "get"
  })
}

// 进入订单的下一步
export function toNextOrderStatus(orderNo: string, currentStatus: string) {
  return axiosInstance({
    url: "/orderInfoAdmin/nextStatus/" + orderNo + "/" + currentStatus,
    method: "post"
  })
}

// 查询订单状态
export function queryWeixinOrder(orderNo: string) {
  return axiosInstance({
    url: "/orderInfoAdmin/wxPayStatus/" + orderNo
  })
}

// 取消没有付款的订单
export function cancelOrderNotPay(orderNo: string) {
  return axiosInstance({
    url: "/orderInfoAdmin/cancelOrderNotPay/" + orderNo,
    method: "put"
  })
}

// 取消订单并退款
export function cancelAndRefund(orderNo: string, reason: string) {
  return axiosInstance({
    url: "/orderInfoAdmin/cancelAndRefund/" + orderNo + "?reason=" + reason,
    method: 'put'
  })
}
