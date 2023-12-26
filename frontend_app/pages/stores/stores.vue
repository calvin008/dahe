<template>
	<view class="container">
		<view class="page-map">
			<map style="width:100%;height: 100%;" :latitude="latitude" :longitude="longitude" :markers="markers"></map>
		</view>
		<view style="height: 400rpx; width: 100%;"></view>
		<!-- 门店列表 -->
		<view class="content">
			<scroll-view scroll-y="true" >
				<view class="store" v-for="(store,index) in storeData" :key="index" >
					<!-- 左 -->
					<view class="store-left">
						<view class="store-title">
							<view class="store-name">
								{{store.storeName}}
							</view>
							<view class="store-status"
							v-if="new Date().getHours() >= appProperties.businessStartTime && new Date().getHours()<= appProperties.businessEndTime"
							>
								营业中
							</view>
							<view class="store-status" v-else>
								休息中
							</view>
						</view>
						<view class="store-content">
							<view class="distance">距离您{{store.distance}}km</view>
							<view class="store-text">
								营业时间{{appProperties.businessStartTime}}:00 - {{appProperties.businessEndTime}}:00
							</view>
						</view>
					</view>
					<!-- 右侧 -->
					<view class="store-right" >
						<view class="look" @tap="tapstore(store)">去看看</view>
						<view class="icon">
							<view class="round" @tap="call(store.storeMobile)">
								<image src="../../static/images/store/store-tel.png" ></image>
							</view>
							<view class="round" @tap="openMap(store)">
								<image src="../../static/images/store/store-nav.png" ></image>
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		getCurrentInstance,
		computed
	} from "vue";
	import {goodsStore} from '@/store/goods.js'
	import {
		userStore
	} from '@/store/user.js'
	import {
		storeToRefs
	} from 'pinia'
	const useUserStore = userStore()
	const useGoodsStore = goodsStore()
	const {
		appProperties
	} = storeToRefs(useUserStore)
	
	const {
		proxy
	} = getCurrentInstance()
	const latitude = ref()
	const longitude = ref()
	const storeData = ref([])
	const markers = ref([])

	const getStoreData = () => {
		return new Promise((resolve, reject) => {
			// 获取用户定位
			uni.getLocation({
				type: 'gcj02',
				success(resLocation) {
					if (resLocation.latitude && resLocation.longitude) {
						resolve(resLocation)
					}
				}
			})
		}).then(local => {
			let la1 = local.latitude
			let lo1 = local.longitude
			return proxy.$request('/store/query', 'get').then(resStore => {
				let temp = []
				let l = resStore.data.length
				if (l > 1) {
					for (let i = 0; i < l; i++) {
						let element = resStore.data[i]
						let d = distance(element.storeLatitude, element.storeLongitude, la1, lo1)
						element.distance = d
						temp.push(element)
					}
					storeData.value = temp.sort(function(a, b) {
						return a.distance - b.distance
					})
					return storeData.value
				}
			})
		}).then(resMap => {
			console.log(resMap)
			latitude.value = resMap[0].storeLatitude
			longitude.value = resMap[0].storeLongitude
			let map = []
			let len = resMap.length
			if (len >= 1) {
				for (let i = 0; i < len; i++) {
					let maps = {}
					maps.id = i
					maps.latitude = resMap[i].storeLatitude
					maps.longitude = resMap[i].storeLongitude
					maps.iconPath = '/static/images/store/logo.png'
					maps.width = 30
					maps.height = 30
					maps.callout = {
						'content': resMap[i].storeName,
						'display': 'ALWAYS',
						'bgColor': '#000000',
						'color': '#ffffff'
					}
					map.push(maps)
				}

				markers.value = map
			}
		})
	}

	getStoreData()

	// 计算距离
	const distance = (la1, lo1, la2, lo2) => {
		var La1 = la1 * Math.PI / 180.0; //角度转弧度
		var La2 = la2 * Math.PI / 180.0;
		var La3 = La1 - La2;
		var Lb3 = lo1 * Math.PI / 180.0 - lo2 * Math.PI / 180.0;
		var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(La3 / 2), 2) + Math.cos(La1) * Math.cos(La2) * Math.pow(Math
			.sin(Lb3 / 2), 2)));
		s = s * 6378.137;
		s = Math.round(s * 10000) / 10000;
		s = s.toFixed(2)
		return s
	}
	
	// 跳转
	const tapstore = (store)=>{
		useGoodsStore.setStoreInfo(store)
		uni.switchTab({
			url:'/pages/index/index'
		})
		
	}
	
	// 拨打门店电话
	const  call = (storeMobile)=>{
		uni.makePhoneCall({
			phoneNumber:storeMobile,
			success:function(){
				console.log('拨打电话成功')
			},
			fail:function(){
				console.log('拨打电话失败')
			}
		})
	}
	
	// 跳转导航
	const openMap =(store)=>{
		uni.openLocation({
			latitude:store.storeLatitude,
			longitude:store.storeLongitude,
			scale:28,
			name:store.storeName,
			address:store.address
		})
	}
	
	
	
	
</script>

<style lang="scss">
	.container {

		width: 100%;
		height: 100%;

		.page-map {

			position: fixed;
			top: 0;
			width: 100%;
			height: 400rpx;
		}

		.content {
			
			height: 75vh;
			overflow: auto;
			width: 100%;

		}

		.store {
			display: flex;
			flex-wrap: nowrap;
			margin: 10rpx 20rpx;
			padding: 20rpx;
			background-color: #FFFFFF;
			box-shadow: $box-shadow;
		}

		.store-left {
			width: 70%;
			display: flex;
			flex-direction: column;

			.store-title {
				display: flex;
				flex-wrap: nowrap;

				.store-name {
					color: $text-color-black;
					font-size: $font-size-lg;
					font-weight: bold;
					margin-right: 50rpx;
					white-space: nowrap;
					text-overflow: ellipsis;
					overflow: hidden;
				}

				.store-status {
					color: $color-primary;
					font-size: $font-size-sm;
					padding: 0 5rpx;
					border: 1rpx solid $color-primary;
					white-space: nowrap;
				}
			}

			.store-content {
				margin-top: 60rpx;
				font-size: $font-size-sm;
				display: flex;
				flex-direction: column;

				.store-text {
					color: $text-color-assist;
					white-space: nowrap;
					text-overflow: ellipsis;
					overflow: hidden;
				}
			}
		}

		.store-right {
			width: 30%;
			display: flex;
			flex-direction: column;
			align-items: flex-end;

			.look {
				font-size: $font-size-sm;
				color: $color-primary;
			}

			.icon {
				display: flex;
				flex-wrap: nowrap;
				align-items: center;
				margin-top: 60rpx;
				justify-content: flex-end;

				.round {
					border-radius: 50%;
					width: 60rpx;
					height: 60rpx;
					margin: 10rpx;
					background-color: $bg-color;

					image {
						display: block;
						width: 40rpx;
						height: 40rpx;
						margin: 10rpx;
						align-items: center;
					}
				}
			}
		}
	}
</style>
