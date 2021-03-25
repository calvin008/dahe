import instance from './request'

export const GetIconList = () =>{
    return instance({
        url:'/getIconList',
        method:'GET'
    })
}