import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import ChatList from '../views/ChatList.vue'
import ChatDetailView from '../views/ChatDetailView.vue'
import BoardList from '@/views/BoardList.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      component: LoginView
    },{
      path: '/chat-list',
      component: ChatList
    },{
      path: '/chat-detail',
      component: ChatDetailView
    },
    {
      path: '/board-list',
      component: BoardList
    }
  ]
})

export default router
