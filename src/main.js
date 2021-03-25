import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import {setupAntd} from './ant-design-vue'
import store from './store'
import '../mock/mock'
import './permission'



const  app = createApp(App)

setupAntd(app)
app.use(store)
app.use(router)
app.mount('#app')


