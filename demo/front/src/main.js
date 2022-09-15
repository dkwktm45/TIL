import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Chat from './Chat.vue'
import router from './router/index.js'
import './assets/main.css'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import { BootstrapIconsPlugin } from 'bootstrap-icons-vue';
import store from "./stores/counter.js"

const app = createApp(Chat)
app.use(store)
app.use(createPinia())
app.use(BootstrapIconsPlugin)
app.use(router)

app.mount('#app')
