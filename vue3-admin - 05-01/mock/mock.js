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
    const {token} = req.body
    let roles = ['admin']
    let username = 'admin'
    if(token == 'admin-token') {
      roles = ['admin']
      username = 'admin'
     
    } 
    if (token == 'editor-token') {
      roles = ['editor']
      username = 'editor'
      
    } 
    if (token == 'test-token') {
       roles = ['test']
       username = 'test'
      
    }

    return {
      code : 200,
      msg: 'success',
      data: {
        roles,
        username,
        avatar:'https://i.gtimg.cn/club/item/face/img/2/15922_100.gif'
      }
    }
   
  }

)