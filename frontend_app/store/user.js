import {defineStore} from 'pinia'


export const userStore = defineStore('user',{
	state:()=>({
		appProperties:{
			"shopNotice":''
		},
		userInfo:null
	}),
	
	actions:{
		login(userInfo) {
			this.userInfo = userInfo
			uni.setStorageSync('token',userInfo.token)
			uni.setStorageSync("userInfo",userInfo)
		},
		logout() {
			uni.removeStorageSync('token')
			uni.removeStorageSync('userInfo')
			this.userInfo = null
		},
		setToken(token) {
			uni.setStorageSync('token',token)
		},
		setUserInfo(userInfo) {
			this.userInfo = userInfo
			uni.setStorageSync("userInfo",userInfo)
		}
	}
})