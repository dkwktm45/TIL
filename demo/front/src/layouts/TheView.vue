<template>
  <main>
    <div class="container mt-4 ">
      <router-view></router-view>
      <TheFooter v-if="store.state.username != null"></TheFooter>

    </div>
  </main>

</template>

<script setup>
import {useStore} from "vuex";
import TheFooter from "../layouts/TheFooter.vue";
const store = useStore();

</script>

<style lang="scss" scoped></style>
