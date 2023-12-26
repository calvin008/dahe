<template>
	<div class="page">order</div>
</template>

<script setup>
	import {onMounted} from 'vue'
	import {userStore} from '@/store/user.js'
	
	const useUserStore = userStore()
	onMounted(()=>{
		console.log(useUserStore.appProperties)
	})
	
</script>
<style>
</style>