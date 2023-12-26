<!--  -->
<template>
  <div>
    <!-- 添加类目按钮 -->
    <div style="text-align: left; margin: 5px 10px">
      <el-button type="primary" @click="toAdd">{{ $t('button.wadd') }}</el-button>
    </div>
    <!-- 添加修改类目模态框 -->
    <el-dialog :title="state.formTitle" v-model="state.formDialogVisible">
      <el-form
        ref="refForm"
        :model="state.formData"
        :rules="rules"
        size="large"
        label-width="100px"
      >
        <el-form-item label="商品类别" prop="name">
          <el-input v-model="state.formData.name" placeholder="输入商品类别名称"></el-input>
        </el-form-item>
        <el-form-item label="显示顺序" prop="displayOrder">
          <el-input v-model="state.formData.displayOrder" placeholder="输入显示顺序"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="handelConfirm">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--列表-->
    <div style="margin: 0px 10px;text-align: left;">
      <el-table
        :data="state.goodsCategoryAdmins"
        stripe
        :default-sort="{ prop: 'displayOrder', order: 'ascending' }"
      >
        <el-table-column prop="name" label="商品类别名称" sortable></el-table-column>
        <el-table-column prop="displayOrder" label="显示顺序" sortable></el-table-column>

        <el-table-column label="是否显示该类商品">
          <template #default="scope">
            <el-switch
              v-model="scope.row.showStatus"
              active-color="green"
              inactive-color="red"
              @change="(value:boolean) => commitStatusChange(value, scope.row.name)"
            ></el-switch>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-row>
              <el-button type="primary" size="small" :icon="Edit" circle @click="toEdit(scope.row)"></el-button>
              <el-button
                type="danger"
                size="small"
                :icon="Delete"
                circle
                @click="toDelete(scope.row.name)"
              ></el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup lang='ts'>
import {
    Delete,
    Edit,
  } from '@element-plus/icons-vue'
import 'element-plus/es/components/notification/style/css'
import 'element-plus/es/components/message-box/style/css'
import {
  addGoodsCategoryAdmin,
  deleteGoodsCategoryAdmins,
  getAllGoodsCategoryAdmins,
  updateGoodsCategoryAdmin,
  updateGoodsCategoryShowStatus
} from "@/api/app/goodsCategory";
import { reactive, ref, ComponentInternalInstance, getCurrentInstance } from "vue";
import type { FormInstance, FormRules } from 'element-plus'
import type { categoryType } from './categoryType';
// 引入全局变量
const { proxy } = getCurrentInstance() as ComponentInternalInstance


const refForm = ref<FormInstance>()
const state = reactive({
  goodsCategoryAdmins: [],
  formTitle: '',
  formDialogVisible: false,
  formData: {
    oldname: '',
    name: '',
    displayOrder: null,
    showStatus: 1
  },

})
// from表单规则
const rules = reactive<FormRules>({
  name: [
    { required: true, message: '不能为空', trigger: 'blur' }
  ], displayOrder: [
    { required: true, message: '显示顺序不能为空', trigger: 'blur' }
  ], showStatus: [
    { required: true, message: '是否显示不能为空', trigger: 'blur' }
  ]
})
// 获取商品类目数据
const getAllGoodsCategory = () => {
  getAllGoodsCategoryAdmins().then(res => {
    console.log(res);

    state.goodsCategoryAdmins = res.data
  })
}

// 初始化执行
getAllGoodsCategory()

// 点击添加商品种类
const toAdd = () => {
  state.formTitle = '添加类目'
  state.formDialogVisible = true
}

// dialog重置
const resetForm = () => {
  // 手动重置 不然数据被绑定在toEdit时深克隆出来的对象
  state.formData = {
    oldname: '', name: '', displayOrder: null, showStatus: 1
  }
}

// 添加商品提交数据
const handelConfirm = () => {

  refForm.value!.validate(vaild => {
    if (!vaild)
      return

    state.formDialogVisible = false;
    if (state.formTitle.startsWith('添加类目')) {
      addGoodsCategoryAdmin(state.formData).then(() => {
        proxy?.$Notify.success('添加成功')
        getAllGoodsCategory()
      })
    } else if (state.formTitle.startsWith('更新类目')) {
      updateGoodsCategoryAdmin(state.formData.oldname, state.formData).then(result => {
        console.log(result);
        if (result.data === 1) {
          proxy?.$Notify.success('修改成功')
          getAllGoodsCategory()
        }

      })
    }
  })
}

// 修改类目
const toEdit = (obj: categoryType) => {
  // 深拷贝一个对象 不然在表格显示的数据会受到印象
  state.formData = JSON.parse(JSON.stringify(obj));
  state.formDialogVisible = true
  state.formData.oldname = obj.name
  state.formTitle = '更新类目'
}

// 删除类目
const toDelete = (name: string) => {
  proxy?.$Confirm("提示!对应的商品也会一并删除，确定删除吗？").then(() => {
    deleteGoodsCategoryAdmins(name).then(res => {
      console.log(res);

      proxy?.$Notify.success("删除成功");
      getAllGoodsCategory()
    })
  }).catch(() => {
  })
}

// 修改类目状态
const commitStatusChange = (value:boolean, goodsCategoryAdmin:string)=>{
  proxy?.$Confirm(value === false ? '是否隐藏该类商品？' : '是否显示该类商品？').then(() => {
          updateGoodsCategoryShowStatus(goodsCategoryAdmin).then(() => {
            proxy?.$Notify.success(value === false ? "已隐藏" : "已开启显示")
          }).catch(() => {
            getAllGoodsCategory()
          })
        }).catch(() => {
          getAllGoodsCategory()
        })
}
</script>
<style lang='scss' scoped>
</style>