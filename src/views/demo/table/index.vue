<!--  -->
<template>
  <a-table :dataSource="dataSource" :columns="columns" />
</template>

<script>
export default {
  setup() {
    return {
        dataSource: [
          {
            key: '1',
            name: '王大合',
            age: 32,
            address: '西湖区湖底公园1号',
          },
          {
            key: '2',
            name: '王小河',
            age: 42,
            address: '西湖区湖底公园1号',
          },
            {
            key: '1',
            name: '王大合',
            age: 32,
            address: '西湖区湖底公园1号',
          },
          {
            key: '2',
            name: '王小河',
            age: 42,
            address: '西湖区湖底公园1号',
          },
            {
            key: '1',
            name: '王大合',
            age: 32,
            address: '西湖区湖底公园1号',
          },
          {
            key: '2',
            name: '王小河',
            age: 42,
            address: '西湖区湖底公园1号',
          },
            {
            key: '1',
            name: '王大合',
            age: 32,
            address: '西湖区湖底公园1号',
          },
          {
            key: '2',
            name: '王小河',
            age: 42,
            address: '西湖区湖底公园1号',
          },
            {
            key: '1',
            name: '王大合',
            age: 32,
            address: '西湖区湖底公园1号',
          },
          {
            key: '2',
            name: '王小河',
            age: 42,
            address: '西湖区湖底公园1号',
          },
            {
            key: '1',
            name: '王大合',
            age: 32,
            address: '西湖区湖底公园1号',
          },
          {
            key: '2',
            name: '王小河',
            age: 42,
            address: '西湖区湖底公园1号',
          },
        ],

      columns:[
         {
            title: '姓名',
            dataIndex: 'name',
            
          },
          {
            title: '年龄',
            dataIndex: 'age',
            
          },
          {
            title: '住址',
            dataIndex: 'address',
            
          },
      ]
    }
  }
}

</script>
<style lang='less' scoped>
</style>