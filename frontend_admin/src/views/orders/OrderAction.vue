<!--  -->
<template>
  <div>
    <!-- 订单状态 -->
    <el-row>
      <el-select v-model="state.storeId" placeholder="请选择门店" 
      style="margin-right: 15px;">
        <el-option
          v-for="item in state.storeList"
          :key="item.storeId"
          :label="item.storeName"
          :value="item.storeId"
        />
      </el-select>
      <el-button
        :type="state.currentOrderStatus === '未付款' ? 'info' : ''"
        round
        @click="getOrderInfo('未付款')"
      >未付款</el-button>
      <el-button
        :type="state.currentOrderStatus === '制作中' ? 'warning' : ''"
        round
        @click="getOrderInfo('制作中')"
      >制作中</el-button>
      <el-button
        :type="state.currentOrderStatus === '配送中' ? 'primary' : ''"
        round
        @click="getOrderInfo('配送中')"
      >配送中</el-button>
      <el-button
        :type="state.currentOrderStatus === '请取餐' ? 'success' : ''"
        round
        @click="getOrderInfo('请取餐')"
      >请取餐</el-button>
      <el-button
        :type="state.currentOrderStatus === '已送达' ? 'success' : ''"
        round
        @click="getOrderInfo('已送达')"
      >已送达</el-button>
      <el-button
        :type="state.currentOrderStatus === '退款中' ? 'danger' : ''"
        round
        @click="getOrderInfo('退款中')"
      >退款中</el-button>
    </el-row>

    <!--增加或更新的表单弹窗-->
    <el-dialog title="退款取消订单" v-model="state.showCancelReasonModal">
      <textarea
        cols="50"
        rows="10"
        maxlength="30"
        v-model="state.cancelReason"
        placeholder="输入取消订单的原因..."
      ></textarea>
      <br />
      <el-button type="primary" @click="handelCancelOrder" round>确定取消</el-button>
    </el-dialog>

    <!--列表-->
    <div style="margin: 0px 10px;text-align: left;">
      <el-table
        :data="state.orderInfoAdmins"
        stripe
        style="width: 100%"
        :default-sort="{ prop: 'createTime', order: 'descending' }"
      >
        <el-table-column type="expand">
          <template #default="props">
            <el-form label-position="left" inline>
              <el-form-item label="订单编号">
                <span>{{ props.row.orderNo }}</span>
              </el-form-item>
              <br />
              <el-form-item label="取餐方式">
                <span>{{ props.row.takeType }}</span>
              </el-form-item>
              <br />
              <el-form-item label="商品信息">
                <span>{{ props.row.goodsPreview }}</span>
              </el-form-item>
              <br />
              <el-form-item label="总价格">
                <span>￥{{ props.row.totalPrice / 100 }}</span>
              </el-form-item>
              <br />
              <el-form-item label="支付金额">
                <span>￥{{ props.row.payPrice / 100 }}</span>
              </el-form-item>
              <br />
              <el-form-item label="下单时间">
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
              <br />
              <el-form-item label="支付时间">
                <span>{{ props.row.payTime }}</span>
              </el-form-item>
              <br />
              <el-form-item label="微信订单号">
                <span>{{ props.row.wxPayTransactionId }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>

        <el-table-column prop="orderStatus" label="订单状态"></el-table-column>
        <el-table-column prop="addressDetail" label="收货地址" sortable></el-table-column>
        <el-table-column prop="goodsPreview" label="商品信息" width="250"></el-table-column>
        <el-table-column prop="goodsTotalNum" label="商品总数" sortable></el-table-column>
        <el-table-column prop="verifyNum" label="取单号"></el-table-column>
        <el-table-column prop="createTime" label="下单时间" sortable width="180"></el-table-column>
        <el-table-column prop="payTime" label="支付时间" sortable width="180"></el-table-column>
        <el-table-column prop="userPhone" label="用户电话" show-overflow-tooltip></el-table-column>
        <el-table-column prop="receiver" label="取餐人" sortable></el-table-column>
        <el-table-column prop="extraInfo" label="订单备注" width="1080"></el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <span v-if="scope.row.orderStatus == '未付款'">
              <el-button
              link
                type="primary"
                size="small"
                @click="queryWeixinOrder(scope.row.orderNo)"
              >查询支付状态</el-button>
              <el-button link
                type="primary" size="small" @click="cancelOrderNotPay(scope.row.orderNo)">取消</el-button>
            </span>
            <span v-else-if="scope.row.orderStatus != '退款中'">
              <el-button link
                type="primary" size="small" @click="toNextOrder(scope.row)">下一步</el-button>
              <el-button
              link
                type="primary"
                size="small"
                @click="refundOrder(scope.row)"
                v-if="scope.row.orderStatus != '未付款' && scope.row.orderStatus != '退款中'"
              >退款</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <el-row style="float: right">
        <el-pagination
          @current-change="changePageNo"
          @size-change="changePageSize"
          :current-page.sync="page.searchParams.pageNo"
          :page-sizes="[5, 10, 20, 30, 50, 100]"
          :page-size.sync="page.searchParams.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="page.total"
        ></el-pagination>
      </el-row>
    </div>
  </div>
</template>

<script setup lang='ts'>
import { reactive, ComponentInternalInstance, getCurrentInstance } from 'vue';
import {
  queryWeixinOrder,
  getOrderInfoAdmins,
  toNextOrderStatus,
  cancelAndRefund,
  cancelOrderNotPay,
  
} from '@/api/app/orderInfoAdminApi'
import 'element-plus/es/components/notification/style/css'
import 'element-plus/es/components/message-box/style/css'
import {getStoreList} from '@/api/app/store'
const { proxy } = getCurrentInstance() as ComponentInternalInstance

const state = reactive({
  
  storeId:8,//门店id
  storeList:[], //门店列表
  orderInfoAdmins: [],
  currentOrderStatus: '制作中',
  showCancelReasonModal: false,
  selectedOrder: {
    orderStatus: null,
    orderNo: null
  },
  cancelReason: '', // 取消订单原因
})

const page = reactive({
  searchParams: {
    searchParam1: null,
    pageNo: 1,
    pageSize: 10,
  },
  total: 0,
})

// 获取数据
const getOrderInfo = (orderStatus: string) => {
  getStoreList().then(res=>{
    state.storeList = res.data;
    console.log(res.data)
  })
  if(state.storeId) {
    state.currentOrderStatus = orderStatus;
  getOrderInfoAdmins(page.searchParams.pageNo, page.searchParams.pageSize, orderStatus,state.storeId).then(result => {
    state.orderInfoAdmins = result.data.records;
    page.total = result.data.total;
  })
  } else {
    proxy.$Alert("门店不能为空")
  }
}
// 初始化获取数据

getOrderInfo(state.currentOrderStatus)

// 切换页数
const changePageNo = (pageNo: number) => {
  page.searchParams.pageNo = pageNo;
  getOrderInfo(state.currentOrderStatus)
}
// 改变页面大小
const changePageSize = (pageSize: number) => {
  page.searchParams.pageSize = pageSize;
  getOrderInfo(state.currentOrderStatus)
}
// 确认取消订单
const handelCancelOrder = () => {

  cancelAndRefund(state.selectedOrder.orderNo, state.cancelReason).then(result => {
    proxy.$Notify.success(result.data);
    getOrderInfo(state.currentOrderStatus);
  })
}

type orderType = {
  orderStatus: null | string,
  orderNo: null | string
}
// 进入订单的下一步
const toNextOrder = (selectedOrder: orderType) => {

  if (state.selectedOrder.orderStatus === '未付款') {
    proxy.$Confirm("用户并未付款, 请确保已经线下付款或其他方式支付,是否继续?").then(() => {
      proxy.$Confirm("订单目前的状态为: " + selectedOrder.orderStatus + "请再次确认是否继续").then(() => {
        toNextOrderStatus(selectedOrder.orderNo, selectedOrder.orderStatus).then(result => {
          proxy.$Notify.success("订单状态已标记为: " + result.data);
          getOrderInfo(selectedOrder.orderStatus);
        })
      }).catch(() => {
      })
    }).catch(() => {
    })
  } else {
    proxy.$Confirm("订单目前的状态为: " + selectedOrder.orderStatus + "是否进入下一步").then(() => {
      toNextOrderStatus(selectedOrder.orderNo, selectedOrder.orderStatus).then(result => {
        proxy.$Notify.success("订单状态已标记为: " + result.data);
        getOrderInfo(selectedOrder.orderStatus);
      })
    }).catch(() => {
    })
  }
}
// 退款取消订单
const refundOrder = (selectedOrder: orderType) => {

  state.selectedOrder = selectedOrder;
  proxy.$Confirm("是否退款取消该订单").then(() => {
    state.showCancelReasonModal = true;
  }).catch(() => {
  })
}
</script>
<style lang='scss' scoped>
</style>