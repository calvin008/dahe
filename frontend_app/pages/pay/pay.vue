<template>
	<view class="container">
		<!-- 订单类型 -->
		<view class="mb-20">
			<list-cell v-if="useGoodsStore.orderType == '外卖配送'">
				<view>预计送达时间：15分钟 </view>
			</list-cell>
			<list-cell v-else>
				<view>门店自取</view>
			</list-cell>
		</view>
		<!-- 购物车列表 -->
		<view class="mb-20">
			<!-- 门店信息 -->
			<list-cell>
				<view>门店：</view>
				<view>{{useGoodsStore.storeInfo.storeName}}</view>
			</list-cell>
			<!-- 购物车列表 -->
			<list-cell v-for="(goods,index) in cart" :key="index">
				<view class="w-100 d-flex flex-column">
					<view class="d-flex align-items-center mb-10">
						<view class="w-60 overflow-hidden">
							<view class="font-size-lg">{{goods.name}}</view>
						</view>
						<view class="d-flex flex-fill justify-content-between align-items-center">
							<view class="text-color-assist font-size-lg">x{{goods.number}}</view>
							<view class="text-color-assist font-size-lg">￥{{goods.realPrice /100}}</view>
						</view>
					</view>
					<view class="text-truncate font-size-base text-color-assist">{{goods.propertyStr}}</view>
				</view>
			</list-cell>
			<!-- 优惠价格 -->
			<list-cell arrow>
				<view class="flex-fill d-flex justify-content-between align-items-center" @click="useCoupon">
					<view class="text-color-base">优惠券</view>
					<view class="text-color-primary" style="color: red;">
						{{choseCoupon.couponStatus == 0 ? '优惠'+ choseCoupon.couponAmount /100 + '元': '查看优惠卷'}}
					</view>
				</view>
			</list-cell>
			<!-- 计算总价 -->
			<list-cell>
				<view class="flex-fill d-flex justify-content-end align-items-center">
					<view>总计￥{{totalPrice / 100}}</view>
				</view>
			</list-cell>
		</view>
	</view>


</template>

<script setup>
	import listCell from '@/components/list-cell/list-cell'
	import {
		ref,
		getCurrentInstance,
		computed
	} from "vue";
	import {
		userStore
	} from '@/store/user.js'
	import {
		goodsStore
	} from '@/store/goods.js'
	import {
		storeToRefs
	} from 'pinia'

	const useUserStore = userStore()
	const useGoodsStore = goodsStore()
	const {
		cart,
		choseCoupon
	} = storeToRefs(useGoodsStore)
	const {
		appProperties
	} = storeToRefs(useUserStore)
	const {
		proxy
	} = getCurrentInstance()

	// 计算总价格
	const totalPrice = computed(() => {
		let totalPrice = 0;
		useGoodsStore.cart.forEach(goods => {
			totalPrice += goods.realPrice * goods.number
		})
		if(useGoodsStore.choseCoupon.couponAmount) {
			totalPrice -= useGoodsStore.choseCoupon.couponAmount
		}
		if (useGoodsStore.orderType == '外卖配送') {
			totalPrice += useUserStore.appProperties.packingPrice + useUserStore.appProperties.sendingPrice
		}

		return totalPrice
	})
	
	// 调转优惠卷页面
		const useCoupon = () => {
			uni.navigateTo({
				url: '/pages/coupon/coupon'
			})
		}
	// 推荐最佳的优惠卷
		const getCouponOne = async ()=>{
			let totalPrice = 0;
		await	useGoodsStore.cart.forEach(goods => {
				totalPrice += goods.realPrice * goods.number
			})
			
		const path = '/api-app/coupon/list/' + useUserStore.userInfo.wxOpenid
		await proxy.$request(path,'get').then(res=>{
			if(res.code == 200) {
				let couponList = res.data.filter(item => item.couponLow <= totalPrice)
				if(couponList.length ===0) {
					useGoodsStore.choseCoupon = {}
				} else {
					couponList.sort(function(a,b){
						return b.couponAmount - a.couponAmount 
					})
					
					useGoodsStore.setCoupon(couponList[0])
				}
				
			}
		})
		}
		
		getCouponOne()
</script>

<style lang="scss" scoped>
	.container {
		padding: 30rpx;
	}

	.arrow {
		width: 50rpx;
		height: 50rpx;
		position: relative;
		margin-right: -10rpx;
	}

	.location {
		.store-name {
			font-size: $font-size-lg;
		}

		.iconfont {
			font-size: 50rpx;
			line-height: 100%;
			color: $color-primary;
		}
	}




	.payment {
		margin-bottom: 30rpx;

		.disabled {
			color: $text-color-grey;
		}

		.payment-icon {
			font-size: 44rpx;
			margin-right: 10rpx;
		}

		.checkbox {
			font-size: 36rpx;
			margin-left: 10rpx;
		}

		.checked {
			color: $color-primary;
		}
	}

	.pay-box {
		box-shadow: 0 0 20rpx rgba(0, 0, 0, 0.1);
		height: 100rpx;
	}

	.modal-content {
		.change-address-btn {
			line-height: 2;
			padding: 0 1em;
		}

		.pay_btn {
			width: 100%;
			border-radius: 50rem !important;
			line-height: 3;
		}
	}
</style>
