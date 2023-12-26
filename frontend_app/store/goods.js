import {defineStore} from 'pinia'

export const goodsStore = defineStore('goods',{
	state:()=>({
		cart:[],
		orderType:'外卖配送'
	}),
	actions:{
		setCart(good) {
			this.cart.push(good)
		},
		setOrderType(type) {
			this.orderType = type
		}
	}
})