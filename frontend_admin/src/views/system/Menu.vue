<!--  -->
<template>
  <div>
    <!-- 新增和展开折叠 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">{{ $t('button.wadd')}}</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="info" plain icon="Sort" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="danger" plain icon="Sort" @click="handleAllDelete">批量删除</el-button>
      </el-col>
    </el-row>

    <!-- 菜单表单 -->
    <el-table v-if="refreshTable" class="t1" ref="dragTable" :data="tableData" row-key="menuId" style="width: 100%"
    :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
       @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="名称" width="280">
        <template #default="scope">
          <el-tag :type="scope.row.type === 2 ? 'warning' : (scope.row.type === 1 ? 'success' : '')"
            disable-transitions>
            {{ scope.row.type === 2 ? '权限' :
                (scope.row.type === 1 ? '菜单' : '目录')
            }}</el-tag>
          {{ scope.row.menuName }}
        </template>

      </el-table-column>
      <el-table-column prop="permission" label="权限名称" width="180" />
      <el-table-column prop="icon" label="icon图标" width="180">
        <template #default="scope">
          <el-icon>
            <component :is="scope.row.icon" />
          </el-icon>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="280" className="operationCloumn">
        <template #default="scope">
          <el-button type="primary" text @click="handleDelete(scope.row)">删除</el-button>
          <el-button type="primary" text @click="handleUpdate(scope.row)">修改</el-button>

        </template>
      </el-table-column>
    </el-table>

    <!-- 添加和修改对话框 -->
    <el-dialog :title="state.formTitle" v-model="state.dialogVisible" width="680px" append-to-body>
      <el-form ref="refForm" :model="state.formData" :rules="refRules" label-width="100px">
        <el-row>
          <!-- 菜单类型 -->
          <el-col :span="24">
            <el-form-item label="菜单类型:" prop="Type">
              <el-radio-group v-model="state.formData.type">
                <el-radio :label="0">目录</el-radio>
                <el-radio :label="1">菜单</el-radio>
                <el-radio :label="2">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!-- 节点名称 -->
          <el-col :span="24">
            <el-form-item label="节点名称:" prop="menuName">
              <el-input v-model="state.formData.menuName" placeholder="请输入节点名称" />
            </el-form-item>
          </el-col>
          <!-- 节点权限 -->
          <el-col :span="24">
            <el-form-item label="节点权限:" prop="permission">
              <el-input v-model="state.formData.permission"  placeholder="请输入节点权限" />
            </el-form-item>
          </el-col>
          <!-- 上级节点(只有菜单和按钮拥有) -->
          <el-col :span="24" v-if="state.formData.type != 0">
            <el-form-item label="上级节点:" prop="parentId">
              <el-tree-select v-model="state.formData.parentId" :data="state.menuOptions"
                :props="{ value: 'menuId', label: 'menuName', children: 'children' }" value-key="menuId"
                placeholder="选择上级菜单" check-strictly />
            </el-form-item>
          </el-col>
          <!-- 节点路由 -->
          <el-col :span="24" v-if="state.formData.type != 2">
            <el-form-item label="节点路由:" prop="router">
              <el-input v-model="state.formData.router"  placeholder="请输入节点路由" />
            </el-form-item>
          </el-col>
          <!-- 文件路径 -->
          <el-col :span="24" v-if="state.formData.type != 2">
            <el-form-item label="文件路径:" prop="viewPath">
              <el-input v-model="state.formData.viewPath"  placeholder="请输入节点路径" />
            </el-form-item>
          </el-col>

          <!-- icon图标 -->
          <el-col :span="24" v-if="state.formData.type != 2">

            <el-form-item label="菜单图标" prop="icon">
              <el-popover placement="bottom-start" :width="540" v-model:visible="state.showChooseIcon" trigger="click"
                @show="showSelectIcon">
                <template #reference>
                  <el-input v-model="state.formData.icon" placeholder="点击选择图标" @click="showSelectIcon"
                    v-click-outside="hideSelectIcon" readonly>
                    <template #prefix>

                      <el-icon style="height: 32px;width: 16px;" v-if="state.formData.icon">
                        <component :is="state.formData.icon" />
                      </el-icon>
                      <el-icon v-else style="height: 32px;width: 16px;">
                        <search />
                      </el-icon>
                    </template>


                  </el-input>
                </template>
                <icon-select ref="iconSelectRef" @selected="selected" />
              </el-popover>
            </el-form-item>
          </el-col>
          <!-- i18nmenu名称 -->
          <el-col :span="24">
            <el-form-item label="i18n名称:" prop="title">
              <el-input v-model="state.formData.title"  :min="0" />
            </el-form-item>
          </el-col>
          <!-- 显示排序 -->
          <el-col :span="24">
            <el-form-item label="显示排序:" prop="orderNum">
              <el-input-number v-model="state.formData.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <!-- 是否缓存 -->
          <el-col :span="24" v-if="state.formData.type != 2">
            <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致" placement="top">
                    <el-icon>
                      <question-filled />
                    </el-icon>
                  </el-tooltip>
                  是否缓存
                </span>
              </template>
              <el-radio-group v-model="state.formData.keepalive">
                <el-radio :label='0'>缓存</el-radio>
                <el-radio :label='1'>不缓存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <!-- 菜单状态 -->
          <el-col :span="24" v-if="state.formData.type != 2">
            <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                    <el-icon>
                      <question-filled />
                    </el-icon>
                  </el-tooltip>
                  菜单状态
                </span>
              </template>
              <el-radio-group v-model="state.formData.isShow">
                <el-radio :label='0'>正常</el-radio>
                <el-radio :label='1'>停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang='ts'>

import { getAllMenus, getLevelMenus, addMenu, deleteMenu, updateMenu } from '@/api/system/menu'
import { ref, onMounted, reactive, ComponentInternalInstance, getCurrentInstance,nextTick } from "vue";
import type { ElTable, FormInstance, FormRules } from 'element-plus'
import { ClickOutside as vClickOutside } from 'element-plus'
import IconSelect from "@/components/IconSelect/index.vue";
import 'element-plus/es/components/notification/style/css'
import 'element-plus/es/components/message-box/style/css'

const { proxy } = getCurrentInstance() as ComponentInternalInstance

// 表单数据
const tableData = ref([])
// 多选
const multipleSelection = ref([])
// ref绑定from表单,用于提交效验
const refForm = ref<FormInstance>()
// 控制树状数据展开
const isExpandAll = ref(false);
// 折叠之后激活tab
const refreshTable = ref(true);
// form基本数据
const state = reactive({
  formTitle: '',
  dialogVisible: false,
  showChooseIcon: false,
  menuOptions: [],
  formData: {
    menuId: undefined,
    type: 0,
    menuName: '',
    router: '',
    viewPath: '',
    icon: '',
    keepalive: 1,
    isShow: 0,
    title: '',
    permission: '',
    orderNum: undefined,
    parentId: undefined,
  },

})

const refRules = reactive<FormRules>({
  type: [
    {
      required: true,
      message: '请选择菜单类型',
      trigger: 'blur',
    },
  ], menuName: [
    {
      required: true,
      message: '请输入节点名称',
      trigger: 'blur',
    }, { min: 2, max: 5, message: '最少需要 2 到 5个字符', trigger: 'blur' }
  ], permission: [
    {
      required: true,
      message: '请输入权限',
      trigger: 'blur',
    },
  ], orderNum: [
    {
      required: true,
      message: '请输入排序号',
      trigger: 'blur',
    },
  ]
})
// 绑定ref
const dragTable = ref<InstanceType<typeof ElTable>>()
// 创建sortable实例


const _getAllMenus = () => {
  getAllMenus().then(res => {


    tableData.value = res.data
    
  })
}
onMounted(() => {


  _getAllMenus()
})



// 展开折叠
const toggleExpandAll =()=>{
  refreshTable.value = false;
  isExpandAll.value = !isExpandAll.value;
  nextTick(() => {
    refreshTable.value = true;
  });
}
// 展开icon弹窗
const showSelectIcon = () => {
  state.showChooseIcon = true;
}
// 图标外层点击隐藏下拉列表
const hideSelectIcon = () => {
  state.showChooseIcon = false;
}
// 選擇圖標
const selected = (name: string) => {


  state.formData.icon = name;
  state.showChooseIcon = false;
}

//通过type获取上级节点列表
const getTreeselect = () => {
  state.menuOptions = [];
  getLevelMenus().then(res => {


    state.menuOptions = res.data
    
  })
}
// 新增
const handleAdd = () => {
  reset()
  state.dialogVisible = true
  state.formTitle = '新增菜单'
  state.formData.isShow = 0
  state.formData.keepalive = 1
  getTreeselect()
}
// 修改按钮
const handleUpdate = (selectMenu: API.menuForm) => {
  
  getTreeselect()
  state.formData = JSON.parse(JSON.stringify(selectMenu))
  state.dialogVisible = true
  state.formTitle = '修改菜单'
   
}

// 取消按钮
const cancel = () => {
  state.dialogVisible = false;
  reset();
}

// 重置form表单
const reset = () => {
  state.formData = {
    menuId: undefined,
    type: 0,
    menuName: '',
    router: '',
    viewPath: '',
    icon: '',
    keepalive: 1,
    isShow: 0,
    permission: '',
    title: '',
    orderNum: undefined,
    parentId: undefined,
  }

}
// 多选
const handleSelectionChange = (val:API.menuForm[])=>{
  console.log(val)
  multipleSelection.value = []
  val.forEach(item => {
        multipleSelection.value.push(item.menuId)
  });
  console.log(multipleSelection.value)
}

// 新增或者修改
const submitForm = () => {
  refForm.value.validate(valid => {
    if (valid) {
      if (state.formTitle === '新增菜单') {
        //如果是目录类型,parentId为0
        if (state.formData.type == 0) {
          state.formData.parentId = 0
        }

        addMenu(state.formData).then(res => {
          proxy?.$Notify.success('添加菜单成功')
          state.dialogVisible = false
          _getAllMenus()

        })
      } else if (state.formTitle === '修改菜单') {


        updateMenu(state.formData).then(res => {
          proxy?.$Notify.success('修改菜单成功')
          state.dialogVisible = false
          _getAllMenus()

        })
      }

    }
  })
}

// 删除菜单
const handleDelete = (row: API.menuForm) => {
  proxy.$Confirm("确实删除菜单(或者权限)名为" + row.menuName + "的数据项以及子项吗?").then(() => {
    deleteMenu([row.menuId]).then(() => {
      proxy?.$Notify.success('删除成功')
      _getAllMenus()
    })
  })
}

// 多选删除
const handleAllDelete = ()=>{
  proxy.$Confirm("确实批量删除菜单数据吗?").then(()=>{
    deleteMenu(multipleSelection.value).then(() => {
      proxy?.$Notify.success('删除成功')
      _getAllMenus()
    })
  })
}
</script>
<style lang='scss' scoped>
// 拖拽
.dragClass {
  background: rgba($color: #41c21a, $alpha: 0.5) !important;
}

// 停靠
.ghostClass {
  background: rgba($color: #6cacf5, $alpha: 0.5) !important;
}

// 选择
.chosenClass:hover>td {
  background: rgba($color: #f56c6c, $alpha: 0.5) !important;
}
</style>