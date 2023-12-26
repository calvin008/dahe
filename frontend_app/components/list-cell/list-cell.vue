<template>
	<view class="wui-list-cell"
	:hover-class="hover ? 'wui-list-hover':''"
	:style="{background:bgcolor,fontSize:size + 'rpx',color:color,padding:padding,borderRadius:radius}">
		<slot></slot>
		<image src="/static/images/navigator-1.png" class="arrow" v-if="arrow"></image>
	</view>
</template>

<script setup>
	defineProps({
		// 是否有箭头
		arrow:{
			type:Boolean,
			default:false
		},
		// 点击效果
		hover:{
			type:Boolean,
			default:true
		},
		
		// 内边距
		padding:{
			type:String,
			default:"26rpx 30rpx"
		},
		radius:{
			type:String,
			default:"4px 4px 4px 4px"
		},
		bgcolor:{
			type:String,
			default:"#fff"
		},
		size:{
			type:Number,
			default:28
			
		},
		color:{
			type:String,
			default:"#5A5B5C"
		}
	})
</script>

<style lang="scss" scoped>
	.wui-list-cell {
		position: relative;
		width: 100%;
		box-sizing: border-box;
		overflow: hidden;
		display: flex;
		align-items: center;
	}
	.wui-list-hover {
		background: #f7f7f9 !important;
	}
	
	.wui-list-cell::after {
		countent:'';
		position: absolute;
		border-bottom: 2rpx solid #eee;
		transform: scaleY(0.8);
		bottom: 0;
		right: 0;
		left:0
	}
	.arrow {
		width: 50rpx;
		height: 50rpx;
		position: relative;
		margin-right: -10rpx;
		flex-shrink: 0;
	}
</style>