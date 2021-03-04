import Mock from 'mockjs'

/* login */
Mock.mock(
    '/api/login',
    'post',
    (req) => {
        const { password, username } = JSON.parse(req.body)
        if (username === 'admin' && password === '123456') {
            return {
              code : 200,
              token:'admin-token'
            }
          } else if(username === 'editor' && password === '123456') {
            return {
              code : 200,
              token:'editor-token'
            }
          } else if(username === 'test' && password === '123456') {
            return {
              code : 200,
              token:'test-token'
            }
          }
    }
)

/* getInfo */

Mock.mock(
  '/api/getInfo',
  'post',
  (req) => {
    
    const token= req.body
    let roles = ['admin']
    let username = 'admin001'
    if(token == 'admin-token') {
      roles = ['admin']
      username = 'admin1'
     
    } else if (token == 'editor-token') {
      roles = ['editor']
      username = 'editor'
      
    } else if (token == 'test-token') {
       roles = ['test']
       username = 'test'
      
    }

    return {
      code : 200,
      msg: 'success',
      token,
      data: {
        roles,
        username,
        avatar:'https://i.gtimg.cn/club/item/face/img/2/15922_100.gif',
        menus:[
          {
            name:'Index',
            meta:{
              icon:'icon-home-fill',
              title:'首页'
            }
          },
          {
            name:'demo',
            meta:{
              icon:'icon-wallet1',
              title:'demo'
            },
            children: [
              {
                name:'Table',
                meta: {
                  title: '表格',
                  icon: 'icon-form-fill',
                },
              },
              {
                name:'Icon',
                meta: {
                  title: '图标',
                  icon: 'icon-shuffling-banner-fill',
                },
              },
            ],
          },
          {
            name:'test',
            meta:{
              icon:'icon-inspection-fill',
              title:'test'
            },
            children:[
              {
                name:'Test1',
                meta: {
                  title: 'test1',
                  icon: 'icon-help1',
                },
              },
              {
                name:'Test2',
                meta: {
                  title: 'test2',
                  icon: 'icon-help1',
                },
              },
              {
                name:'Test3',
                meta: {
                  title: 'test3',
                  icon: 'icon-help1',
                },
              },
            ]
          },

          {
            name:'error',
            meta:{
              icon:'icon-gold-supplie-fill',
              title:'error'
            },
            children:[
              {
                name:'Error403',
                meta: {
                  title: '403',
                  icon: 'icon-help1',
                },
              },
              {
               name: 'Error404',
                meta: {
                  title: '404',
                  icon: 'icon-help1',
                },
              },
             
            ]
          },
          
          {
            name:'system',
            meta:{
              icon:'icon-set1',
              title:'system'
            },
            children:[
              {
                name:'account',
                meta: {
                  title: 'account',
                  icon: 'icon-office-supplies-fill',
                },
              },
              {
                name:'group',
                meta: {
                  title: 'group',
                  icon: 'icon-order-fill',
                },
              },
             
            ]
          },
        ] 
        
      }
    }
   
  }

)