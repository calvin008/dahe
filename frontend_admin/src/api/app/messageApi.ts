import axiosInstance from "../axios";

// 消息服务
// 最新订单消息
export function resentOrderMessage(){
  return axiosInstance({
    url: "/message/resentOrderMessage",
    method: "get"
  })
}

// 确认收到消息
export function confirmReceiveOrderMessage(orderNo:string) {
  return axiosInstance({
    url: "/message/confirmReceiveOrderMessage/"+ orderNo,
    method: "delete"
  })
}

//确认收到所有消息
export function receiveAllOrderMessage() {
  return axiosInstance({
    url:"/message/receiveAllOrderMessage",
    method:"delete"
  })
}
