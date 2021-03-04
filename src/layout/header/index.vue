<!--  -->
<template>
  <div class="app-avatar">
    <a-dropdown>
      <span class="ant-dropdown-link">
        <a-avatar :src="avatar" />
        {{ username }}
        <DownOutlined />
      </span>
      <!--  收纳部分template  -->
      <template #overlay>
        <a-menu>
          <a-menu-item @click="Logout">退出登录</a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
  </div>
</template>

<script>
import { DownOutlined } from "@ant-design/icons-vue";
import {reactive,toRefs} from 'vue'
import {useStore} from 'vuex'
import {message, Modal} from 'ant-design-vue'
import {useRouter,useRoute} from 'vue-router'
export default {
  components: {
    DownOutlined,
  },

  
  setup() {
    const store = useStore()
       const router = useRouter()
    const route = useRoute()

    const state = reactive({
      avatar:store.getters.avatar,
      username:store.getters.username
    })

    /* 用户登出 */
    const Logout = ()=>{
      Modal.confirm({
        title:'您确定退出登录吗?',
        onOk(){
          store.dispatch('user/LogoutResult').then(()=>{
            message.success('退出成功')
              router.replace({
              name: 'login',
              query: {
                redirect: route.fullPath
              }
            })
          })
        }

      })
    }

    return {
      ...toRefs(state),
      Logout
    }
  }
};
</script>
<style lang='less' scoped>
.app-avatar {
  .ant-dropdown-link {
    display: block;
    min-height: 64px;
    cursor: pointer;
  }
}
</style>