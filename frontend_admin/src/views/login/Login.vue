<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { getCode, login } from '@/api/Auth'
import { useRouter } from 'vue-router';

import { User, Lock } from '@element-plus/icons-vue'
// 引入pinia
import { authStore } from '@/pinia/authStore'
const useAuthStore = authStore()




const router = useRouter()
// 验证码
const codeUrl = ref<string>()
// form表单数据
const loginForm = reactive({
  username: 'admin',
  password: '123456',
  uuid: '',
  verifyCode: ''
})

// rules表单校验
const loginRules = reactive({
  username: [
    {
      required: true,
      message: '不能为空,请输入username',
      trigger: 'blur'
    },
    {
      pattern: /^[a-zA_Z0-9]{2,10}$/,
      message: '请输入2到10的字母或者数字',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 15,
      message: '请输入3到15的字母或者数字',
      trigger: 'blur'
    },
  ],
  password: [
    {
      required: true,
      message: '不能为空,请输入密码',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 15,
      message: '请输入3到15的字母或者数字',
      trigger: 'blur'
    },{
      whitespace: true,
      message: '不能为空格'
    }
  ], verifyCode: [
    {
      required: true,
      message: '不能为空',
      trigger: 'blur'
    }, {
      whitespace: true,
      message: '不能为空格'
    }
  ]
})

// 获取验证码
const getValidaCode = () => {
  getCode().then(res => {


    codeUrl.value = res.data.image
    loginForm.uuid = res.data.uuid
  })
}

onMounted(() => {
  getValidaCode()

})
// token登录
// const handleToken = () => {
//   const token = localStorage.getItem('token')
//   if (token != null) {
//     store.dispatch('authStore/loginByToken', token)
//   }
// }

// 登录
const handleLogin = () => {
  // store.dispatch('authStore/login', loginForm)
  useAuthStore.login(loginForm)
}


</script>

<template>
  <div class="login-container">
    <!-- 背景vedio -->
    <video poster="@/assets/login/1.jpg" loop autoplay muted>
      <source src="@/assets/login/Particles.mp4" />
    </video>

    <div class="login-form">
      <!-- 标题 -->
      <header>
        <img src="@/assets/logo.png" />
        <h3>vue3-admin</h3>
      </header>
      <el-form :model="loginForm" :rules="loginRules">
        <el-form-item prop="username">
          <el-input placeholder="username" v-model="loginForm.username" type="text" :prefix-icon="User"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="password" v-model="loginForm.password" type="password" :prefix-icon="Lock"></el-input>
        </el-form-item>
        <el-form-item prop="verifyCode">
          <el-input placeholder="验证码" v-model="loginForm.verifyCode" type="text"
            style="width: 40%; display:inline-block;  "></el-input>
          <div style="margin-left: 15px; display:inline-block; height:40px">
            <img :src="codeUrl" @click="getValidaCode"
              style="margin-bottom: -12px;width:100%;height:100%;object-fit:cover" />
          </div>
        </el-form-item>
        <el-form-item style=" border: none;
      ">
         
          <el-button type="primary" style="width: 100%;" @click="handleLogin">{{ $t('button.wLogin') }}</el-button>
         
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style lang="scss">
// 隐藏滚动条
::-webkit-scrollbar {
  width: 0 !important;
}

::-webkit-scrollbar {
  width: 0 !important;
  height: 0;
}

.login-container {
  height: 100vh;
  width: 100%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;

  video {
    position: absolute;
    /* Vertical and Horizontal center*/
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    object-fit: fill;
    z-index: -1;
  }

  .login-form {
    display: flex;
    width: 380px;
    height: 380px;
    padding: 4vh;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background: url("@/assets/login/login_form.png");
    background-size: 100% 100%;
    border-radius: 10px;
    box-shadow: 0 2px 8px 0 rgba(247, 243, 243, 0.06);
    opacity: "0.2";
    
    header {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-bottom: 20px;

      img {
        display: inline-block;
        width: 40px;
      }

      h3 {
        margin-bottom: 0;
        font-size: 18px;
        color: #fff;
        text-align: center;
      }
    }

    .el-input__wrapper {
       background-color: transparent !important;
      input {
       
         
        
     

        padding: 12px 5px 12px 5px;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $loginBg inset !important;
          -webkit-text-fill-color: #fff !important;
        }
      }
    }

    .el-form-item {
      display: flex;
      flex-wrap: nowrap;
      border-radius: 5px;
      color: #454545;
      
    }

  }
 
}

</style>