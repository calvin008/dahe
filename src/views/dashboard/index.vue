<template>
  <div class="dashboard-container">
   <panel-group />
   <el-row :gutter="40">
     <el-col el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">
       <div class='table-content'>
         <div class='table-container'> 
           <div class='table-text'>抖音成长榜</div>
          <el-tag class='table-tag'>更多</el-tag>
         </div>
         <grow-table/>
       </div>
       </el-col>
     <el-col el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">
       <div class='table-content'>
         <div class='table-container'> 
           <div class='table-text'>达人追踪</div>
          <el-tag class='table-tag'>详情</el-tag>
        </div>
          <svg icon-class="zhuye" size="10"></svg>
         
       </div>
       </el-col>
   </el-row>
   
  
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store/modules/user';
import PanelGroup from  './components/PanelGroup.vue';
import GrowTable from  './components/GrowTable.vue';



@Component({
  components: {
    PanelGroup,
    GrowTable
   
  },
})
export default class Dashboard extends Vue {
  get name() {
    return UserModule.name;
  }

  get roles() {
    return UserModule.roles;
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    padding: 32px;
    background-color: #f5f0f2;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
  .table {
    &-content {
    background-color:#fff;
    border-radius:6px;
    padding:10px;
    height:500px;
    overflow: hidden;
    }
    
    &-container {
    display:-webkit-flex;
    display:flex;
    flex-flow: nowrap;
    justify-content:space-between;
    padding:10px;
    }
    &-text {
      font-weight:bold;
    }
  
}


</style>
