<template>
    <div class="logo">
        <img src="@/assets/logo.png" alt="">
        <h2 v-show="!collapsed" class="title">vue3-admin</h2>
    </div>
</template>

<script setup lang="ts">
    defineProps({
  collapsed:{
    type:Boolean
  }
})

</script>

<style lang="scss" scoped>
.logo {
    display: flex;
    align-items: center;
    padding-left: 14px;
    width: 200px;
    height: 64px;
    overflow: hidden;
    white-space: nowrap;
    img {
        height: 32px;
        margin-right: 8px;
    }

    .title {
        font-size: 20px;
        color: white;
        margin-bottom: 0;
    }
}

</style>