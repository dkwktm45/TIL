import { defineStore } from 'pinia'

import { createStore } from "vuex";

// store 생성
export default createStore({
  state: {
    token: null,
    username: null,
    userId : null
  },
  getters: {
    isLogin(state){
      return state.token == null ? false : true ;
    }
  },
  mutations: {  // commit 으로 부를 수 있다.
    setToken(state, _token){
      state.token = _token;
    },
    setUsername(state, username){
      state.username = username;
    },
    setUserId(state, id){
      state.userId = (id);
    },
    setUser(state, user){
      state.user = (user);
    },
  },
  actions: { // dispatch 로 부를 수 있다.
    setToken:({commit} , _token) => {
      commit('setToken' , _token);
    },
    setUsername:({commit} , username) => {
      commit('setUsername' , username);
    },
    setUserId:({commit} , id) => {
      commit('setUserId' , id);
    },
    setUser:({commit} , user) => {
      commit('setUser' , user);
    }
  }
})