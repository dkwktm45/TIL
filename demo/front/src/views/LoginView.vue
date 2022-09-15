<template>
  <form>
  <div class=" mb-3">
    <label for="exampleInputEmail1" class="form-label">login</label>
    <input type="username" v-model="username" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
    <div id="emailHelp" class="form-text">We'll never share your id with anyone else.</div>
    </div>
    <div class="mb-3">
      <label for="exampleInputPassword1" class="form-label">Password</label>
      <input type="password" v-model="password" class="form-control" id="exampleInputPassword1">
    </div>
    <div class="mb-3 form-check">
      <input type="checkbox" class="form-check-input" id="exampleCheck1">
      <label class="form-check-label" for="exampleCheck1">Check me out</label>
    </div>
    <button type="button" class="btn btn-primary" @click="login()">Submit</button>
  </form>
</template>
<script setup>
import { useRouter } from 'vue-router';
import { useStore } from "vuex";
import axios from 'axios';
import { ref } from 'vue'

const username = ref("")
const password = ref("")
const store = useStore()
const router = useRouter();

const login = () =>{
  axios.post("http://localhost:8088/loginInsert",null ,{
    auth : {
      username: username.value,
      password: password.value
    }
  }).then(res => {
    store.commit("setUsername",username.value)
    store.commit("setUserId",res.data.userId)
    store.commit("setUser",res.data)

    router.push("/board-list")
  }).catch(e => {
    console.log(e.response.data);
    console.log(e.response.status);
    console.log(e.response.headers);
  })
}

</script>
