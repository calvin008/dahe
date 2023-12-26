<template>
	<view>
		<!-- 弹窗背景 -->
		<view v-show="ifshow" @touchmove.top.prevent class="popup-layer" @tap="close"></view>
		<!-- 弹窗内容 -->
		<view class="popup-content" :style="_location" >
			<slot name="content"></slot>
		</view>
	</view>
</template>

<script setup>
	import {ref,computed,onMounted,watch} from 'vue'
	const props = defineProps({
		showPop:{
			type:Boolean,
			default:false
		},
		direction:{
			type:String,
			default:'',
		}
	})
	// 控制弹窗显示
	const ifshow= ref(false)
	// 位移
	const translateVale = ref(-100)
	
	const _translate = computed(()=>{
		const transformObj = {
			'top':`transform:translateY(${-translateVale.value}%)`,
			'bottom':`transform:translateY(${translateVale.value}%)`,
			'left':`transform:translateX(${-translateVale.value}%)`,
			'right':`transform:translateX(${translateVale.value}%)`
		}
		
		return transformObj[props.direction]
	})
	
	const _location= computed(()=>{
		const positionValue = {
			'top':'bottom:0px;width:100%;',
			'bottom':'top:0px;width:100%;',
			'left':'right:0px;height:100%;',
			'right':'left:0px;height:100%;'
		}
		return positionValue[props.direction] + _translate.value
	})
	
	onMounted(()=>{
		if(props.showPop) {
			show()
		}
	})
	
	watch(()=>props.showPop,()=>{
		if(props.showPop) {
			show()
		}else {
			close()
		}
	})
	
	const show = ()=>{
		ifshow.value=true
		translateVale.value = 0
	}
	
	const close = ()=>{
		ifshow.value=false
		translateVale.value = -100
	}
	
</script>

<style lang="scss">
	.popup-layer {
		position: fixed;
		z-index: 9990;
		background: rgba(0,0,0,.3);
		height: 100%;
		width: 100%;
		top:0px;
		left:0px;
		overflow: hidden;
	}
	.popup-content {
		position: fixed;
		z-index: 9991;
		background: #ffffff;
		transition: all .3s ease;
		overflow: hidden;
		box-sizing: border-box;
	}
</style>