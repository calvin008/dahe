<template>
	<view class="coupon-item">
		<view class="coupon-money">
			<view class="demand">{{item.couponTitle}}</view>
			<view class="nick">{{item.couponInfo}}</view>
			<view class="layof" :style="{color:theme}">￥{{item.couponAmount /100}}</view>
			<view class="end_time">{{item.endTime}}前使用</view>
		</view>
		<view class="get-btn" :style="{color:theme,borderColor:color,background:solid}" @tap="useCoupon(item)">
			立即使用
		</view>
	</view>
</template>

<script setup>
	import {
		goodsStore
	} from '@/store/goods.js'

	const useGoodsStore = goodsStore()
	defineProps({
		item: {
			type: Object
		},
		theme: {
			type: String,
			default: '$color-primary'
		},
		solid: {
			type: String,
			default: '$color-primary'
		},
		color: {
			type: String,
			default: '$color-primary'
		},
	})

	const useCoupon = (item)=>{
		let totalPrice = 0;
		useGoodsStore.cart.forEach(goods => {
				totalPrice += goods.realPrice * goods.number
			})
			if(item.couponLow > totalPrice) {
				uni.switchTab({
					url:'/pages/index/index'
				})
				uni.showToast({
					title:'满' + item.couponLow/100  + '可用'
				})
			} else {
				useGoodsStore.setCoupon(item)
				uni.navigateBack()
			}
		
	}

	
</script>

<style lang='scss'>
	.coupon-item {
		width: 100%;
		height: auto;
		display: table;
		border-radius: 10upx;
		padding: 0 20upx;
		margin-top: 22upx;
		border: 1px solid #e1e1e1;
		position: relative;

		.coupon-money {
			width: 465upx;
			height: auto;
			display: table;
			float: left;
			padding: 26upx 0;
			border-style: none dotted none none;
			border-color: #e1e1e1;

			.nick {
				width: 100%;
				height: 50upx;
				line-height: 30upx;
				font-size: $font-size-sm;
				color: $text-color-black;
			}

			.tit {
				width: 100%;
				height: 50upx;
				line-height: 50upx;
				font-size: $font-size-sm;
				color: $text-color-black;
			}

			.demand {
				width: 100%;
				height: 30upx;
				line-height: 30upx;
				margin-bottom: 5upx;
				font-size: $font-size-lg;
				color: $color-primary;
			}

			.layof {
				width: 100%;
				height: 48upx;
				line-height: 30upx;
				font-size: 44upx;
				color: $color-primary;
				font-weight: bold;
			}

			.end_time {
				width: 100%;
				height: 30upx;
				line-height: 30upx;
				font-size: $font-size-sm;
				color: $text-color-black;
			}
		}

		.get-btn {
			width: 146upx;
			height: 52upx;
			line-height: 50upx;
			position: absolute;
			top: 50%;
			right: 26upx;
			margin-top: -26upx;
			text-align: center;
			border-radius: 60upx;
			border: 1px solid $color-primary;
			font-size: $font-size-sm;
			float: right;
		}
	}

	.coupon-item:after {
		width: 40upx;
		height: 20upx;
		position: absolute;
		left: 460upx;
		top: -1px;
		border-radius: 0 0 40upx 40upx;
		content: "";
		display: block;
		background: $bg-color-grey;
		border: 1px solid #eeeeee;
		border-top: 0px;
	}

	.coupon-item:before {
		width: 40upx;
		height: 20upx;
		position: absolute;
		left: 460upx;
		bottom: -1px;
		border-radius: 40upx 40upx 0 0;
		content: "";
		display: block;
		background: $bg-color-grey;
		border: 1px solid #eeeeee;
		border-bottom: 0px;
	}
</style>
