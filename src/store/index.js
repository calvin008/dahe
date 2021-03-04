import { createStore } from 'vuex'
import user from './modules/user'
import asyncRouter from './modules/async-router'
import getters from './getters'


export default createStore({
    modules:{
        user,
        asyncRouter
    },
    getters
})