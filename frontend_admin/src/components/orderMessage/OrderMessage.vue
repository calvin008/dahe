<template>
  <div>
    <!--达到要求弹出div-->
    <el-card class="box-card" v-if="messageList && messageList.length">
      <div style="font-size: 25px">
        <span>您有新的订单来了,请注意查看</span>
      </div>
      <div v-for="message in messageList" :key="message.orderNo" style="font-size: 15px;">
        订单号[{{message.orderNo}}] [{{message.address}}]
        <el-button style="" size="default" link
                type="primary" @click="confirmReceiveMessage(message.orderNo)">收到</el-button>
        <br>
      </div>
    </el-card>
  </div>
</template>

<!--最新订单消息弹窗-->
<script setup lang='ts'>
  import {resentOrderMessage, confirmReceiveOrderMessage} from "@/api/app/messageApi"
import { ComponentInternalInstance, getCurrentInstance, onMounted, reactive } from "vue";
import { ElMessage} from 'element-plus'
const { proxy } = getCurrentInstance() as ComponentInternalInstance
const socketUrl = import.meta.env.VITE_SOCKET_URL
    
  
let socket: any = null
let  messageList= reactive<any>([])
      
    

// Websoket连接成功事件
const websocketonopen = (res: string) => {
  ElMessage.success("WebSocket连接成功");
};
// Websoket接收消息事件
const websocketonmessage = (res: any) => {
  console.log("接收消息成功")
  console.log(res)
  messageList = JSON.parse(res.data)
  if (messageList.length > 0) {
   
    
    ElMessage.success("有新的订单"+ messageList[0].orderNo);
    voiceTips(messageList[0].verifyNum)
    console.log(messageList)





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


// 创建 websocket 的实例
const createSocket = () => {
 
   
  const wsurl = socketUrl + "/order-server/" + 'service';
  socket = new WebSocket(wsurl);
  socket.onopen = websocketonopen;
  socket.onmessage = websocketonmessage;
  socket.onerror = websocketonerror;
  socket.onclose = websocketclose;
};

      onMounted(()=>{
        getMessage()
      })
      
      // 语音提醒来了新订单
      const voiceTips = (address:string)=>{
        let txt = '您有新的订单来了,' + address + ',请注意查看'
        new Audio('http://tts.baidu.com/text2audio?cuid=baiduid&lan=zh&ctp=1&pdt=12&tex=' + txt).play();
      }
      const getMessage = ()=>{
    
        // setInterval(() => {
        //   console.log('获取订单')
        //   getRecentMessage();
        // }, 15000);
      }
      const getRecentMessage = ()=>{
        resentOrderMessage().then(result => {
          if (result.data.length > messageList.length)
            voiceTips(result.data[result.data.length - 1].address)
          messageList = result.data;
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

<style>

  .box-card {
    position: fixed;
    top: 70vh;
    right: 1vw;
    width: 600px;
    height: 29vh;
    z-index: 100;
    background: #ff9966 !important;
  }
</style>
