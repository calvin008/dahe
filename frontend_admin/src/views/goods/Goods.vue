<!--  -->
<template>
  <div>
    <div style="text-align: left; margin: 5px 10px">
      <el-button v-btn="'system:goods:goodsInfo:add'" @click="toAdd" type="primary">{{ $t('button.wadd') }}</el-button>
    </div>

    <!--增加或更新商品的表单弹窗-->
    <el-dialog :title="state.formTitle" v-model="state.formDialogVisible">
      <el-form ref="refForm" :model="state.formData" :rules="rules" size="large" label-width="150px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="state.formData.name" placeholder></el-input>
        </el-form-item>
        <el-form-item label="选择商品种类">
          <el-select v-model="state.formData.goodsCategoryName" placeholder="选择商品种类" size="small" style="float: left">
            <el-option v-for="item in state.goodsCategoryList" :key="item.name" :label="item.name" :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-upload class="avatar-uploader"  :show-file-list="false"
              :before-upload="beforeAvatarUpload">
              <img v-if="state.formData.image" :src="state.formData.image" style="width: 60px; height: 60px" alt="商品图片" />
              <div v-else style="border:1px solid lightgray; color: lightgray">+上传图片</div>
            </el-upload>
        </el-form-item>
        <el-form-item label="显示次序" prop="displayOrder">
          <el-input v-model="state.formData.displayOrder" placeholder="显示次序" type="number"></el-input>
        </el-form-item>
        <el-form-item label="默认价格(单位: 分)" prop="defaultPrice">
          <el-input v-model="state.formData.defaultPrice" placeholder="默认价格" type="number"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="state.formData.description" placeholder="商品描述, 最多100字"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm">重置</el-button>
          <el-button type="primary" @click="handelConfirm">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--增加或更新商品属性的表单弹窗-->
    <el-dialog :title="property.goodsPropertyFormTitle" v-model="property.goodsPropertyFormDialogVisible">
      <el-form ref="goodsPropertyForm" :model="property.goodsPropertyFormData" :rules="propertyRules" size="large"
        label-width="180px">
        <el-form-item label="属性类型" prop="category">
          <el-select v-model="property.goodsPropertyFormData.category" placeholder="选择商品种类" size="mini"
            style="float: left">
            <el-option v-for="item in property.goodsPropertyCategories" :key="item" :label="item" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="属性值" prop="propertyOption">
          <el-input v-model="property.goodsPropertyFormData.propertyOption" placeholder="比如: 大杯，加冰，常温..."></el-input>
        </el-form-item>
        <el-form-item label="商品默认价格(单位: 分)" prop="realPrice">
          <el-input v-model="property.goodsPropertyFormData.rebasePrice" placeholder="属性为'大小'时才要填，其他情况不填或填0"
            type="number"></el-input>
        </el-form-item>
        <el-form-item label="额外价格(单位: 分)" prop="realPrice">
          <el-input v-model="property.goodsPropertyFormData.extraPrice" placeholder="没有就不填，一般在'加料'的时候填" type="number">
          </el-input>
        </el-form-item>
        <el-form-item label="商品库存" prop="stock">
          <el-input v-model="property.goodsPropertyFormData.stock" placeholder="当前商品规格库存" type="number"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetGoodsPropertyForm">重置</el-button>
          <el-button type="primary" @click="handelGoodsPropertyFormConfirm">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 商品列表 -->
    <div style="margin: 0px 10px;text-align: left;">
      <el-table :data="state.goodsAdmins" stripe :expand-row-keys="state.expands"
        :row-key="(row: any) => { return row.id; }" @expand-change="expandSelect"
        :default-sort="{ prop: 'goodsCategoryName', order: 'ascending' }">
        <!--商品的属性列表-->
        <el-table-column type="expand">
          <template #default="goodsScope">
            <el-button size="small" type="primary" @click="toAddGoodsProperty(goodsScope.row.id)" round>添加商品属性
            </el-button>
            <el-table :data="goodsScope.row.goodsPropertyList" stripe>
              <el-table-column prop="category" label="属性类型" sortable></el-table-column>
              <el-table-column prop="propertyOption" label="属性值"></el-table-column>
              <el-table-column prop="isDefault" label="是否为默认属性" sortable>
                <template #default="goodsPropertyScope">
                  <el-switch v-model="goodsPropertyScope.row.isDefault" active-color="green" inactive-color="red"
                    :disabled="goodsPropertyScope.row.isDefault || goodsPropertyScope.row.category === '加料'"
                    @change="(value: boolean) => setDefaultProperty(value, goodsPropertyScope.row)"></el-switch>
                </template>
              </el-table-column>
              <el-table-column prop="rebasePrice" label="商品默认价格" sortable>
                <template #default="goodsPropertyScope">{{ goodsPropertyScope.row.rebasePrice ? '￥' +
                    goodsPropertyScope.row.rebasePrice / 100 : '无'
                }}</template>
              </el-table-column>
              <el-table-column prop="extraPrice" label="额外价格" sortable>
                <template #default="goodsPropertyScope">{{ goodsPropertyScope.row.extraPrice ? '￥' +
                    goodsPropertyScope.row.extraPrice / 100 : '无'
                }}</template>
              </el-table-column>
              <el-table-column prop="stock" label="库存" sortable></el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="goodsPropertyScope">
                  <el-button @click="toEditProperty(goodsPropertyScope.row)" type="primary" size="small" icon="edit"
                    circle></el-button>
                  <el-button @click="deleteProperty(goodsPropertyScope.row)" type="danger" size="small" icon="delete"
                    circle></el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>

        <el-table-column prop="name" label="商品名称" sortable></el-table-column>
        <el-table-column prop="goodsCategoryName" label="商品种类" sortable></el-table-column>
        <el-table-column prop="displayOrder" label="显示顺序" sortable></el-table-column>
        <el-table-column prop="defaultPrice" label="默认价格" sortable>
          <template #default="scope">{{ '￥' + scope.row.defaultPrice / 100 }}</template>
        </el-table-column>

        <!--上传商品图片-->
        <el-table-column prop="image" label="商品图片">
          <template #default="scope">
            
            <img v-if="scope.row.image" :src="scope.row.image" style="width: 60px; height: 60px" alt="商品图片" />
            
            
          </template>
        </el-table-column>
        <el-table-column prop="description" label="商品描述"></el-table-column>

        <el-table-column label="是否上架">
          <template #default="scope">
            <el-switch v-model="scope.row.isSell" active-color="green" inactive-color="red"
              @change="(value: boolean) => commitStatusChange(value, scope.row)"></el-switch>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="130">
          <template #default="scope">
            <el-row>
              <el-button v-btn="'system:goods:goodsInfo:update'" type="primary" size="small" :icon="Edit"
                @click="toEdit(scope.row)" circle></el-button>
              <el-button type="danger" v-btn="'system:goods:goodsInfo:delete'" size="small" :icon="Delete" @click="toDelete(scope.row.id)"
                
                 circle></el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <el-row style="float: right">
        <el-pagination @current-change="changePageNo" @size-change="changePageSize"
          :current-page.sync="page.searchParams.pageNo" :page-sizes="[5, 10, 20, 30, 50, 100]"
          :page-size.sync="page.searchParams.pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="page.total"></el-pagination>
      </el-row>
    </div>
  </div>
</template>

<script setup lang='ts'>
import {
  Delete,
  Edit,
} from '@element-plus/icons-vue'
import {
  addGoodsAdmin,
  addGoodsProperty,
  deleteGoodsAdmins,
  deleteGoodsProperty,
  getGoodsAdminById,
  getGoodsAdmins,
  setDefaultPropertyOfGoods,
  updateGoodsAdmin,
  updateGoodsProperty,
  updateSellStatus,
  updateGoodsAdminImage
} from "@/api/app/goods";
import { cosUpload } from "@/api/system/cos"
import { getAllGoodsCategoryAdmins } from "@/api/app/goodsCategory"
import { reactive, ref, ComponentInternalInstance, getCurrentInstance } from 'vue';
import 'element-plus/es/components/notification/style/css'
import 'element-plus/es/components/message-box/style/css'
import type { FormInstance, FormRules } from 'element-plus'
import type { goodsType, goodsPropertyType } from './goodType'
import type { UploadProps } from 'element-plus'
import COS from 'cos-js-sdk-v5'




const beforeAvatarUpload: UploadProps['beforeUpload'] = (file:File) => {
  // 要开始做文件上传的检查了
  // 文件类型 文件大小
  const types = ['image/jpeg', 'image/gif', 'image/bmp', 'image/png']
  if (!types.includes(file.type)) {
    proxy.$Alert('上传图片只能是 JPG、GIF、BMP、PNG 格式!')
    return false
  }
  //  检查大小
  const maxSize = 1 * 1024 * 1024
  if (maxSize < file.size) {
    proxy.$Alert('图片大小最大不能超过5M')
    return false
  } else {
    // const cos = new COS({
    //   SecretId: '',
    //   SecretKey: ''
    // }) // 实例化的包 已经具有了上传的能力 可以上传到该账号里面的存储桶了
   
    
   
    // cos.putObject({
    //   //   储存桶名字，存储桶所在地域，上传文件对象是必须修改的。上传后的文件名看个人需求
    //   Bucket: '', /* 储存桶名字 必须 */
    //   Region: 'ap-guangzhou', /* 存储桶所在地域，必须字段 */
    //   Key: `/mall/${file.name}`, /* 上传后的文件名 自定义即可 必须 */
    //   StorageClass: 'STANDARD',
    //   Body: file, // 上传文件对象
     
    //   // 上传的进度---要用箭头函数，不然this指向不是Vue实例
    //   onProgress: (progressData) => {
    //     console.log(progressData)
    //     // onProgress是上传图片完成才会触发的方法，progressData包含上传完的返回数据。percent是上传          完成进度，从0开始到1
    //   }
    // }, (err, data) => {
    //   if (err) {
    //     return proxy.$Alert('图片上传失败')
    //   }
    //   state.formData.image = 'https://' + data.Location

    // })
    
    //调用后端接口上传图片
    const formData = new FormData();
    formData.append('file', file);
    
     updateGoodsAdminImage(formData).then(res=>{
      state.formData.image = res.data
     })
  }


}



// 引入全局变量
const { proxy } = getCurrentInstance() as ComponentInternalInstance
// 绑定商品ref
const refForm = ref<FormInstance>()
// 绑定属性ref
const goodsPropertyForm = ref<FormInstance>()
// 响应式数据
const state = reactive({
  goodsAdmins: [],//表单数据
  expands: [], // 展开的行
  formTitle: '',
  formDialogVisible: false,
  formData: {
    id: null,
    goodsCategoryName: '',
    name: '',
    displayOrder: 0,
    defaultPrice: 0.0,
    isSell: null,
    image: null,
    description: ''
  },
  goodsCategoryList: [
    {
      name: ''
    }
  ],
})
// 商品属性数据
const property = reactive({
  goodsPropertyFormDialogVisible: false,
  goodsPropertyFormTitle: "添加商品属性",
  currentGoodsId: null, // 操作当前的商品商品属性所需要的goodsId
  goodsPropertyCategories: ['大小', '温度', '甜度', '口味', '加料'], // 商品属性的类型，在数据库就定死了
  goodsPropertyFormData: {
    id: null,
    goodsId: null,
    category: '', // 商品属性类型: 温度','甜度','大小','口味'
    propertyOption: '',
    isDefault: null,
    rebasePrice: null,
    extraPrice: null,
    stock: null
  },
})
// 分页数据
const page = reactive({
  searchParams: {
    searchParam1: null,
    pageNo: 1,
    pageSize: 10,
  },
  total: 0,
})
// 提交商品规则
const rules = reactive<FormRules>({
  name: [
    { required: true, message: '商品种类不能为空', trigger: 'blur' }
  ], goodsCategoryName: [
    { required: true, message: '商品种类不能为空', trigger: 'blur' }
  ], displayOrder: [
    { required: true, message: '显示次序不能为空', trigger: 'blur' }
  ], defaultPrice: [
    { required: true, message: '默认价格不能为空', trigger: 'blur' }
  ], isSell: [
    { required: true, message: '是否在卖不能为空', trigger: 'blur' }
  ], image: [
    { required: true, message: '商品图片不能为空', trigger: 'blur' }
  ]
})

// 商品属性规则
const propertyRules = reactive<FormRules>({
  category: [
    { required: true, message: '商品属性类型不能为空', trigger: 'blur' }
  ], propertyOption: [
    { required: true, message: '商品的属性值不能为空', trigger: 'blur' }
  ]
})

// 获取类目数据
const getAllGoodsCategory = () => {

  getAllGoodsCategoryAdmins().then(result => {
    state.goodsCategoryList = result.data;
    console.log(state.goodsCategoryList);

  })
}

// 获取商品数据
const getGoods = () => {

  getGoodsAdmins(page.searchParams.pageNo, page.searchParams.pageSize).then(result => {
    // TODO 将获取的商品的图片加个随机值，不然上传了图片后不能马上刷新
    result.data.records.forEach((goods: any) => {
      if (goods.image)
        goods.image = goods.image + "?random=" + Math.floor(Math.random() * 10000);
    })
    state.goodsAdmins = result.data.records;
    console.log(state.goodsAdmins)
    page.total = result.data.total;
  })
}
getAllGoodsCategory()
getGoods()

// 控制只有一行被展开
const expandSelect = (row: any, expandedRows: any) => { // 当行展开时

  if (expandedRows.length) {
    state.expands = []
    if (row) {
      state.expands.push(row.id)
    }
  } else {
    state.expands = []
  }
}

// 添加商品
const toAdd = () => {
  resetForm()
  state.formDialogVisible = true
  state.formTitle = '添加'
}

// 重置商品数据表单
const resetForm = () => {
  // 手动重置 不然数据被绑定在toEdit时深克隆出来的对象
  state.formData = {
    id: null,
    goodsCategoryName: '',
    name: '',
    displayOrder: 0,
    defaultPrice: 0.0,
    isSell: 1,
    image: null,
    description: ''
  }
}

// 提交更新商品的表单
const handelConfirm = () => {
  refForm.value!.validate(valid => {
    console.log(state.formTitle)
    if (!valid)
      return

    state.formDialogVisible = false;
    if (state.formTitle.startsWith('添加')) {
      addGoodsAdmin(state.formData).then(result => {
        proxy?.$Notify.success("添加成功");
        getGoods()
      })
    } else if (state.formTitle.startsWith('更新')) {
      updateGoodsAdmin(state.formData).then(result => {
        proxy?.$Notify.success("更新成功");
        getGoods()
      })
    }
  })
}
// 修改商品
const toEdit = (selectedGoodsAdmin: goodsType) => {
  // 深拷贝一个对象 不然在表格显示的数据会受到印象
  state.formData = JSON.parse(JSON.stringify(selectedGoodsAdmin));
  state.formDialogVisible = true
  state.formTitle = '更新'
}

const UploadImage = (selectedGoodsAdmin: goodsType)=>{
  // 深拷贝一个对象 不然在表格显示的数据会受到印象
  state.formData = JSON.parse(JSON.stringify(selectedGoodsAdmin));
  state.formDialogVisible = true
  state.formTitle = '更新'
}

// 删除商品
const toDelete = (id: number) => {

  proxy?.$Confirm("提示:是否删除").then(() => {
    deleteGoodsAdmins([id]).then(res => {
      console.log(res);
      
      if(res.data == 1) {
           proxy?.$Notify.success("删除成功");
      getGoods();
      }
    })
  }).catch(() => {
  })
}

// 添加商品属性
const toAddGoodsProperty = (goodsId: number) => {
  property.currentGoodsId = goodsId
  resetGoodsPropertyForm()
  property.goodsPropertyFormDialogVisible = true
  property.goodsPropertyFormTitle = '添加商品属性'
}

const resetGoodsPropertyForm = () => {
  // 手动重置 不然数据被绑定在toEdit时深克隆出来的对象
  property.goodsPropertyFormData = {
    id: null,
    goodsId: null,
    category: '', // 商品属性类型: 温度','甜度','大小','口味'
    propertyOption: '',
    isDefault: 0,
    rebasePrice: null,
    extraPrice: null,
    stock: null
  }
}
const handelGoodsPropertyFormConfirm = () => {

  goodsPropertyForm.value.validate(valid => {

    if (!valid)
      return

    property.goodsPropertyFormDialogVisible = false;
    if (property.goodsPropertyFormTitle.startsWith('添加')) {
      property.goodsPropertyFormData.goodsId = property.currentGoodsId;
      addGoodsProperty(property.goodsPropertyFormData).then(result => {
        proxy.$Notify.success("添加成功");
        getGoods()
      })
    } else if (property.goodsPropertyFormTitle.startsWith('更新')) {
      updateGoodsProperty(property.goodsPropertyFormData).then(result => {
        proxy.$Notify.success("更新成功");
        getGoods()
      })
    }
  })
}

// 设置默认商品属性
const setDefaultProperty = (value: boolean, goodsProperty: goodsPropertyType) => {
  property.currentGoodsId = goodsProperty.goodsId;
  if (!value) { // 如果是先取消默认属性则直接返回，通过设置其他为默认属性就可以间接取消了
    goodsProperty.isDefault = true;
    return;
  }

  proxy.$Confirm('是否设置为默认属性？').then(() => {
    setDefaultPropertyOfGoods(goodsProperty.id).then(() => {
      proxy.$Notify.success("设置成功")
      getGoods()


    }).catch(() => {
      goodsProperty.isDefault = !goodsProperty.isDefault;
    })
  }).catch(() => {
    goodsProperty.isDefault = !goodsProperty.isDefault;
  })
}

// 编辑商品属性
const toEditProperty = (selectedGoodsProperty: goodsPropertyType) => {
  property.currentGoodsId = selectedGoodsProperty.goodsId
  // 深拷贝一个对象 不然在表格显示的数据会受到印象
  property.goodsPropertyFormData = JSON.parse(JSON.stringify(selectedGoodsProperty));
  property.goodsPropertyFormDialogVisible = true
  property.goodsPropertyFormTitle = '更新商品属性'
}

const deleteProperty = (goodsProperty: goodsPropertyType) => {

  property.currentGoodsId = goodsProperty.goodsId

  proxy.$Confirm("提示:是否删除该商品属性").then(() => {
    deleteGoodsProperty(goodsProperty.id).then(() => {
      proxy.$Notify.success("删除成功");
      getGoods()
    })
  }).catch(() => {
  })

}
// 上架或下架商品
const commitStatusChange = (value: boolean, goodsAdmin: goodsType) => {
  proxy.$Confirm(value === false ? '是否下架商品？' : '是否上架商品？').then(() => {
    updateSellStatus(goodsAdmin.id).then(res => {
      if (res.data === 1) {
        proxy.$Notify.success(value === true ? "已上架" : "已下架")
      }


    }).catch(() => {
      getGoods()
    })
  }).catch(() => {
    getGoods()
  })
}
// 切换页数
const changePageNo = (pageNo: number) => {
  page.searchParams.pageNo = pageNo;
  getGoods()
}

//  改变页面数据条数
const changePageSize = (pageSize: number) => {
  page.searchParams.pageSize = pageSize;
  getGoods()
}
</script>
<style lang='scss' scoped>
</style>
