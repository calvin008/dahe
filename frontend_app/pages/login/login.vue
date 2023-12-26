<template>
	<view class="container">
		<view class="intro">
			<image src="/static/images/logo.png"></image>
			<view class="tips">王大合茶餐厅欢迎您!</view>
		</view>
		<view class="bottom">
			<button type="primary" size="default" class="login-btn"  @tap="handlerLocalToken"
				:disabled="logining">
				<image src="/static/images/mine/wechat.png"></image>
				微信授权登录
			</button>


		</view>
	</view>
</template>

<script setup>
	import {
		getCurrentInstance,
		ref,
		computed,
		onMounted
	} from 'vue'
	
	import {userStore} from '@/store/user.js'
	const useUserStore = userStore()
	const {
		proxy
	} = getCurrentInstance()
	const logining = ref(false)
	
	onMounted(()=>{
		handlerLocalToken()
	})
	
	const handlerLocalToken = ()=>{
		let token = uni.getStorageSync('token')
		if(token) {
			proxy.$request('/api-app/user/info','get').then(result=>{
				console.log(result)
				uni.navigateBack({})
			})
		}
		
	}
		
	const weiXinLogin = ()=>{
		
		
		
		
	}
		
		
</script>

<style lang="scss" scoped>
	.intro {
		width: 100%;
		height: 50vh;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-evenly;
		font-size: $font-size-base;
		color: $text-color-assist;

		image {
			width: 200rpx;
			height: 200rpx;
		}

		.tips {
			line-height: 72rpx;
			text-align: center;
		}
	}

	.bottom {
		height: 20vh;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		padding: 0 40rpx;

		.login-btn {
			width: 100%;
			border-radius: 50rem !important;
			display: flex;
			align-items: center;
			justify-content: center;
			padding: 10rpx 0;

			image {
				width: 36rpx;
				height: 30rpx;
				margin-right: 10rpx;
			}
		}

		.row {
			.grid {
				width: 20%;

				image {
					width: 60rpx;
					height: 60rpx;
					margin-bottom: 10rpx;
				}
			}
		}
	}
</style>
