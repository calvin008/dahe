<!--  -->
<template>
  <div class="tabs-content">
        <div>
         <!--  a-tabs 示例 --> 
         <a-tabs v-model:activeKey="activeKey"
         hideAdd type="editable-card"
          @change="changePage"
          @edit="removeTab"
        >
            <a-tab-pane
            v-for ="item in visitedRoutes"
            :key="item.fullPath"
            :tab="item.name"
            ></a-tab-pane>
         </a-tabs>
        </div>
        <div>
            <router-view></router-view>
        </div>
  </div>
</template>

<script>
import {useRoute,useRouter} from "vue-router";
import {watch,reactive,toRefs} from 'vue'
import {message} from 'ant-design-vue'

export default {
  setup(){
    
    const route = useRoute()
    const router = useRouter()

    const state = reactive({
      activeKey:route.fullPath,
      visitedRoutes:[]
    })
 

    watch(() => route.fullPath,(to) =>{
        if(!state.visitedRoutes.find((item) => item.fullPath == to)) {
          const {fullPath, name , path} = route
          const routes = {fullPath, name , path}
          state.visitedRoutes.push(routes)
          state.activeKey = to
        } else {
          state.activeKey = to
        }
    })

    //切换tab
    const changePage = (key) =>{
        state.activeKey = key
        router.push(key)
    }

    //删除tab
    const removeTab = (fullPath) =>{
      if(state.visitedRoutes.length === 1) {
        return message.warning('这已经是最后一页,不能再删除了!')
      }
        const routePath = state.visitedRoutes.find((item) =>{
           return fullPath === item.fullPath
        })
        
        state.visitedRoutes.forEach((item,index)=>{
          if(item.fullPath === routePath.fullPath) {
            state.visitedRoutes.splice(index,1)
          }
        })

    }

    return {
      ...toRefs(state),
      changePage,
      removeTab
    }
  
  }
}

</script>
<style lang='less' scoped>
.tabs-content {
    border-top: 1px solid #eee;
    ::v-deep(.tabs) {

    .ant-tabs-bar {
      padding: 4px 20px 0 10px;
      margin: 0;
      background-color: white;
      user-select: none;
    }

    .ant-tabs-tabpane {
      display: none;
    }
  }

  .tabs-view-content {
    padding: 20px 14px 0;
    /*height: calc(100vh - #{$header-height});*/
    height: calc(100vh - 110px);
    overflow: auto;
  }
}
</style>