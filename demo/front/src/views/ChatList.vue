<template>
  <div class="room" id="app" v-cloak>
    <div class="row">
      <div class="col-md-12">
        <h3>내가 모집한 듀오 채팅방</h3>
      </div>
    </div>
    <ul class="list-group">
      <li class="list-group-item list-group-item-action" v-for="item in myRoomList"
          v-on:click="enterRoom(item.roomId, item.participantId)">
        {{ item.userName }} <span class="badge badge-info badge-pill"></span>
      </li>
    </ul>
    <div class="row">
      <div class="col-md-12">
        <h3>내가 지원한 듀오 채팅방</h3>
      </div>
    </div>
    <ul class="list-group">
      <li class="list-group-item list-group-item-action" v-for="item in otherRoomList"
          v-on:click="enterRoom(item.boardParticipantsList[0].roomId, item.boardParticipantsList[0].participantId)">
        {{ item.boardName }} <span class="badge badge-info badge-pill"></span>
      </li>
    </ul>
  </div>

</template>
<script setup>
import {useRouter} from 'vue-router';
import {ref} from 'vue'
import axios from 'axios';
import {useStore} from "vuex";

const router = useRouter();
const store = useStore();
const user_name = ref("");
const myRoomList = ref([]);
const otherRoomList = ref([]);

const myBoardRoom = () => {
  axios.post('http://localhost:8088/chat/my-rooms',store.state.userId, {
    headers: {
      "Content-Type": `application/json`,
    }, auth: {
      username: store.state.username,

      password: "1234"
    }
  }).then(response => {
    myRoomList.value = response.data;
  }).catch(e => {
    console.log(e)
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}

const otherBoardRoom = () => {
  axios.post('http://localhost:8088/chat/other-rooms',store.state.userId, {
    headers: {
      "Content-Type": `application/json`,
    }, auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(response => {
    otherRoomList.value = response.data;
  }).catch(e => {
    console.log(e)
    alert("로그인 요청에 문제가 발생했습니다.")
  });
}

const enterRoom = (roomId, participantId) => {
  localStorage.setItem('wschat.roomId', roomId);
  localStorage.setItem('wschat.participantId', participantId);
  router.push("/chat-detail")
}
myBoardRoom()
otherBoardRoom()
</script>
<style>
</style>
