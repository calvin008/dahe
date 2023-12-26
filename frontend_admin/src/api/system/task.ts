import axiosInstance from "../axios";

export function doTimingTask(taskMethodName:string){
  return axiosInstance({
    url: "/timingTask/execute/" + taskMethodName,
    method: "get"
  })
}