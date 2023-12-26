<script setup lang="ts">
import { ref, reactive, onBeforeUnmount, computed ,ComponentInternalInstance,getCurrentInstance} from "vue";
import {resentOrderMessage, confirmReceiveOrderMessage} from "@/api/app/messageApi"
import SpeakTts from 'speak-tts'
import {authStore} from '@/pinia/authStore'
import { ElMessage} from 'element-plus'

const { proxy } = getCurrentInstance() as ComponentInternalInstance
const useAuthStore = authStore()
let socket: any = null
// 语音播放实例
const speech: any = ref(null)
speech.value = new SpeakTts();
//初始化语音播报的一些参数，这个方法要在语音播报之前执行
speech.value.init({
 
  lang: 'zh-CN',
  rate: 0.8
 
  
})


let list = reactive([]);

let noticesNum = computed(() => {
  return list.length;
})


// Websoket连接成功事件
const websocketonopen = (res: string) => {
  ElMessage.success("WebSocket连接成功");
};
// Websoket接收消息事件
const websocketonmessage = (res: any) => {
  console.log("接收消息成功")
  console.log(res)
  list = JSON.parse(res.data)
  if (list.length > 0) {
   
    
    ElMessage.success("有新的订单"+ list[0].orderNo);
    voiceTips(list[0].verifyNum)
    console.log(list)





  }
};
// Websoket连接错误事件
const websocketonerror = (res: string) => {
  ElMessage.error("连接错误");
};
// Websoket断开事件
const websocketclose = (res: string) => {
  ElMessage.error("断开连接");
};

const voiceTips = (verifyNum:string)=>{
        let txt = '您有新的订单来了,取餐号' + verifyNum + ',请注意查看'
      
        speech.value.speak({ text: txt })
      }
// 创建 websocket 的实例
const createSocket = () => {
 
   
  const wsurl = "ws://localhost:9002/order-server/" + useAuthStore.userInfo.id;
  socket = new WebSocket(wsurl);
  socket.onopen = websocketonopen;
  socket.onmessage = websocketonmessage;
  socket.onerror = websocketonerror;
  socket.onclose = websocketclose;
};


// 组件被销毁之前，清空 sock 对象
onBeforeUnmount(() => {
  // 关闭连接
  websocketclose;

  // 销毁 websocket 实例对象
  socket = null;
});

createSocket()


const getRecentMessage = ()=>{
        console.log("获取未完成订单")
        resentOrderMessage().then(result => {
          if (result.data.length > list.length)
            voiceTips(result.data[result.data.length - 1].verifyNum)
          list = result.data;
        }).catch(result => {
          console.log(result.data)
        })
      }
 // 确认收到消息
 const confirmReceiveMessage = (orderNo:string)=>{
        
        confirmReceiveOrderMessage(orderNo).then(result => {
          proxy?.$Notify.success(result.data);
          getRecentMessage();
        }).catch(result => {
          console.log(result.data)
        })
      }
</script>

<template>
  <el-dropdown trigger="click" placement="bottom-end" >
    <span class="dropdown-badge navbar-bg-hover select-none">
      <el-badge :value="noticesNum" :max="999">

        <img style="width: 25px; height: 25px; ;" src="../../../../assets/images/notify.png" />

      </el-badge>
    </span>
    <template #dropdown>
        <div style="font-size: 25px; margin: 15px; color: darkgray;">
          <span>您有新的订单来了,请注意查看</span>
        </div>
      <el-dropdown-menu>
       
        <el-empty v-if="list.length === 0" description="暂无消息" :image-size="60" />
        <span v-else>
          <el-card class="box-card" v-if="list && list.length">

            <div v-for="message in list" :key="message.orderNo" style="font-size: 15px;">
              订单号[{{ message.orderNo }}] [{{ message.takeType }}][{{ message.verifyNum }}]
              <el-button style="" size="default" 
                @click="confirmReceiveMessage(message.orderNo)">收到</el-button>
              <br>
            </div>
          </el-card>
        </span>

      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<style lang="scss" scoped>
.dropdown-badge {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 48px;
  margin-right: 10px;
  cursor: pointer;


}

.dropdown-tabs {
  .noticeList-container {
    padding: 15px 24px 0;
  }

  :deep(.el-tabs__header) {
    margin: 0;
  }

  :deep(.el-tabs__nav-wrap)::after {
    height: 1px;
  }

  :deep(.el-tabs__nav-wrap) {
    padding: 0 36px;
  }
}
</style>
