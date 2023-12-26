import axiosInstance from "../axios";
import { configType } from "@/views/system/setting";

// 获取小程序的所有配置信息
export function getAppConfig(){
  return axiosInstance({
    url: "/config" ,
    method: "get"
  })
}


// 修改配置信息
export function updateAppConfig(appConfig:configType) {
  return axiosInstance({
    url: "/config",
    method: "put",
    data: appConfig
  })
}
