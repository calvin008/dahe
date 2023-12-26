import App from './App'
import * as Pinia from 'pinia'
import config from './config.js'

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
// #endif

const request = function(path,method,data,failCallback,completeCallback) {
	// loading
	uni.showLoading({
		mask:true
	})
	
	return new Promise(resolve =>{
		uni.request({
			url:config.baseUrl + path,
			data:data,
			method:method,
			header:{
				'Content-Type':'application/json;charset=UTF-8',
				'token':uni.getStorageSync('token')
			},
			success:res=>{
				// 隐藏loading
				uni.hideLoading()
					if(res.data.code === 200){
						resolve(res.data)
					}
			}
		})
	})
}

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  app.use(Pinia.createPinia());
 // 全局挂载
 app.config.globalProperties.$request = request
 
  return {
    app,
	Pinia

  }
}
// #endif