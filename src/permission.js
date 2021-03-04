import router from "@/router"
import store from './store'


router.beforeEach(async(to, from, next) => {
    if(to.path === '/login') {
      next()
    } else {
      let token = localStorage.getItem('token')
      if(!token) {
        next('/login')
      } else {
        const hasRoles = store.getters.roles && store.getters.roles.length > 0
        if(hasRoles) {
            next()
        } else {
          let {data} = await store.dispatch('user/GetInfo', token)
          const accessedRoutes = await store.dispatch('asyncRouter/generateRoutes', data.roles)
          accessedRoutes.forEach((item) =>{
            router.addRoute(item)
          })
          router.push({ path: to.path })
        }
      }
    }
    
  })