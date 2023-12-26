<!--  -->
<template>
  <div>
    <div style="width: 50%; padding: 30px;">
      <el-form
        :model="appConfig"
        :rules="rules"
        ref="refForm"
        label-width="200px"
        class="demo-ruleForm"
      >
        <el-form-item label="店铺名称" prop="shopName">
          <el-input v-model="appConfig.shopName" disabled></el-input>
        </el-form-item>
        <el-form-item label="营业状态" prop="shopStatus">
          <el-switch
            v-model="appConfig.shopStatus"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="营业中"
            inactive-text="暂停营业"
          ></el-switch>
        </el-form-item>
        <el-form-item label="小程序菜单栏上的公告" prop="shopNotice">
          <el-input type="textarea" v-model="appConfig.shopNotice" maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="外卖配送费(单位: 分)" prop="packingPrice">
          <el-input type="number" v-model="appConfig.packingPrice"></el-input>
        </el-form-item>
        <el-form-item label="外卖打包费(单位: 分)" prop="sendingPrice">
          <el-input type="number" v-model="appConfig.sendingPrice"></el-input>
        </el-form-item>
        <el-form-item label="外卖起送价格(单位: 分)" prop="sendingNeedLeastPrice">
          <el-input type="number" v-model="appConfig.sendingNeedLeastPrice"></el-input>
        </el-form-item>
        <el-form-item label="营业时间段开始时间(0-24点)" prop="businessStartTime">
          <el-input type="number" v-model="appConfig.businessStartTime"></el-input>
        </el-form-item>
        <el-form-item label="营业时间段结束时间(0-24点)" prop="businessEndTime">
          <el-input type="number" v-model="appConfig.businessEndTime"></el-input>
        </el-form-item>
        <el-form-item label="是否开启测试登录" prop="testUserLoginEnabled">
          <el-switch
            v-model="appConfig.testUserLoginEnabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="开启"
            inactive-text="关闭"
          ></el-switch>
        </el-form-item>
        <el-form-item label="联系电话1" prop="lianxidianhua1">
          <el-input v-model="appConfig.lianxidianhua1" maxlength="11"></el-input>
        </el-form-item>
        <el-form-item label="联系电话2" prop="lianxidianhua2">
          <el-input v-model="appConfig.lianxidianhua2" maxlength="11"></el-input>
        </el-form-item>
        <el-form-item label="联系QQ" prop="lianxiQQ">
          <el-input v-model="appConfig.lianxiQQ" maxlength="10"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="submitForm()">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang='ts'>
import { reactive, ref,ComponentInternalInstance, getCurrentInstance, } from 'vue';
import type { FormInstance, FormRules } from 'element-plus'
import type { configType } from './setting'
import { getAppConfig, updateAppConfig } from '@/api/system/setting'

// 引入全局变量
const { proxy } = getCurrentInstance() as ComponentInternalInstance
// 绑定配置ref
const refForm = ref<FormInstance>()

const appConfig = ref({
  shopName: "王大合茶餐厅",
  packingPrice: 0,
  sendingPrice: 100,
  sendingNeedLeastPrice: 1000,
  businessStartTime: 10,
  businessEndTime: 22,
  testUserLoginEnabled: false,
  lianxidianhua1: "",
  lianxidianhua2: "",
  lianxiQQ: "",
  shopStatus: true,
  shopNotice: ""
})
const rules = reactive<FormRules>({
  shopName: [
    { required: true, message: '请输入店铺名称', trigger: 'blur' }
  ],
  packingPrice: [
    { required: true, message: '请设置打包费', trigger: 'change' }
  ],
  sendingPrice: [
    { required: true, message: '请设置外卖配送费', trigger: 'change' }
  ],
  sendingNeedLeastPrice: [
    { required: true, message: '请设置配送需要的最低价格', trigger: 'change' }
  ],
  businessStartTime: [
    { required: true, message: '请设置每天开始营业的时间点', trigger: 'change' }
  ],
  businessEndTime: [
    { required: true, message: '请设置每天结束营业的时间点', trigger: 'change' }
  ], shopStatus: [
    { required: true, message: '请设置营业状态', trigger: 'change' }
  ]
})

// 获取数据
const getAppConfigList = () => {

  getAppConfig().then((result) => {
    appConfig.value = result.data;
  })
}
// 执行获取数据
getAppConfigList()

// 提交form
const submitForm = ()=>{
        refForm.value!.validate((valid) => {
          if (valid) {
            
            updateAppConfig(appConfig.value).then((result) => {
              proxy.$Notify.success('更新成功')
              appConfig.value = result.data
            })
          }
        });
      }
</script>
<style lang='scss' scoped>
</style>