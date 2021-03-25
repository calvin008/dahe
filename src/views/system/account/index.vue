<!--  -->
<template>
  <div>
    <a-button
      type="primary"
      class="editable-add-btn"
      @click="handleAdd"
      style="margin-bottom: 8px"
      >Add</a-button
    >
    <a-modal v-model:visible="visible" title="添加用户" @ok="handleOk">
      <a-form :model="formState">
        <a-form-item label="用户名称" required>
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="用户年龄" required>
          <a-input v-model:value="formState.age" />
        </a-form-item>
        <a-form-item label="组织权限" required>
          <a-select
            v-model:value="formState.group"
            placeholder="please select your group"
          >
            <a-select-option value="admin">admin</a-select-option>
            <a-select-option value="guest">guest</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
    <a-table bordered :data-source="dataSource" :columns="columns">
      <!-- 文本修改 -->
      <template
        v-for="col in ['name', 'age', 'group']"
        #[col]="{ text, record }"
        :key="col"
      >
        <div>
          <a-input
            v-if="editableData[record.key]"
            v-model:value="editableData[record.key][col]"
            style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>

      <!-- 修改删除 -->
      <template #action="{ record }">
        <!-- 修改 -->
        <a-row>
          <a-col>
            <span v-if="editableData[record.key]">
              <a @click="save(record.key)">Save</a>
              <a-divider type="vertical" />
              <a-popconfirm
                title="Sure to cancel?"
                @confirm="cancel(record.key)"
              >
                <a>Cancel</a>
              </a-popconfirm>
            </span>
            <span v-else>
              <a @click="edit(record.key)">Edit</a>
            </span>
          </a-col>
          <a-divider type="vertical" />
          <!-- 删除 -->
          <a-col>
            <a-popconfirm
              v-if="data.length"
              title="确定要删除吗?"
              @confirm="onDelete(record.key)"
            >
              <a>Delete</a>
            </a-popconfirm>
          </a-col>
        </a-row>
      </template>
    </a-table>
  </div>
</template>

<script>
import { reactive, toRefs, ref } from "vue";
import { cloneDeep } from "lodash-es";

const data = [
  {
    key: "1",
    name: "王大合",
    age: 32,
    group: "admin",
  },
  {
    key: "2",
    name: "王小河",
    age: 42,
    group: "editor",
  },
  {
    key: "3",
    name: "王大军",
    age: 32,
    group: "test",
  },
  {
    key: "4",
    name: "王小明",
    age: 42,
    group: "guest",
  },
];

const columns = [
  {
    title: "姓名",
    dataIndex: "name",
    key: "name",
    slots: { customRender: "name" },
  },
  {
    title: "年龄",
    dataIndex: "age",
    key: "age",
    slots: { customRender: "age" },
  },
  {
    title: "组织权限",
    dataIndex: "group",
    key: "group",
    slots: { customRender: "group" },
  },
  {
    title: "action",
    dataIndex: "action",
    key: "action",
    slots: { customRender: "action" },
  },
];

export default {
  setup() {
    const dataSource = ref(data);

    const state = reactive({
      visible: false,
      editableData: [],
      formState: {},
    });

    const edit = (key) => {
      state.editableData[key] = cloneDeep(
        dataSource.value.filter((item) => key === item.key)[0]
      );
    };

    const save = (key) => {
      Object.assign(
        dataSource.value.filter((item) => key === item.key)[0],
        state.editableData[key]
      );
      delete state.editableData[key];
    };
    const cancel = (key) => {
      delete state.editableData[key];
    };

    const onDelete = (key) => {
      dataSource.value = dataSource.value.filter((item) => item.key !== key);
    };

    const handleAdd = () => {
      state.visible = true;
    };

    const handleOk = () => {
      dataSource.value.push(state.formState)
      state.visible = false
    }

    return {
      ...toRefs(state),
      data,
      columns,
      dataSource,
      edit,
      save,
      cancel,
      onDelete,
      handleAdd,
      handleOk
    };
  },
};
</script>
<style lang='less' scoped>
.editable-add-btn {
  margin-bottom: 8px;
  margin-left: 8px;
}
</style>