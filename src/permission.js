import router from "@/router"
import store from './store'


router.beforeEach(async(to, from, next) => {
  console.log("beforeEach ENTER", from.path, "=>", to.path)
  let token = localStorage.getItem('token')
  const hasRoles = store.getters.roles && store.getters.roles.length > 0
  if(to.path === '/login') {
   next()
  } else if(!token) {
    next('/login')
  }else if(hasRoles) {
    next()
  } else {
    {
      let {data} = await store.dispatch('user/GetInfo', token)
      const accessedRoutes = await store.dispatch('asyncRouter/generateRoutes', data.roles)
      accessedRoutes.forEach((item) =>{
        router.addRoute(item)
      })
      router.push({ path: to.path })
    } 
  }
  })