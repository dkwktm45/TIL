<template>
  <footer class="footer text-center">
  <span :class="alarmStyle" >
    <div class="list-group ">
      <div class="float-start list-group-item  ">
        당신에게 도착한 메시지입니다!
        <svg v-on:click="notified" xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-x-circle float-end" viewBox="0 0 16 16">
          <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
          <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
        </svg>
      </div>
      <div class="test">
        <div v-if="alarmList.length != 0" v-for="alarm in alarmList">
          <button v-if="alarm.alarmType == 'DUO'" v-on:click="enterRoom(alarm.id)" type="button" class="list-group-item list-group-item-action text-primary">{{ alarm.senderName }}님에게 도착한 메시지가 있습니다.
            <span class="badge bg-primary rounded-pill">{{alarm.count}}</span>
          </button>
          <button v-else-if="alarm.value.alarmType == 'FRIEND'" type="button" class="list-group-item list-group-item-action text-danger">{{ alarm.value.senderName }}님에게 도착한 메시지가 있습니다.</button>
          <button v-else-if="alarm.value.alarmType == 'AGREE'" type="button" class="list-group-item list-group-item-action text-dark">
            {{ alarm.value.senderName }} 친구요청이 왔습니다.
            <button type="button" class="list-group-item list-group-item-action text-dark"  v-on:click="agreeFriend(alarm)" >수락</button>
            <button type="button" class="list-group-item list-group-item-action text-dark"  v-on:click="refuseFriend(alarm)" >거절</button>
          </button>
          <button v-else type="button" v-on:click="deleteNoti(alarm)" class="list-group-item list-group-item-action text-dark">
            {{ alarm.value.senderName }} 친구요청이 왔습니다.
          </button>
        </div>
        <div v-else>
          <button type="button" class="list-group-item list-group-item-action disabled text-primary ">
            <h4>도착한 알림이 없습니다.</h4>
          </button>
        </div>
        <div>
        </div>
      </div>
    </div>
  </span>
    <button v-on:click="alertList" type="button" :class="'float-end rounded-5 btn btn-primary '+button ">
      <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
        <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"/>
      </svg>
      <div class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
        !
      </div>
    </button>
  </footer >
</template>
<script setup>
import { useStore } from "vuex";
import {reactive, ref} from 'vue'
import {useRouter} from "vue-router/dist/vue-router";
import {dataJSON} from "../api/boardList.js"
import axios from "axios";
const router = useRouter();
const store = useStore();
const name = store.state.username;
const alarmStyle = ref("visually-hidden")
const button = ref('')
let alarmList = ref([]);
let recevieAlarm = null;
const source = new EventSource("http://localhost:8088/receive/notify/" +name,{withCredentials : true});

source.onmessage = function(e) {
  var data = JSON.parse(e.data)
  data.count = 1
  recvAlarm(data)
};
const data = dataJSON;

source.onerror = (e) => {
  router.push("/login")
};

const recvAlarm = (alarm) => {
  var data = ref(alarm)
  var findUser = alarmList.value.find(info => info.senderName === data.value.senderName);
  if(findUser){
    findUser.count++
  }else{
    alarmList.value.push({alarmType: alarm.alarmType, senderId: alarm.senderId
      , id: alarm.id, senderName : alarm.senderName , count : alarm.count})
  }
}

const alertList = () =>{
    alarmStyle.value = "visible"
    button.value = "d-none"
}

const notified = () =>{
  alarmStyle.value = "visually-hidden-focusable"
  button.value = "d-print"
}

const findAll = () => {
  var params = new URLSearchParams();
  params.append('name', name);
  axios.post(`http://localhost:8088/find/`,params, {
     auth: {
      username: "happydaddy",
      password: "1234"
    }
  }).then(response => {
    if(response.data.length == 0){
      alarmList.value = null;
    }else {
      response.data.forEach(info => info.count =1)
      recevieAlarm = response.data.reduce(function(acc, current) {
        if (acc.findIndex(({ senderName }) => senderName === current.senderName) === -1) {
          acc.push(current);
        }else if(acc.find(x => x.senderName === current.senderName)){
          acc.find(x => x.senderName === current.senderName).count++
        }
        return acc;
      }, []);
      recevieAlarm.forEach(info => recvAlarm(info))
    }
  }).catch(e => {
    console.log(e)
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}
const enterRoom = (roomId) => {
  var params = new URLSearchParams();
  params.append('roomId', roomId);
  params.append('userName', store.state.username);
  axios.post('http://localhost:8088/participants/one',params, {
     auth: {
      username: "happydaddy",
      password: "1234"
    }
  }).then(res => {
    var id = alarmList.value.findIndex(function (alarm) {
      return alarm.id === roomId;
    })
    alarmList.value.pop(alarmList.value.slice(id,1))
    localStorage.setItem('wschat.roomId', res.data.roomId);
    localStorage.setItem('wschat.participantId', res.data.participantId);

    router.push("/chat-detail")

  }).catch(e => {
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}

const refuseFriend = (alarm) => {
  var params = new URLSearchParams();
  params.append('roomId', alarm);
  params.append('userName', store.state.username);
  axios.post('http://localhost:8088/friend/one',params, {
    auth: {
      username: "happydaddy",
      password: "1234"
    }
  }).then(res => {
    localStorage.setItem('wschat.roomId', res.data.roomId);
    localStorage.setItem('wschat.participantId', res.data.participantId);

    router.push("/chat-detail")

  }).catch(e => {
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}

const agreeFriend = (alarm) => {
  var data = JSON.stringify(alarm.value);
  axios.put('http://localhost:8088/friend/plus', data, {
    headers: {
      "Content-Type": `application/json`,
    },auth: {
      username: "happydaddy",
      password: "1234"
    }
  }).then(res => {
    console.log("성공")
  }).catch(e => {
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}
const deleteNoti = (alarm) => {
  var params = new URLSearchParams();
  params.append('roomId', alarm);
  params.append('userName', store.state.username);
  axios.post('http://localhost:8088/friend/one',params, {
    auth: {
      username: "happydaddy",
      password: "1234"
    }
  }).then(res => {
    localStorage.setItem('wschat.roomId', res.data.roomId);
    localStorage.setItem('wschat.participantId', res.data.participantId);

    router.push("/chat-detail")

  }).catch(e => {
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}

findAll()
</script>

<style scoped>
.footer{
  height: 100px;
  position: fixed;
  bottom: 0;
  right: 4%;
}
.list-group{
  position: fixed;
  bottom: 6.5%;
  width: 33%;
  right: 4%;
}

.test{
  max-height: 300px;
  overflow-y: scroll;
}
.test::-webkit-scrollbar {
  width: 10px;
}
.test::-webkit-scrollbar-thumb {
  background-color: #2f3542;
  border-radius: 10px;
  background-clip: padding-box;
  border: 2px solid transparent;
}
.test::-webkit-scrollbar-track {
  background-color: grey;
  border-radius: 10px;
  box-shadow: inset 0px 0px 5px white;
}
</style>