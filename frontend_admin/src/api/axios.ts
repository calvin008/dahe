import axios, { InternalAxiosRequestConfig, AxiosResponse} from 'axios'
import { ElMessage} from 'element-plus'
import 'element-plus/es/components/message/style/css'
const axiosInstance = axios.create({
    baseURL:import.meta.env.VITE_URL,

})





// request拦截器
axiosInstance.interceptors.request.use((requestInfo:InternalAxiosRequestConfig)=>{
    if(requestInfo.headers) {
       
        requestInfo.headers['token'] = localStorage.getItem('token') || '0'
        // 如果是上传文件的请求，设置正确的 Content-Type 为 multipart/form-data
  if (requestInfo.url === '/goodsAdmin/image') {
    requestInfo.headers['Content-Type'] = 'multipart/form-data'
  }
  // 如果不是上传文件的请求，设置默认的 Content-Type 为 application/json
  else {
    requestInfo.headers['Content-Type'] = 'application/json;charset=UTF-8'
  }
        return requestInfo
    }
    
},
error=>{
     return Promise.reject(error)   
})


// response 拦截器
axiosInstance.interceptors.response.use((response:AxiosResponse)=>{
    const res = response.data
  
    if(res.code === 200) {
        
        return res
    } else if(res.code === 10001) {
        ElMessage({
            message: '未登录或者登录过期,请登录',
            type: 'warning',
          })
        
        localStorage.removeItem('token')
        location. reload()
    }else if(res.code === 10002) {
        ElMessage({
            message: '权限不足',
            type: 'warning',
          })
          localStorage.removeItem('token')
        location. reload()
    } else {
        ElMessage({
            message: res.message,
            type: 'warning',
          })
        
    }
}, error => {
  if (error.response && error.response.data) {
    const status = error.response.status
    const message = error.response.data.message || '出错了'
    switch (status) {
      case 401: // 未登录状态，需要跳转到登录页
        console.log('未登录')
        break
      case 403: // 授权失败，需要跳转到首页
        console.log('授权失败')
        break
      case 500: // 服务端错误
        console.log('服务器错误')
        ElMessage({
          message: '未登录或者登录过期,请登录',
          type: 'warning',
        })
      
      localStorage.removeItem('token')
      location. reload()
        break
      default:
        console.log(message)

    }
  }
})


export default axiosInstance
