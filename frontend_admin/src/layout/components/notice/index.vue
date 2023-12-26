<template>
  <div class="order-notify">
    <el-dropdown ref="dropdown" class="my-dropdown">
      <span class="el-dropdown-link" @click="showClick">
        <Bell style="width: 2em; height: 2em; color: #000; margin-right: 8px" />
        <el-badge :value="messageList.length" class="order-badge"></el-badge>
      </span>
      <template #dropdown>
        <div class="menu-header">
          <span>您有新的订单来,请查收</span>
          <el-button style="" size="small" type="primary" link  @click="confirmReceiveAllMessage()">全部已读
              </el-button>
        </div>
      <el-dropdown-menu>
       
        <el-empty v-if="messageList.length === 0" description="暂无消息" :image-size="60" />
        <span v-else class="menu-content">
          <div  v-if="messageList && messageList.length">

            <el-card v-for="message in messageList" :key="message.orderNo" style="font-size: 15px;">
              <div class="message-top">
              <span class="order-no">{{ `取餐号[${message.verifyNum}]` }}</span>
              <el-button style="" size="small" type="primary" link @click="confirmReceiveMessage(message.orderNo)">已收到
              </el-button>
            </div>
            <div class="message-content">
              <div class="message-type">{{ message.takeType }}</div>
              <div class="message-desc">{{  message.orderNo }}</div>
            </div>
            </el-card>
          </div>
        </span>

      </el-dropdown-menu>
    </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import {resentOrderMessage,confirmReceiveOrderMessage,receiveAllOrderMessage} from '@/api/app/messageApi'
import { ref,onUnmounted } from 'vue';
import { DropdownInstance, ElMessage } from 'element-plus'
import SpeakTTS from 'speak-tts';
import { Bell } from '@element-plus/icons';
import {authStore} from '@/pinia/authStore'

interface IOptionSpeech {
  lang: string;
  volume?: number;
  rate?: number;
  pitch?: number;
  outputFormat?: string;
  preferXHR?: boolean;
}
const useAuthStore = authStore()
const dropdown = ref<DropdownInstance>()
let socket: any = null
const messageList = ref([]); // 新增订单列表
const socketUrl = import.meta.env.VITE_SOCKET_URL


function showClick() {
  resentOrderMessage().then(res=>{
    messageList.value = res.data
  })
  if (!dropdown.value) return
  dropdown.value.handleOpen()
}
// Websoket连接成功事件
const websocketonopen = () => {
  ElMessage.success("订单接收连接成功");
};
// Websoket接收消息事件
const websocketonmessage = (res: any) => {
  console.log("接收消息成功")
  let data;
  try {
    data = JSON.parse(res.data);
  } catch (e) {
    console.error("从服务器接收到的不是有效的 JSON 字符串：" + res.data);
    return;
  }
  // 监听服务器消息，这里假设服务器返回的消息格式
  messageList.value.push(...data)
  showClick()
  
  console.log(messageList.value.length)
  if (messageList.value.length > 0) {


    ElMessage.success("有新的订单" + messageList.value[0].orderNo);
    voiceTips(messageList.value[0].verifyNum)


  }
};
// Websoket连接错误事件
const websocketonerror = () => {
  ElMessage.error("连接错误");
};
// Websoket断开事件
const websocketclose = () => {
  
  ElMessage.error("断开连接");
};
// 连接WebSocket服务器
// 创建 websocket 的实例
const createSocket = () => {
  console.log(useAuthStore.userInfo)
  const wsurl = socketUrl + "/order-server/" + useAuthStore.userInfo.id;
  socket = new WebSocket(wsurl);
  socket.onopen = websocketonopen;
  socket.onmessage = websocketonmessage;
  socket.onerror = websocketonerror;
  socket.onclose = websocketclose;
};

createSocket()

const voiceTips = async (verifyNum: string) => {
  let txt = '您有新的订单来了,取餐号为' + verifyNum + ',请注意查看'
  const speakTTS = new SpeakTTS();
  
  const optionSpeech: IOptionSpeech = {
    // 设置语音朗读的语言，支持多种语言，比如中文、英文、日文等
    lang: 'zh-CN',
    // 设置语音朗读的音量，范围为 0 ~ 1
    volume: 1,
    // 设置语音朗读的语速，数字越大朗读速度越快，最小值为 0.1
    rate: 1,
    // 设置语音朗读的音高，范围为 0.1 ~ 2
    pitch: 1.5,
    // 设置音频输出的音频格式，支持 mp3、wav、ogg、pcm 等多种格式
    outputFormat: 'audio/mp3',
    // 设置音频输出的流式传输方式，如果 preferXHR 设置为 false，则默认使用 fetch 的方式传输流式音频
    preferXHR: false,
  }
  
  await speakTTS.init(optionSpeech).then(() => {
    // 朗读语音
    speakTTS.speak({ text: txt });
  }).catch((err:any) => {
     console.log(err)
  })
}

const getRecentMessage = ()=>{
        resentOrderMessage().then(result => {
          if (result.data.length > messageList.value.length)
            voiceTips(result.data[result.data.length - 1].verifyNum)
          messageList.value = result.data;
        }).catch(result => {
          console.log(result.data)
        })
      }

      getRecentMessage()

 // 确认收到消息
 const confirmReceiveMessage = (orderNo:string)=>{
        
        confirmReceiveOrderMessage(orderNo).then(result => {
          getRecentMessage()
         
        }).catch(result => {
          console.log(result.data)
        })
      }

// 全部已读
const confirmReceiveAllMessage = ()=>{
  receiveAllOrderMessage().then(res=>{
    getRecentMessage()
  })
}

onUnmounted(() => {
  websocketclose();
});

</script>

<style scoped>
.order-notify {
  position: relative;
  display: inline-block;
}

.order-badge {
  position: absolute;
  top: -5px;
  right: -10px;
}

.my-dropdown .el-dropdown-menu {
  overflow: visible;
}

.menu-content {
  padding: 10px;
  text-align: center;
  max-height: 400px; /* 设置最大高度，可以根据实际需要调整 */
  overflow-y: auto; /* 内容超出最大高度时，显示滚动条 */
  margin: 10px 5px 5px 10px;
}
.menu-header {
  font-size: 20px;
  margin: 15px;
  color: darkgray;
  text-align: center;
}


.message-item {
  margin: 10px 0;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.message-top {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.order-no {
  font-size: 16px;
  font-weight: 600;
}

.message-content {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.message-type {
  font-size: 14px;
  font-weight: 600;
  margin-right: 8px;
}

.message-desc {
  font-size: 14px;
  color: gray;
}

</style>