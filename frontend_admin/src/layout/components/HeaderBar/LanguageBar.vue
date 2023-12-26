<!--  -->
<template>
  <div>
             <el-dropdown>
            <div>
                <img 
                style="width: 25px; height: 25px; ;"
                src="../../../assets/images/lang.png"  />
            </div>

            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item @click="toLang('chs')" :disabled="getCurrentLang == 'chs'">中文</el-dropdown-item>
                    <el-dropdown-item @click="toLang('en')" :disabled="getCurrentLang == 'en'">English</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
  </div>
</template>

<script setup lang='ts'>
import { computed } from 'vue';
import { useI18n } from 'vue-i18n';
const i18n = useI18n()

const getCurrentLang = computed(()=>{
    return i18n.locale.value
})
const toLang = (lang:string)=>{
    i18n.locale.value  = lang
    localStorage.setItem('lang',lang)
}
</script>
<style lang='scss' scoped>
</style>
