import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import UserRegister from '../views/UserRegister.vue'
import UserInfo from '../views/UserInfo.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/userRegister',
    name: 'UserRegister',
    component: UserRegister
  },
  {
    path: '/userInfo',
    name: 'UserInfo',
    component: UserInfo
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
