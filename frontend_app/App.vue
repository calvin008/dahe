<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang='scss'>
	/*每个页面公共css */
	/* iconfont */
	@import '@/static/icon/iconfont.css';
	@import '@/static/style/app.scss';
	
	.page {
		background-color: #666999;
	}
</style>
