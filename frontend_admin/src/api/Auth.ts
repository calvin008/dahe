import axiosInstance from "./axios";

// 获取验证码

export function getCode() {
    return axiosInstance({
      url: '/auth/code',
      method: 'get'
    })
  }


  // 账号密码登录
  export function login(requestUser:object) {
    return axiosInstance({
      url: '/auth/login',
      method: 'post',
      data: requestUser
    })
  }
  
  // 通过token直接登录
  export function loginByToken(token:string) {
    return axiosInstance({
      url: '/auth/loginByToken?token=' + token,
      method: 'post'
    })
  }