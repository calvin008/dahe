<!--  -->
<template>
  <div>
    <div style="text-align: left; margin: 5px 10px">
      <el-form 
      ref="refForm"
      :model="state.orderObj"
      :rules="rules"
      label-width="150px">
        <!-- 订单号选择 -->
        <el-form-item label="订单号选择">
          <el-input v-model="state.orderObj.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <!-- 门店选择 -->
        <el-form-item label="门店选择">
          <el-select v-model="state.orderObj.storeId" placeholder="请选择你要查询的门店">
            <el-option
          v-for="item in state.storeList"
          :key="item.storeId"
          :label="item.storeName"
          :value="item.storeId"
        />
          </el-select>
        </el-form-item>
        <!-- 订单状态选择 -->
        <el-form-item label="订单状态选择">
          <el-select v-model="state.orderObj.orderStatus"  placeholder="请选择你要查询的订单状态">
             <el-option
          v-for="(item,index) in state.orderStatusList"
          :key="index"
          :label="item"
          :value="item"
        />
          </el-select>
        </el-form-item>
        <!-- 订单时间选择 -->
        <el-form-item label="订单开始时间选择">
          <el-date-picker v-model="state.orderObj.createTime" 
          format="YYYY/MM/DD hh:mm:ss"
        value-format="YYYY-MM-DD hh:mm:ss" type="datetime" placeholder="订单开始时间" />
        </el-form-item>
        <el-form-item label="订单结束时间选择">
          <el-date-picker v-model="state.orderObj.finishTime" 
          format="YYYY/MM/DD hh:mm:ss"
          value-format="YYYY-MM-DD hh:mm:ss"
           type="datetime" placeholder="订单结束时间" />
        </el-form-item>
        <!-- 查询或者重置 -->
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 分割线 -->
    <el-divider />
    <div>
      <!--列表-->
      <div style="margin: 0px 10px;text-align: left;">
        <el-table
          :data="state.orderInfoAdmins"
          stripe
          :default-sort="{ prop: 'createTime', order: 'descending' }"
        >
          <el-table-column prop="orderNo" label="订单号" sortable></el-table-column>
          <el-table-column prop="orderStatus" label="订单状态" sortable></el-table-column>
          <el-table-column prop="takeType" label="取餐方式" sortable></el-table-column>
          <el-table-column prop="addressDetail" label="收货地址" show-overflow-tooltip sortable></el-table-column>
          <el-table-column prop="goodsPreview" label="商品信息"></el-table-column>
          <el-table-column prop="goodsTotalNum" label="商品总数" sortable></el-table-column>
          <el-table-column prop="totalPrice" label="总价格" sortable>
            <template #default="scope">{{ '￥' + scope.row.totalPrice / 100 }}</template>
          </el-table-column>
          <el-table-column prop="payPrice" label="支付金额" sortable>
            <template #default="scope">{{ '￥' + scope.row.payPrice / 100 }}</template>
          </el-table-column>
          <el-table-column prop="verifyNum" label="取单号"></el-table-column>
          <el-table-column prop="wxPayTransactionId" label="微信订单号"></el-table-column>
          <el-table-column prop="createTime" label="下单时间" sortable></el-table-column>
          <el-table-column prop="payTime" label="支付时间" show-overflow-tooltip sortable></el-table-column>
          <el-table-column prop="finishTime" label="完成时间" show-overflow-tooltip sortable></el-table-column>
          <el-table-column prop="userPhone" label="用户联系电话" show-overflow-tooltip></el-table-column>
          <el-table-column prop="receiver" label="取餐人" show-overflow-tooltip></el-table-column>
          <el-table-column prop="extraInfo" label="订单备注" show-overflow-tooltip></el-table-column>
        </el-table>

        <!--分页组件-->
        <el-row style="float: right">
          <el-pagination
            @current-change="changePageNo"
            @size-change="changePageSize"
            :current-page.sync="state.searchParams.pageNo"
            :page-sizes="[5, 10, 20, 30, 50, 100]"
            :page-size.sync="state.searchParams.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="state.total"
          ></el-pagination>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script setup lang='ts'>
import {getStoreList} from '@/api/app/store'
import { getOrderInfoList } from '@/api/app/orderInfoAdminApi'
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules } from 'element-plus'


// 绑定form 的ref
const refForm = ref<FormInstance>()

const state = reactive({
  searchParams: {

    pageNo: 1,
    pageSize: 10
  },
  orderObj: {
    orderNo: '',
    orderStatus: "",
    storeId: null,
    createTime: null,
    finishTime: null
  },
  storeList:[], //门店列表
  orderStatusList:['未付款','制作中','配送中','请取餐','已送达','退款中'],
  total: 0,
  orderInfoAdmins: []
})

// 获取订单信息
const getOrderInfo = () => {
   getStoreList().then(res=>{
    state.storeList = res.data;
    
  })
  getOrderInfoList(state.searchParams.pageNo, state.searchParams.pageSize, state.orderObj).then(result => {
    state.orderInfoAdmins = result.data.records;
    state.total = result.data.total;
  })
}

const rules = reactive<FormRules>({

})

// 初始化执行
getOrderInfo()

// 切换页数
const changePageNo = (pageNo: number) => {
  state.searchParams.pageNo = pageNo;
  getOrderInfo()
}
// 改变页面大小
const changePageSize = (pageSize: number) => {
  state.searchParams.pageSize = pageSize;
  getOrderInfo()
}

const onSubmit = () => {
  if(state.orderObj.orderNo) {
     state.orderObj.orderStatus="",
     state.orderObj.orderStatus=null,
     state.orderObj.storeId = null,
     state.orderObj.createTime=null,
     state.orderObj.finishTime = null
  }
  getOrderInfo()
  console.log(state.orderObj.createTime>state.orderObj.finishTime);
  
}

const reset = ()=>{
  state.orderObj = {
     orderNo: '',
    orderStatus: "",
    storeId: null,
    createTime: null,
    finishTime: null
  }
}
</script>
<style lang='scss' scoped>
</style>
