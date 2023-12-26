<!--  -->
<template>
  <div>
    <!--更新的表单弹窗-->
    <el-dialog :title="state.formTitle" v-model="state.formDialogVisible">
      <el-form ref="refForm" :model="state.formData" :rules="rules" size="large" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="state.formData.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="state.formData.phone" placeholder="手机号"></el-input>
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
        :data="state.userAdmins"
        stripe
        :default-sort="{ prop: 'name', order: 'ascending' }"
      >
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
        <el-table-column prop="wxAvatar" label="微信头像">
          <template #default="scope">
            <img
              :src="scope.row.wxAvatar"
              
              style="width: 50px; height: 50px; border-radius: 50%;"
            />
          </template>
        </el-table-column>
        <!-- 账号状态-->
        <el-table-column label="账号状态">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              active-color="green"
              inactive-color="red"
              @change="(value: boolean) => commitStatusChange(value, scope.row)"
            ></el-switch>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button link
          type="primary" size="small" @click="toEdit(scope.row)">编辑</el-button>
            <!-- 禁用删除微信用户 -->
            <!-- <el-button type="text" size="small" @click="toDelete(scope.row.wxOpenid)">删除</el-button> -->
          </template>
        </el-table-column>
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
</template>

<script setup lang='ts'>
import { deleteUserAdmins, getUserAdmins, updateUserAdmin } from "@/api/app/user";
import { reactive,ComponentInternalInstance, getCurrentInstance, ref  } from "vue";
import type { FormInstance, FormRules } from 'element-plus'
import type { WxuserType } from './user'
import 'element-plus/es/components/notification/style/css'
import 'element-plus/es/components/message-box/style/css'
// 引入全局变量
const { proxy } = getCurrentInstance() as ComponentInternalInstance

// 绑定用戶ref
const refForm = ref<FormInstance>()

const state = reactive({
  searchParams: {

    pageNo: 1,
    pageSize: 10,
  },
  total: 0,
  userAdmins: [],
  formDialogVisible: false,
  formTitle: '添加',
  formData: {
    wxOpenid: null, name: null, phone: null, sex: null, wxAvatar: null, status: null
  }
})
// 表单规则
const rules = reactive<FormRules>({
  name: [
    { required: true, message: '姓名不能为空', trigger: 'blur' }
  ], phone: [
    { required: true, message: '手机号不能为空', trigger: 'blur' }
  ]
})

// 获取并且根据(分页)数据
const getUserList = () => {

  getUserAdmins(state.searchParams.pageNo, state.searchParams.pageSize).then(result => {
    state.userAdmins = result.data.records;
    state.total = result.data.total;
  })
}
// setup生命周期执行获取数据
getUserList()

const changePageNo = (pageNo: number) => {
  state.searchParams.pageNo = pageNo;
  getUserList()
}
// 改变页面大小
const changePageSize = (pageSize: number) => {
  state.searchParams.pageSize = pageSize;
  getUserList()
}



// 重置表单
const resetForm = () => {
  state.formData = {
    wxOpenid: null, name: null, phone: null, sex: null, wxAvatar: null, status: null
  }
}

// 编辑用户信息
const toEdit = (userObj: WxuserType) => {
  // 深拷贝一个对象 不然在表格显示的数据会受到印象
  state.formData = JSON.parse(JSON.stringify(userObj));
  state.formDialogVisible = true
  state.formTitle = '更新'
}
// 提交表單
 const handelConfirm = ()=> {
         refForm.value!.validate(valid => {
         
          if (!valid)
            return

          state.formDialogVisible = false;
         if (state.formTitle.startsWith('更新')) {
            updateUserAdmin(state.formData).then(result => {
              proxy.$Notify.success("更新成功");
              getUserList()
            })
          }
        })
      }

 // 删除
    // const toDelete = (openid:string)=>{
        
    //     proxy.$Confirm("提示:是否删除").then(() => {
    //       deleteUserAdmins([openid]).then(() => {
    //         proxy.$Notify.success("删除成功");
    //         getUserList()
    //       })
    //     }).catch(() => {
    //         proxy.$Notify.console.warn("删除失败");
            
    //     })
    //   }

    // 改变用户状态
    const  commitStatusChange = (value:boolean, userAdmin:WxuserType) => {
        proxy.$Confirm(value === false ? '冻结？' : '激活？').then(() => {
          let obj = {
            wxOpenid: userAdmin.wxOpenid,
            status: userAdmin.status
          }
          updateUserAdmin(obj).then(() => {
            proxy.$Notify.success(value === false ? "已冻结" : "已激活")
          }).catch(() => {
            getUserList()
          })
        }).catch(() => {
          getUserList()
        })
      }

</script>
<style lang='scss' scoped>
</style>