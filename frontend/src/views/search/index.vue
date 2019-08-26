<template>
  <div class="app-container">
  <el-row>
      <el-col el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">
          <el-form ref="form" :model="form" label-width="80px">
     <el-form-item >
        <el-radio-group v-model="form.resource">
          <el-radio label='关键词搜索'/>
          <el-radio label="高级搜索"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item >
        <el-input v-model="form.desc" placeholder="请输入抖音达人名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">搜索一下</el-button>
        <el-button @click="onCancel">即刻收录</el-button>
        <div>
           如未搜索到，可以点击 即刻收录 添加达人
        </div>
      </el-form-item>
      <el-form-item>
        <div v-if="form.resource=='高级搜索'">
          hello word
        </div>
      <div v-if="form.resource=='关键词搜索'">
        
      </div>
      </el-form-item>
      </el-form>
      </el-col>
  </el-row>
  
  </div>
    
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Form extends Vue {
  private form = {
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
    
  };

  private onSubmit() {
    this.$message('submit!');
  }

  private onCancel() {
    this.$message({
      message: 'cancel!',
      type: 'warning',
    });
  }
}
</script>

<style lang="scss" scoped>
.app-container {
    border:1px solid #f5f2f0;
}

</style>

