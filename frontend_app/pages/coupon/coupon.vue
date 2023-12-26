<template>
	<view class="coupon_type">
		<view class="text"><span>优惠券</span></view>
	</view>
	
	<coupon v-for="(item, index) in couponList" :key="index" v-bind:item="item" theme="#ff0000"></coupon>
</template>

<script setup>
	import {ref,getCurrentInstance} from 'vue'
	import {userStore} from '@/store/user.js'
	import coupon from '@/components/user-coupon/user-coupon';
	const couponList = ref([])
	const useUserStore = userStore()
	const {proxy} = getCurrentInstance()
	
	
	const getCouponList = ()=>{
		const path = '/api-app/coupon/list/' + useUserStore.userInfo.wxOpenid
		proxy.$request(path,'get').then(res=>{
			console.log(res)
			couponList.value = res.data
		})
	}
	
	getCouponList()
	
</script>


<style lang='scss'>
	.coupon_box {
		width: 100%;
		height: auto;
		display: table;
		padding: 6upx 26upx 26upx 26upx;
	}

	.coupon_type {
		width: 100%;
		height: 90upx;
		padding-top: 50upx;

		.text {
			width: 100%;
			border-top: 1px solid #eeeeee;
			display: block;
			text-align: center;
			position: relative;
		}

		.text span {
			width: 180upx;
			height: 40upx;
			line-height: 40upx;
			color: #999999;
			display: block;
			background: #ffffff;
			position: absolute;
			left: 50%;
			top: 50%;
			margin-left: -90upx;
			margin-top: -20upx;
			font-size: $font-size-base;
		}
	}
</style>
