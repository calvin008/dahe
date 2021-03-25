<!--  -->
<template>
  <div class="icon-container">
    <a-alert message="icon图标列表" type="success" show-icon></a-alert>
        <a-input-search
          v-model:value="iconForm.name"
          style="width: 300px"
          placeholder="图标名称"
          enter-button
          @search="queryData"
        /> 
        
    <a-row  :gutter="[16,16]">
      <a-col 
      v-for="(item, index) in iconList"
      :key="index"
      :span="4"
      >
        <div class="icon-img">
          <icon-font style="font-size: 36px" :type="item.font_class" />
        </div>
          
        
        <div class="icon-text" @click="handleCopyText(item, $event)">
          {{ item.name }}
        </div>
      </a-col>
     
    </a-row>
  </div>
</template>

<script>
import IconFont from "@/assets/iconfont/icon";
import {GetIconList} from '@/api/icon'
import {reactive,toRefs,onBeforeMount} from "vue"
export default {
  components:{
    IconFont
  },

  setup() {
    const state = reactive({
      iconList:[],
      iconForm:{
        name:'',
        font_class:''
      }
    })

    onBeforeMount(async () =>{
      const List = await GetIconList()
     
      
      
      
     state.iconList = List.data.data
     console.log(state.iconList)
    })

    const queryData = (val)=>{
     state.iconForm.name = val
     state.iconForm.font_class =  "icon-" + val
     state.iconList.splice(0,state.iconList.length)
     state.iconList.push(state.iconForm)
    }

    return {
      ...toRefs(state),
      queryData
    }

  }
}

</script>
<style lang="less">
  .icon-container {
    .ant-input-search,
    .ant-alert {
      margin-left: 20px;
      margin-bottom: 20px;
    }
    .icon-img {
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 68px;
      cursor: pointer;

      i {
        font-size: 28px;
        text-align: center;
        pointer-events: none;
        cursor: pointer;
      }
    }

    .icon-text {
      height: 30px;
      overflow: hidden;
      font-size: 12px;
      line-height: 30px;
      text-align: center;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
</style>