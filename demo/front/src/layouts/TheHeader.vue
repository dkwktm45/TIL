<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container-fluid " >
        <a class="navbar-brand" href="#">GYM CODING</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto">
            <li class="nav-item" v-if="store.state.username == null">
              <router-link class="nav-link" active-class="active" to="/login">login</router-link>
            </li>
            <li class="nav-item" v-show="store.state.username != null">
              <router-link class="nav-link" active-class="active" to="/board-list">게시판 목록</router-link>
            </li>
            <li class="nav-item" v-show="store.state.username != null">
              <router-link class="nav-link" active-class="active" to="/chat-list">채팅방 목록</router-link>
            </li>
            <li class="nav-item" v-show="store.state.username != null">
              <router-link class="nav-link" active-class="active" to="/chat-detail">채팅방</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>
<script setup>
import {useStore} from "vuex";
import router from "../router";

const store = useStore();
const authUser = () =>{
  if(store.state.username ==null){
    router.push("/login")
  }
}
authUser()
</script>
<!-- eslint-disable prettier/prettier -->

<style lang="scss" scoped></style>
