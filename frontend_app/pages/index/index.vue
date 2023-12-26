<template>
	<view class="container">
		<!-- header -->
		<view class="nav">
			<view class="header">
				<!-- 门店信息和选参 -->
				<view class="center">
					<view class="store">
						<!-- 门店 -->
						<view class="title">
							<image src="/static/images/order/order_icon_address.png" class="left-icon"></image>
							<!-- 门店信息 -->
							<view class="address">请选择门店</view>
							<image src="/static/images/common/black_arrow_right.png" class="right-icon"></image>
						</view>
						<!-- 外卖或者自取 -->
						<view class="buttons">
							<button type="default" class="button" :class="{active:orderType == '到店自取'}" plain
								hover-class="none">自取</button>
							<button type="default" class="button" :class="{active:orderType == '外卖配送'}" plain
								hover-class="none">外卖</button>
						</view>
					</view>

				</view>
				<!-- 公告 -->
				<view class="notices">
					<img src="../../static/images/index/me_icon_notification.png" class="image">
					<text class="title">{{appProperties.shopNotice}}</text>
				</view>
			</view>
		</view>
		<!-- header结束 -->
		<!-- menu开始 -->
		<view class="content">
			<!-- 左侧菜单 -->
			<scroll-view class="menus" scroll-with-animation scroll-y>
				<view class="wrapper">
					<view v-for="(item,index) in goods" class="menu" :key="index"
						@tap="handleMenuTap(item.goodsCategoryName)"
						:class="{current:item.goodsCategoryName === currentCategoryName}">
						<text>{{item.goodsCategoryName}}</text>
					</view>
				</view>
			</scroll-view>
			<!-- 右侧菜单 -->
			<scroll-view class="goods" scroll-with-animation scroll-y :scroll-top="categoryScrollTop"
				@scroll="handleGoodsScroll">
				<view class="wrapper">
					<view class="list">
						<!-- 推荐信息 -->
						<view id="ads">
							王大合推荐
						</view>
						<!-- 商品展示 -->
						<view v-for="(category,index) in goods" :key="index" :id="`products-${category.displayOrder}`">
							<view class="category">
								<view>
									<text class="title">{{category.goodsCategoryName}}</text>
								</view>
								<view class="items">
									<!-- 菜单子数据 -->
									<view v-if="category.goodsCategoryShow" class="good"
										v-for="(goods,key) in category.goodsList" :key="key">
										<image :src="goods.image" class="image"></image>
										<view class="right">
											<text class="name">{{goods.name}}</text>
											<text class="tips">{{goods.description}}</text>
											<view class="price_and_action">
												<text class="price">{{goods.defaultPrice /100}}</text>
												<!-- 商品选择按钮 -->
												<view v-if="goods.isSell">
													<!-- 选规格 -->
													<view class="btn-group"
														v-if="goods.goodsPropertyVos && goods.goodsPropertyVos.length">
														<button type="primary" class="btn property_btn" size="mini"
															@tap="showGoodDetailModal(goods)">选规格</button>
													</view>
													<!-- 单选 -->
													<view class="btn-group" v-else>
														<button type="primary" size="mini" class="btn add_btn"
														@tap="handleReduceFormCart(goods)" v-show="goodsCartNum(goods.id)">
															<view class="iconfont icon-sami-select"></view>
														</button>
														<view class="number" v-show="goodsCartNum(goods.id)">
															{{goodsCartNum(goods.id)}}
														</view>
														<button type="primary" size="mini" class="btn add_btn"
														@tap="handleAddCart(goods)">
															<view class="iconfont icon-add-select"></view>
														</button>
													</view>
												</view>
											</view>
										</view>
									</view>

								</view>
							</view>
						</view>
					</view>

				</view>
			</scroll-view>
			<!-- 右侧菜单结束 -->
			<!-- 购物车开始 -->
			<view class="cart-box" v-if="cart && cart.length" @tap="cartPopupVisible = !cartPopupVisible ">
				<view class="mark" >
					<image src="/static/images/index/icon_shopping_bag.png" class="cart-img" />
					<view class="tag">{{getCartGoodsNumber}}</view>
				</view>
				<view class="price">{{getCartGoodsPrice / 100}}</view>
				<view v-if="!appProperties.shopStatus" >
					<button disabled type="primary" class="pay-btn">商家休息中</button>
				</view>
				<view v-else>
					<button type="primary" class="pay-btn" :disabled="disablePay">{{disablePay? `` :'去结算'}}</button>
				</view>
			</view>
			<!--购物车结束 -->
		</view>
		<!-- 购物展示结束 -->
		<!-- modal框 -->
		<modal :show="goodDetailModalVisible" class='good-detail-modal'>
			<view class="cover">
				<image v-if="currentGoods.image" :src="currentGoods.image" class="image"></image>
				<view class="btn-group">
					<view class="iconfont icon-return" @tap="closeGoodDetailModal"></view>
				</view>
			</view>
			<!-- 商品属性 -->
			<scroll-view scroll-y="true" class="detail">
				<view class="wrapper">
					<!-- 基础信息 -->
					<view class="basic">
						<view class="name">{{currentGoods.name}}</view>
						<view class="tips">{{currentGoods.description}}</view>
					</view>
					<!-- 商品属性 -->
					<view v-if="currentGoods.goodsPropertyVos && currentGoods.goodsPropertyVos.length"
						class="properties">
						<view class="property" v-for="(goodsPropertyVo,index) in currentGoods.goodsPropertyVos"
							:key="index">
							<view class="title">
								<text class="name">
									<text v-if="goodsPropertyVo.required" style="color:red">*</text>
									{{goodsPropertyVo.category}}
								</text>
							</view>
							<!-- 属性信息 -->
							<view class="values">
								<view class="value" v-for="(propery,index2) in goodsPropertyVo.propertyList "
									:key="index2" :class="{default:propery.isDefault}"
									@tap="changePropertyDefault(index,index2)">
									{{propery.propertyOption}}
									{{propery.rebasePrice ? propery.rebasePrice / 100 :propery.extraPrice ? propery.extraPrice /100  : ""}}
								</view>

							</view>
						</view>
					</view>
				</view>
			</scroll-view>
			<!-- 显示已选择 -->
			<view class="action">
				<view class="left">
					<!-- 价格 -->
					<view class="price">{{ currentGoods.realPrice / 100}} </view>
					<!-- 属性总 -->
					<view class="props" v-if="getGoodSelectedProps(currentGoods)">
						{{getGoodSelectedProps(currentGoods)}}
					</view>

				</view>
				<!-- modal框商品增减 -->
				<view class="btn-group">
					<button type="default" plain class="btn" size="mini"
					@tap="changeCurrentGoodsNumber(-1)" v-show="currentGoods.number">
						<view class="iconfont icon-sami-select"></view>
					</button>
					<view class="number" v-show="currentGoods.number">{{currentGoods.number}}</view>
					<button type="primary" class="btn" size="mini"
					@tap="changeCurrentGoodsNumber(1)">
						<view class="iconfont icon-add-select"></view>
					</button>
				</view>
			</view>
			<view class="add-to-cart-btn" @tap="addCurrentGoodsToCart()">
				<view>确认</view>
			</view>
		</modal>
		
			<!-- 购物车详情popup -->
			<popup-layer direction="top" :show-pop="cartPopupVisible" class="cart-popup">
				<template #content>
					<view class="top"><text @tap="handleCartClear">清空</text></view>
					<scroll-view class="cart-list" scroll-y>
						<view class="wrapper">
							<view class="item" v-for="(goods, index) in cart" :key="index">
								<view class="left">
									<view class="name">{{ goods.name }}</view>
									<view class="props">{{ getGoodSelectedProps(goods) }}</view>
								</view>
								<view class="center">
									<text>￥{{ goods.realPrice / 100 }}</text>
								</view>
								<view class="right">
									<!-- 购物车里添加数量 -->
									<button type="default" plain size="mini" class="btn" hover-class="none"
										@tap="handleCartItemReduce(index)">
										<view class="iconfont icon-sami-select"></view>
									</button>
									<view class="number">{{ goods.number }}</view>
									<!-- 购物车里减少数量 -->
									<button type="primary" class="btn" size="min" hover-class="none"
										@tap="handleCartItemAdd(index)">
										<view class="iconfont icon-add-select"></view>
									</button>
								</view>
							</view>
							
						</view>
					</scroll-view>
				</template>
			</popup-layer>

	</view>
</template>

<script setup>
	import {
		getCurrentInstance,
		onMounted,
		ref,
		computed
	} from 'vue'
	const {
		proxy
	} = getCurrentInstance()
	import {
		userStore
	} from '@/store/user.js'
	import  {
		goodsStore
	} from '@/store/goods.js'
	import {
		storeToRefs
	} from 'pinia'
	import modal from '@/components/modal/modal.vue'
	import popupLayer from '@/components/popup-layer/popup-layer.vue'
	
	const message = ref('hello')
	const useUserStore = userStore()
	const useGoodsStore = goodsStore()
	const {
		appProperties
	} = storeToRefs(useUserStore)
	// 解构购物车
	const {cart} =storeToRefs(useGoodsStore)
	// menu菜单信息
	const goods = ref([])
	// 右侧分类顶部
	const categoryScrollTop = ref(0)
	// 当前类目
	const currentCategoryName = ref('盖浇饭系列')
	const goodDetailModalVisible = ref(false)
	// 选择商品
	const current = ref([])
	const currentGoods = computed(() => {
		return current.value
	})
	// 购物车列表
	const cartPopupVisible = ref(false)
	
	const getConfig = async () => {

		await proxy.$request('/api-app/config', 'get').then(res => {
			useUserStore.appProperties = res.data
			console.log(useUserStore.appProperties)
		})

		await proxy.$request('/api-app/goods/goodsMenu/list', 'get').then(result => {
			goods.value = result.data
			
		})

	}

	// 计算高度
	const calcSize = () => {
		let h = 0
		let view = uni.createSelectorQuery().select('#ads')
		view.fields({
			size: true
		}, data => {
			h += Math.floor(data.height)
		}).exec()

		goods.value.forEach(item => {
			let view = uni.createSelectorQuery().select(`#products-${item.displayOrder}`)
			view.fields({
				size: true
			}, data => {
				item.top = h
				h += Math.floor(data.height)
				item.bottom = h

			}).exec()
		})
		

	}

	onMounted(async () => {
		await getConfig()
		await calcSize()
	})

	// 点击左侧,右侧跳转对应分类
	const handleMenuTap = (goodsCategoryName) => {
		categoryScrollTop.value = goods.value.find(item => item.goodsCategoryName == goodsCategoryName).top
		currentCategoryName.value = goodsCategoryName
	}

	// 右侧滑动,可以改变左侧样式
	const handleGoodsScroll = ({
		detail
	}) => {
		const {
			scrollTop
		} = detail
		let tabs = goods.value.filter(item => item.top <= scrollTop).reverse()
		if (tabs.length > 0) {
			currentCategoryName.value = tabs[0].goodsCategoryName;
		}

	}

	// 计算真实价格
	const getGoodsRealPrice = (goods) => {
		let realPrice = goods.realPrice;
		if (goods.goodsPropertyVos && goods.goodsPropertyVos.length) {
			let vos = goods.goodsPropertyVos;
			for (let i = 0; i < vos.length; i++) {
				let propertyList = vos[i].propertyList;
				for (let j = 0; j < propertyList.length; j++) {
					if (propertyList[j].isDefault && propertyList[j].extraPrice) {
						realPrice += propertyList[j].extraPrice;
						break;
					}
				}
			}
		}
		return realPrice;

	}
	// 点击modal框
	const showGoodDetailModal = (currentGoods) => {
		currentGoods.number = 1
		currentGoods.realPrice = getGoodsRealPrice(currentGoods)
		console.log(currentGoods.realPrice)
		goodDetailModalVisible.value = true
		current.value = currentGoods
	}

	// 关闭modal框
	const closeGoodDetailModal = () => {
		goodDetailModalVisible.value = false
		proxy.$request('/api-app/goods/goodsMenu/list', 'get').then(result => {
			goods.value = result.data
			console.log(goods.value)
		})
	}

	// 改变商品子属性
	const changePropertyDefault = (categoryIndex, propertyIndex) => {
		let propertyCategory = current.value.goodsPropertyVos[categoryIndex].category
		let propertyList = current.value.goodsPropertyVos[categoryIndex].propertyList

		if (current.value.goodsPropertyVos[categoryIndex].required) {
			propertyList.forEach(property => property.isDefault = false)
			propertyList[propertyIndex].isDefault = true
			if (propertyList[propertyIndex].rebasePrice) {
				current.value.realPrice = current.value.realPrice - current.value.defaultPrice + propertyList[
					propertyIndex].rebasePrice;
				current.value.defaultPrice = propertyList[propertyIndex].rebasePrice;
				current.value.number = 1;
				current.value.propertyStr = getGoodSelectedProps(current.value);
			}
		} else {
			if (propertyList[propertyIndex].isDefault) {
				propertyList[propertyIndex].isDefault = false
				current.value.realPrice -= propertyList[propertyIndex].extraPrice;
			} else {
				propertyList[propertyIndex].isDefault = true
				current.value.realPrice += propertyList[propertyIndex].extraPrice;
			}
		}
	}

	// 计算当前餐品属性 空格隔开
	const getGoodSelectedProps = (goods) => {
		if (goods.goodsPropertyVos) {
			let propertyStr = '';
			goods.goodsPropertyVos.forEach(goodsPropertyVo => {
				goodsPropertyVo.propertyList.forEach(property => {
					if (property.isDefault) {
						propertyStr += property.propertyOption + ' '
					}
				})
			})
			return propertyStr;
		}
		return '';
	}
	
	// modal框商品增减
	const changeCurrentGoodsNumber = (number)=>{
		current.value.number+= number
		if(current.value.number<0) {
			current.value.number = 0
		}
	}
	
	// 添加购物车
	const handleAddCart = (goods)=>{
		let propertyStr = getGoodSelectedProps(goods)
		goods.propertyStr = propertyStr
		// 往下需要存储cart数据,判断购物车是否已经有此商品
		for (let i= useGoodsStore.cart.length -1; i>=0; i--) {
			if(useGoodsStore.cart[i].id == goods.id && useGoodsStore.cart[i].propertyStr == propertyStr) {
				useGoodsStore.cart[i].number++;
				return;
			}
		}
		
		// 购物车不存在
		if(!goods.number) goods.number =1;
		useGoodsStore.setCart(goods)
	}
	
	// modal框确认加入购物车
	const addCurrentGoodsToCart = ()=>{
		if(current.value && current.value.number) {
			handleAddCart(current.value)
		}
		
		closeGoodDetailModal()
	}
	
	// 购物车直接减少
	const handleReduceFormCart = (goods)=>{
		let propertyStr = getGoodSelectedProps(goods)
		for (let i= useGoodsStore.cart.length -1; i>=0; i--) {
			if(useGoodsStore.cart[i].id == goods.id && useGoodsStore.cart[i].propertyStr == propertyStr) {
				if(useGoodsStore.cart[i].number<=1) useGoodsStore.cart.splice(i,1)
				else useGoodsStore.cart[i].number -=1;
				return;
			}
		}
	}
	
	//计算商品添加数量
	const goodsCartNum = computed(()=>{
		return id=>
			useGoodsStore.cart.reduce((acc,cur)=>{
				if(cur.id === id) {
					return (acc += cur.number)
				}
				return acc;
			},0)
	})
	
	// 计算购物车商品总数量
	const getCartGoodsNumber = computed(()=>{
		return useGoodsStore.cart.reduce((acc,cur)=>acc + cur.number,0)
	})
	
	// 购物车总价
	const getCartGoodsPrice = computed(()=>{
		let totalPrice = 0;
		useGoodsStore.cart.forEach(goods =>{
			totalPrice += goods.realPrice* goods.number
		})
		return totalPrice;
	})
	
	// 清空购物车
	const handleCartClear = ()=>{
		uni.showModal({
			title:'提示',
			content:'确定要清空购物车吗?',
			success:({confirm})=>{
				if(confirm) {
					cartPopupVisible.value = false
					useGoodsStore.cart= []
				}
			}
		})
	}
	
	// 购物车商品加减
	const handleCartItemAdd = (index)=>{
		useGoodsStore.cart[index].number +=1
	}
	const handleReduceItemCart = (index)=>{
		if(useGoodsStore.cart[index].number===1) {
			useGoodsStore.cart.splice(index,1)
		} else {
			useGoodsStore.cart[index].number -=1
		}
		if(!useGoodsStore.cart.length) {
			cartPopupVisible.value = false
		}
		
	}
</script>

<style lang="scss">
	@import './index.scss';
</style>
