<!--  -->
<template>
  <div>
    <div style="text-align: left; margin: 5px 10px;">
      <el-button type="primary" @click="toAddStore">{{ $t('button.wadd') }}</el-button>
    </div>
    <!-- 新增和修改弹窗 -->
    <el-dialog :title="state.formTitle" v-model="state.storeFormDialogVisible">
      <el-form ref="refForm" :model="state.storeFormData" :rules="rules" label-width="100px">
        <el-form-item label="门店名" prop="storeName">
          <el-input v-model="state.storeFormData.storeName" placeholder="请输入门店名"></el-input>
        </el-form-item>
        <el-form-item label="门店城市名" prop="storeName">
          <el-input v-model="state.storeFormData.storeCity" placeholder="请输入城市名"></el-input>
        </el-form-item>
        <el-form-item label="门店经度" prop="storeName">
          <el-input v-model="state.storeFormData.storeLongitude" placeholder="请输入经度"></el-input>
        </el-form-item>
        <el-form-item label="门店纬度" prop="storeName">
          <el-input v-model="state.storeFormData.storeLatitude" placeholder="请输入纬度"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="handelConfirm">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 商品列表 -->
    <el-table
      :data="state.storeList"
      stripe
      :default-sort="{ prop: 'createTime', order: 'ascending' }"
    >
      <el-table-column prop="storeId" label="门店id" sortable></el-table-column>
      <el-table-column prop="storeName" label="门店名称" sortable></el-table-column>
      <el-table-column prop="storeCity" label="门店城市" sortable></el-table-column>
      <el-table-column prop="storeLongitude" label="门店经度" sortable></el-table-column>
      <el-table-column prop="storeLatitude" label="门店纬度" sortable></el-table-column>
      <el-table-column prop="createTime" label="门店信息创建时间" sortable></el-table-column>
      <el-table-column prop="updateTime" label="门店信息修改时间" sortable></el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-row>
            <el-button type="primary" size="small" :icon="Edit" circle @click="toEdit(scope.row)"></el-button>
            <el-button
              type="danger"
              size="small"
              :icon="Delete"
              circle
              @click="toDelete(scope.row.storeId)"
            ></el-button>
          </el-row>
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
</template>

<script setup lang='ts'>
import {
  Delete,
  Edit,
} from '@element-plus/icons-vue'
import 'element-plus/es/components/notification/style/css'
import 'element-plus/es/components/message-box/style/css'
import { getStoreAdmins, addStoreAdmin, updateStoreInfo, deleteStoreInfo } from '@/api/app/store'
import { reactive, ref, ComponentInternalInstance, getCurrentInstance } from 'vue';
import type { FormInstance, FormRules } from 'element-plus'
import type { storeForm, storeFullType } from './storeType';
// 引入全局变量
const { proxy } = getCurrentInstance() as ComponentInternalInstance

// 绑定门店ref
const refForm = ref<FormInstance>()

const state = reactive({
  storeList: [],
  formTitle: "",
  storeFormDialogVisible: false,//显示窗口
  storeFormData: {
    storeId: null,
    storeName: "",
    storeCity: "",
    storeLongitude: null,
    storeLatitude: null
  }
})

const page = reactive({
  searchParams: {
    pageSize: 5,
    pageNo: 1
  },
  total: 0
})

const rules = reactive<FormRules>({
  storeName: [
    { required: true, message: '门店信息不能为空', trigger: 'blur' }
  ], storeCity: [
    { required: true, message: '门店名称不能为空', trigger: 'blur' }
  ], storeLongitude: [
    { required: true, message: '门店经度不能为空', trigger: 'blur' }
  ], storeLatitude: [
    { required: true, message: '门店纬度不能为空', trigger: 'blur' }
  ]
})
// 获取数据
const getStoreList = () => {

  getStoreAdmins(page.searchParams.pageNo, page.searchParams.pageSize).then(res => {
    console.log(res);

    state.storeList = res.data.records;
    page.total = res.data.total
  })
}

// 页面初始化执行获取数据
getStoreList()

// 切换页数
const changePageNo = (pageNo: number) => {
  page.searchParams.pageNo = pageNo;
  getStoreList()
}

//  改变页面数据条数
const changePageSize = (pageSize: number) => {
  page.searchParams.pageSize = pageSize;
  getStoreList()
}

// 重置门店数据表单
const resetForm = () => {
  // 手动重置 不然数据被绑定在toEdit时深克隆出来的对象
  state.storeFormData = {
    storeId: null,
    storeName: "",
    storeCity: "",
    storeLongitude: null,
    storeLatitude: null
  }
}

// 添加门店
const toAddStore = () => {
  resetForm()
  state.storeFormDialogVisible = true
  state.formTitle = '添加'
}

// 提交更新门店的表单
const handelConfirm = () => {
  refForm.value!.validate(valid => {
    console.log(state.formTitle)
    if (!valid)
      return

    state.storeFormDialogVisible = false;
    if (state.formTitle.startsWith('添加')) {
      addStoreAdmin(state.storeFormData).then(res => {
        if(res.data) {
          proxy?.$Notify.success("添加成功")
        getStoreList()
        }
        
        
      })

    } else if (state.formTitle.startsWith('更新')) {
      updateStoreInfo(state.storeFormData.storeId, state.storeFormData).then(res => {
        if(res.data) {
          proxy?.$Notify.success("更新成功")
        getStoreList()
        }
      })

    }
  })
}

// 修改类目
const toEdit = (obj: storeFullType) => {
  // 深拷贝一个对象 不然在表格显示的数据会受到印象
  state.storeFormData = JSON.parse(JSON.stringify(obj));
  state.storeFormDialogVisible = true

  state.formTitle = '更新'
}

const toDelete = (storeId: number) => {
  proxy?.$Confirm("提示!对应的商品也会一并删除，确定删除吗？").then(() => {
    deleteStoreInfo(storeId).then(res => {
      
      
      if (res.data === 0) {
        proxy?.$Notify.success("删除成功");
        getStoreList()
      }

    })
  }).catch(() => {
  })
}
</script>
<style lang='scss' scoped>
</style>