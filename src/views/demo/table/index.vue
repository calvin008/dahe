<template>
  <a-table :data-source="data" :columns="columns" :pagination="pagination">
    <!-- 点击下拉搜索 -->
    <template #filterDropdown="{setSelectedKeys, selectedKeys, confirm, clearFilters, column}">
       <div style="padding: 8px">
        <a-input
          ref="searchInput"
          :placeholder="`Search ${column.dataIndex}`"
          :value="selectedKeys[0]"
          style="width: 188px; margin-bottom: 8px; display: block"
          @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
          @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
        />
        <a-button
          type="primary"
          size="small"
          style="width: 90px; margin-right: 8px"
          @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
        >
          <template #icon><SearchOutlined /></template>
          Search
        </a-button>
        <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
          Reset
        </a-button>
      </div>
    </template>
    <!-- 标题列的icon图标 -->
    <template #filterIcon="filtered">
      <SearchOutlined :style="{ color: filtered ? '#108ee9' : undefined }"  />
    </template>
       <template #customRender="{ text, column }">
      <span v-if="searchText && searchedColumn === column.dataIndex">
        <template
          v-for="(fragment, i) in text
            .toString()
            .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
        >
          <mark
            v-if="fragment.toLowerCase() === searchText.toLowerCase()"
            class="highlight"
            :key="i"
          >
            {{ fragment }}
          </mark>
          <template v-else>{{ fragment }}</template>
        </template>
      </span>
      <template v-else>
        {{ text }}
      </template>
    </template>
  </a-table>
</template>
<script>
import { SearchOutlined } from '@ant-design/icons-vue';
import {reactive,ref } from 'vue';

const pagination = {
  showQuickJumper: true,
  pageSize: 5,

  showTotal: (total) => `共 ${total} 条数据`,
  showSizeChanger: true,
  pageSizeOptions: ["10", "15", "30", "40", "50"],
};

const data = [
  {
    key: "1",
    name: "王大合",
    age: 32,
    address: "西湖区湖底公园1号",
  },
  {
    key: "2",
    name: "王小河",
    age: 42,
    address: "西湖区湖底公园1号",
  },
  {
    key: "3",
    name: "王大合",
    age: 32,
    address: "西湖区湖底公园1号",
  },
  {
    key: "4",
    name: "王小河",
    age: 42,
    address: "西湖区湖底公园1号",
  },
  {
    key: "5",
    name: "王大军",
    age: 32,
    address: "西湖区湖底公园1号",
  },
  {
    key: "6",
    name: "王小河",
    age: 42,
    address: "西湖区湖底公园1号",
  },
  {
    key: "7",
    name: "王大合",
    age: 32,
    address: "西湖区湖底公园1号",
  },
  {
    key: "8",
    name: "王小河",
    age: 42,
    address: "西湖区湖底公园1号",
  },
  {
    key: "9",
    name: "王大合",
    age: 32,
    address: "西湖区湖底公园1号",
  },
  {
    key: "10",
    name: "王小河",
    age: 42,
    address: "西湖区湖底公园1号",
  },
  {
    key: "11",
    name: "王大合",
    age: 32,
    address: "西湖区湖底公园1号",
  },
  {
    key: "12",
    name: "王小明",
    age: 42,
    address: "西湖区湖底公园1号",
  },
];

export default {
   components: {
      SearchOutlined
    },

  setup() {
      const state = reactive({
      searchText: '',
      searchedColumn: '',
    });

    const searchInput = ref();
   
    const columns = [
      {
        title: "姓名",
        dataIndex: "name",
        key: "name",
         slots: {
          filterDropdown: 'filterDropdown',
          filterIcon: 'filterIcon',
          customRender: 'customRender',
        },
         onFilter: (value, record) =>
          record.name.toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: visible => {
          if (visible) {
            setTimeout(() => {
              console.log(searchInput.value);
              searchInput.value.focus();
            }, 0);
          }
        },
      },
      {
        title: "年龄",
        dataIndex: "age",
        key: "age",
      },
      {
        title: "住址",
        dataIndex: "address",
        key: "address",
      },
    ];

    const handleSearch = (selectedKeys, confirm, dataIndex) => {
      confirm();
      state.searchText = selectedKeys[0];
      state.searchedColumn = dataIndex;
    };

    const handleReset = clearFilters => {
      clearFilters();
      state.searchText = '';
    };
    return {
      data,
      columns,
      pagination,
      searchText:'',
      searchedColumn:'',
      searchInput,
      handleSearch,
      handleReset
    };
    
  },
};
</script>
<style scoped>
.highlight {
  background-color: rgb(255, 192, 105);
  padding: 0px;
}
</style>