<template>
    <div class="content">
        <h1>客服中心</h1>
        <div v-if="!toUser" style="margin: 20px;">暂无客户咨询~~~</div>

        <div class="container">
            <!-- 客户内容 -->
            <el-scrollbar class="info-wrapper" height="400px">

                <div class="buttons" v-for="(item, index) in userClient" :key="index">
                    <el-button :type="toUser == item ? 'primary' : ''" style="margin-right: 2px; width: 95%;"
                        @click="changeUSer(item)">{{ item }}</el-button>
                </div>

            </el-scrollbar>
            <!-- 聊天内容 -->

            <el-scrollbar height="400px" style="border:1px solid #1813131f; " class="cul-wrapper" ref="scrollbarRef">

            <div ref="innerRef">
                <div v-for="(child, index1) in messageList" :key="index1">
                    <div class="cul-date">{{ child.formatDate }}</div>
                    <div :class="child.toUserId != 'service' ? 'msg-service' : 'msg-me'">
                        <div class="msg-text">
                            <div class="msg-text-content">
                                <text>{{ child.body }}</text>
                            </div>
                        </div>
                    </div>
                </div>
            </div>





            </el-scrollbar>
        </div>

        <div class="operation">

            <el-input v-model="msg" :disabled="!toUser"></el-input>
            <el-button @click="sendMesage(msg)" :disabled="!toUser">发送消息</el-button>

        </div>


    </div>
</template>
   
<script setup lang="ts">
import { onBeforeUnmount, reactive, ref, onMounted, nextTick } from 'vue'
import { ElMessage, ElScrollbar } from 'element-plus'
import { getChatList } from '@/api/system/chat'

let messageList = ref([]);
let userClient = reactive([])
const msg = ref('')
const toUser = ref('')

let message = reactive(
    {
        toUserId: "",

        body: "",
        formatDate: '',
        toClient: ''
    }
);

let socket: any = null
const socketUrl = import.meta.env.VITE_SOCKET_URL


const innerRef = ref<HTMLDivElement>()
const scrollbarRef = ref<InstanceType<typeof ElScrollbar>>()

// Websoket连接成功事件
const websocketonopen = (res: string) => {
    ElMessage.success("客服中心连接成功");
};

const getChatRecord = (openId: string) => {
    getChatList(openId).then(res => {


        nextTick(() => {
            messageList.value = res.data
        })

    })
}
// Websoket接收消息事件
const websocketonmessage = (res: any) => {
    console.log(res)

    if (res.data) {
        ElMessage.success("接收到消息：" + JSON.parse(res.data).body);

        let toUserId = JSON.parse(res.data).toUserId
        
        if (toUser.value === toUserId) {
            messageList.value.push(JSON.parse(res.data));
            
        } else {
           
            if (!userClient.includes(toUserId)) {
                userClient.push(toUserId)
            }
            toUser.value = toUserId
            getChatRecord(toUserId)
         
        }

        scrollBottom(400)
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


const scrollBottom = (num:number)=>{
    nextTick(() => {
        if (innerRef.value!.clientHeight > num) {
      scrollbarRef.value!.setScrollTop(innerRef.value!.clientHeight)
        }

    })
}


const sendMesage = (m: string) => {

    ElMessage.success("发送消息：" + m)



    message.body = msg.value;
    message.toUserId = 'service'
    message.toClient = toUser.value
    message.formatDate = ''
    socket.send(JSON.stringify(message), toUser.value);
    msg.value = ''
    //解决message对象只有一个，push修改所有message对象的问题
    messageList.value.push(JSON.parse(JSON.stringify(message)));

    scrollBottom(400)

}
// 创建 websocket 的实例
const createSocket = () => {
    const wsurl = socketUrl + "/chat-server/" + 'service';
    socket = new WebSocket(wsurl);
    socket.onopen = websocketonopen;
    socket.onmessage = websocketonmessage;
    socket.onerror = websocketonerror;
    socket.onclose = websocketclose;
};

createSocket()


// 组件被销毁之前，清空 sock 对象
onBeforeUnmount(() => {
    // 关闭连接
    websocketclose;

    // 销毁 websocket 实例对象
    socket = null;
});

//切换用户
const changeUSer = (user: string) => {
    toUser.value = user
    getChatRecord(user)
}



</script>
   
<style lang="scss" scoped>
.content {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;
    margin-bottom: 8px;
    width: 100%;

}

.container {
    display: flex;
    box-sizing: border-box;
    width: 100%;
    height: 400px;
    background-color: #fff;
}

.info-wrapper {

    width: 20%;
    height: 100%;

    background-color: #fff;
    justify-content: flex-end;
    align-items: flex-start;

    overflow-x: hidden !important;

    .buttons {
        width: 100%;
        height: 100%;
        margin-bottom: 5px;
        .el-button {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
    }
}

.cul-wrapper {
    padding: 0 5px;
    box-sizing: border-box;
    width: 80%;
    background-color: rgb(245, 245, 245);
}

.cul-date {
    padding-top: 5px;
    color: #999999;
    font-size: 16px;
    text-align: center;
}

.msg-me {
    display: flex;
    align-items: flex-end;
    width: 100%;
    margin: 3px 0;
    box-sizing: border-box;
    overflow: hidden;

}

.msg-service {
    display: flex;
    align-items: flex-start;
    width: 100%;
    margin: 3px 0;
    box-sizing: border-box;
    overflow: hidden;
}



.msg-service {
    justify-content: flex-start;
}

.msg-text {
    width: 100%;
}

.msg-me>.msg-text {
    display: flex;
    justify-content: flex-end;
}

.msg-text-content {
    line-height: 25px;
    display: inline-block;
    box-sizing: border-box;
    padding: 5px 5px;
    font-size: 20px;
    border-radius: 5px;

}

.msg-me>.msg-text>.msg-text-content {
    background-color: #fff;
    font-size: 20px;

    border-radius: 15px;
    color: #fff;
    background-color: #FE564B;
}

.msg-service>.msg-text>.msg-text-content {
    background-color: #FFFFFF;
    font-size: 20px;
    border-radius: 15px;
    color: #333333;
}

.operation {
    display: flex;
    flex-wrap: nowrap;

    background-color: #fff;
    padding-top: 10px;
    border-top: 1rpx solid rgba(184, 184, 184, 0.1);
    width: 80%;
    margin-left: auto;
}



.btn {
    width: 116px;
    height: 58px;
    line-height: 58px;
    border-radius: 8px;
    background-color: #FE564B !important;
    font-size: 28px;
    color: #fff !important;
    margin: 0;
}
</style>