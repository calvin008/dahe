module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/vue3-admin/' : '/',
  pwa: {
    name: 'vue3-admin'
  },
  devServer: {
    port: 8088, // 端口号
  }
}
