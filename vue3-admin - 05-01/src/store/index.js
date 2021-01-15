import { createStore } from 'vuex'
import {Login,UserInfo} from '@/api/user'

export default createStore({
    state:{
        token:localStorage.getItem('token'),
        avatar:localStorage.getItem('avatar'),
        username:localStorage.getItem('username'),
        roles:[]
    },

    mutations:{
        SET_TOKEN:(state,token) =>{
            state.token = token
        },
        SET_AVATAR:(state,avatar) =>{
            state.avatar = avatar
        },
        SET_USERNAME:(state,username) =>{
            state.username = username
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
    },

    actions:{
        /* login */
        LoginResult({commit}, userInfo) {
            return new Promise((resolve,reject) =>{
                Login(userInfo).then(response =>{
                    const {code ,token} = response.data
                    if(code == 200) {
                        localStorage.setItem('token',token)
                        commit('SET_TOKEN',token)
                    }
                    resolve(response.data)
                }).catch(error =>{
                    reject(error)
                })
            })
        },

        /* getUserInfo */
        GetInfo ({commit},token) {
            return new Promise((resolve,reject) => {
                
                UserInfo(token).then(response =>{
                    const {code ,data} = response.data
                    
                    if(code == 200) {
                        commit('SET_AVATAR',data.avatar)
                        commit('SET_USERNAME',data.username)
                        commit('SET_ROLES',data.roles)
                        resolve(response.data)
                    }
                
                    
                
                }).catch(error =>{
                    reject(error)
                })
            })
        },

        /* 用户登出 */
        LogoutResult({commit}) {
            commit('SET_TOKEN','')
            commit('SET_AVATAR','')
            commit('SET_USERNAME','')
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('avatar')
        }
      
    },

    getters:{
        token: state => state.token,
        avatar: state => state.avatar,
        username: state => state.username,
        roles: state => state.roles,
    }
})