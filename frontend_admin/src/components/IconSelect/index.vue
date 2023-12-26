<!--  -->
<template>
    <div>
        <div class="icon-list">
            <div v-for="(name, index) in icons" :index="index" :key="index" @click="selectedIcon(name)">
                <component :is="name" style="width: 2rem;height: 2rem">
                </component>
            </div>
        </div>

    </div>
</template>

<script setup lang='ts'>
import {ref} from 'vue'
import * as ElIcons from '@element-plus/icons'
const icons = ref([])
  const getData = () => {
      
      for (const name in ElIcons) {
        icons.value.push(name)
      }
      return icons
    }

  getData() 
const emit = defineEmits(['selected']);
const selectedIcon = (name:string) => {
    emit('selected', name)
    document.body.click()
}

</script>
<style lang='scss' scoped>
.icon-list {
    width: 100%;
    height: 300px;
    overflow-y: scroll;
    
    
    div {
        height: 20px;
        line-height: 30px;
        margin-bottom: -5px;
        cursor: pointer;
        width: 10%;
        padding: 1rem;
        float: left;
    }

    div:hover {
        background-color: palevioletred;
        color: white;
    }
}
</style>