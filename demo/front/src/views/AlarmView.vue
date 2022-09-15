<template xmlns:div="http://www.w3.org/1999/html">
  <footer class="position-absolute bottom-0 end-0">
  <span :class="alarmStyle" class="sticky-top">
      <ul class="list-group">
        <li class="list-group-item disabled" aria-disabled="true">A disabled item</li>
        <li class="list-group-item">A second item</li>
        <li class="list-group-item">A third item</li>
        <li class="list-group-item">A fourth item</li>
        <li class="list-group-item">And a fifth one</li>
      </ul>
    </span>
  <button v-on:click="alertList" type="button" class="float-end rounded-5 btn btn-primary ">
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
import {ref} from 'vue'
import {useRouter} from "vue-router/dist/vue-router";
const router = useRouter();
const alarm = ref("")
const store = useStore();
const name = store.state.username;
const alarmStyle = ref("visually-hidden")

const source = new EventSource("http://localhost:8088/receive/notify/" +name ,{withCredentials : true});

source.onmessage = function(e) {
  alarm.value = e.data
};

source.onerror = (e) => {
  router.push("/login")
};
const alertList = () =>{
  if(alarmStyle.value == "visible"){
    alarmStyle.value = "visually-hidden-focusable"
  }else{
    alarmStyle.value = "visible"
  }
}

</script>

<style scoped>

</style>