const getters = {
    token: state => state.user.token,
    avatar: state => state.user.avatar,
    username: state => state.user.username,
    roles: state => state.user.roles,
    menus: state => state.user.menus
    
}

export default getters