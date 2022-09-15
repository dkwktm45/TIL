<template>
  <ol class="list-group ">
    <div class="fw-bold">
      <h3>모집 공고
        <button class="btn btn-primary" type="button" @click="createBoard">모집 개설</button>
      </h3>
    </div>
    <li v-for="data in boardList" v-on:click="enterBoard(data.boardId)"
        class="list-group-item d-flex justify-content-between align-items-start">
      <div class="ms-2 me-auto">
        <div class="fw-bold">모집 공고</div>
        닉네임 : {{ data.boardName }}
        <span class="btn btn-outline-danger">Delete</span>
      </div>
    </li>
  </ol>
</template>

<script setup>
import axios from "axios";
import {ref} from 'vue'
import {useStore} from "vuex";
import {useRouter} from "vue-router/dist/vue-router";

const store = useStore();
const boardList = ref([])
const router = useRouter();
const modal = ref(false);

const myPositions = ref([]);
const otherPositions = ref([]);
const createBoard = (e) => {
  modal.value = true;
  axios.post('http://localhost:8088/board-create', {boardName: store.state.username, user: {userId : store.state.userId}}, {
    auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(response => {
    console.log("게시판 성공")
    getBoardList()
  })
}

const getBoardList = () => {
  axios.post('http://localhost:8088/board-all', {}, {
    auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(
      response => {
        console.log("불러오기 성공")
        boardList.value = response.data
      }
  )
      .catch(response => {
        alert("게시판 불러오기 실패하였습니다.");
      });
}

const enterBoard = (boardId) => {
  console.log("들어갈려는 모집 공고" + boardId)
  axios.post(`http://localhost:8088/chat/room?boardId=${boardId}&userName=${store.state.username}&userId=${store.state.userId}`, {}, {
    headers: {
      'Content-Type': 'application/json',
    }, auth: {
      username: store.state.username,
      password: "1234"
    }
  }).then(res => {
    localStorage.setItem('wschat.roomId', res.data.participants.roomId);
    localStorage.setItem('wschat.participantId', res.data.participants.participantId);
    router.push("/chat-detail")
  })
}
getBoardList()
</script>

<style scoped>

</style>