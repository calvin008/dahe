/**
 * 权限树 授权的时候使用 细粒度
 id: 'title',
 label: 'title',
 children: 'children'

 permission作为key
 */
const permissionTree = [
    {
        title: '首页',
        permission: "system:index",
    },

    {
        title: '用户管理',
        permission: "system:user",
    },
    {
        title: '门店管理',
        permission: "system:store",
    },
    {
        title: '商品管理',
        permission: "system:goods",
        children: [
            {
                title: '商品种类',
                permission: 'system:goods:goodsCategory'
            },
            {
                title: '商品信息',
                permission: 'system:goods:goodsInfo',
                children: [
                    {
                        title: '商品查看',
                        permission: "system:goods:goodsInfo:list" // TODO 真正可以添加到数据库的权限值, 对应到具体的后台接口权限
                    },
                    {
                        title: '添加商品',
                        permission: "system:goods:goodsInfo:add" // TODO 真正可以添加到数据库的权限值, 对应到具体的后台接口权限
                    },
                    {
                        title: '修改商品',
                        permission: "system:goods:goodsInfo:update"
                    },
                    {
                        title: '删除商品',
                        permission: "system:goods:goodsInfo:delete"
                    }
                ]
            }
        ]
    },
    {
        title: '订单管理',
        permission: "system:order",
        children: [
            {
                title: '订单查询',
                permission: 'system:order:orderInfo',
                
            },
            {
                title: '订单管理',
                permission: 'system:order:orderManage'
            }
        ]
    },
    {
        title: '系统管理',
        permission: "system:admin",
        children: [
            {
                title: '系统配置',
                permission: 'system:admin:config'
            },
            {
                title: '定时任务',
                permission: "system:admin:timingTask"

            },
            {
                title: '管理员管理',
                permission: "system:admin:sysUser",
                children: [
                    {
                        title: '添加管理员',
                        permission: "system:admin:sysUser:add" 
                    },
                    {
                        title: '删除管理员',
                        permission: "system:admin:sysUser:delete"
                    }
                ]
            },
            {
                title: '角色管理',
                permission: "system:admin:role"
            },{
                title: '菜单管理',
                permission: "system:admin:menu"
            }
        ]
    }

]

export default permissionTree
