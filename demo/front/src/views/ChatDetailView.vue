<template>
  <div class="row">
    <div class="col-md-5">
      <h3>{{ board.boardName }}님 채팅방</h3>
    </div>
  </div>
  <div class="input-group">
    <div class="input-group-prepend">
      <label class="input-group-text">내용</label>
    </div>
    <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage('TALK')">
    <div class="input-group-append">
      <button class="btn btn-primary" type="button" @click="sendMessage('TALK')">보내기</button>
    </div>
  </div>
  <ul class="list-group">
    <li class="list-group-item" v-for="message in messages">
      {{ message.sender }} - {{ message.message }}
    </li>
  </ul>
</template>

<script setup>
import {ref} from 'vue'
import sockjs from "sockjs-client/dist/sockjs"
import Stomp from 'webstomp-client'
import axios from 'axios';
import {useStore} from "vuex";
import {onBeforeRouteLeave} from 'vue-router'

const store = useStore();
const MasterName = ref('');
const board = ref();
const roomId = localStorage.getItem('wschat.roomId');
const participantId = localStorage.getItem('wschat.participantId');
const message = ref('')
const messages = ref([])
let token = ref('')
let chatSocket = new sockjs('http://localhost:8088/ws-stomp')
const ws = Stomp.over(chatSocket)

const findMessage = () => {
  var params = new URLSearchParams();
  params.append('roomId', roomId);
  axios.post('http://localhost:8088/messages-all', params, {
    auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(res => {
    res.data.forEach(info =>
        messages.value.unshift({"type": info.type, "sender": info.sender, "message": info.message})
    )
  })
}

/*const deleteAll = (boardId, participantId) => {
  axios.delete('http://localhost:8088/participants/delete', {
    headers: {
      username: store.state.username,
      password        : "1234",
      "Content-Type": "application/json",
    },
    data   : {
      boardId      : boardId,
      participantId: participantId
    },      withCredentials: true,

  }).then(res => {
    alert("듀오 결성!")
  }).catch(e =>{
    alert(e)
  })
}*/

// 메세지 보내기!
const sendMessage = (type) => {
  ws.send("/pub/chat/message", JSON.stringify({
    type   : type,
    roomId : roomId,
    message: message.value
  }), {"token": token});
  message.value = '';
}

// board에 대한 수락버튼 및 다양한거 버튼 활성화를 위함
const findBoard = (participantId) => {
  axios.post("http://localhost:8088/board-id", participantId, {
    headers: {
      "Content-Type": `application/json`,
    }, auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(res => {
    MasterName.value = res.data.userName
    board.value = res.data.board;
  }).catch(e => {
    alert(e)
  })
}

// 연결이 끊어졌을 때
onBeforeRouteLeave(() => {
  console.log("라우터 이동 alarm")
  ws.disconnect(() => {
    console.log("connect 끊음")
  }, {roomId: roomId})
})
const friendPlus = (name) => {
  var params = new URLSearchParams();
  params.append('roomId', roomId);
  params.append('name', name);
  axios.put("http://localhost:8088/friend/agree",params,{
    auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(res =>{
    console.log("성공")
  }).catch(e => {
    alert(e)
  })
}
const duoEnd = (name) =>{

}
// 메세지를 받아서 html 처리
const recvMessage = (recv) => {
  messages.value.push({"type": recv.type, "sender": recv.sender, "message": recv.message})
}
// 소켓 연결
const socketConnect = () => {
  axios.get('http://localhost:8088/user', {
    auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(response => {
    token = response.data.token;
    ws.connect({token: token}, function (frame) {
      ws.subscribe("/sub/chat/room/" + roomId, function (message) {
        var recv = JSON.parse(message.body);
        recvMessage(recv);
      });
    }, function (error) {
      alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
      ws.disconnect(() => {
        console.log("connect 끊음")
      }, {roomId: roomId})
      location.href = "/chat-list";
    });
  });
}
findMessage()
findBoard(participantId)
socketConnect()
window.addEventListener('beforeunload', (event) => {
  // 표준에 따라 기본 동작 방지
  event.preventDefault();
  // Chrome에서는 returnValue 설정이 필요함
  event.returnValue = '';
});
</script>
<style scoped>
</style>