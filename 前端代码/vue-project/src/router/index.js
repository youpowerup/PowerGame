import Vue from 'vue'
import VueRouter from 'vue-router'
// import { component } from 'vue/types/umd'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Element/LoginPage.vue')
  },
  {
    path: '/customer',
    name: 'customer',
    component: () => import('../views/Element/ElementViewCustomer.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Element/RegisterPage.vue')
  },
  {
    path: '/',
    redirect:'/login'
  },
  {
    path:'/disLogin',
    name:'disLogin',
    component:() => import('../views/Element/DisLogin.vue')
  },
  {
    path:'/managerPage',
    name:'managerPage',
    component:() => import('../views/Element/ManagerPage.vue')
  },
  {
    path:'/superManager',
    name:'superManager',
    component:() => import('../views/Element/SuperManager.vue')
  },
  {
    path:'/gameDetail',
    name:'gameDetail',
    component:() => import('../views/Element/gameDetail.vue')
  },
  {
    path:'/shoppingCar',
    name:'shoppingCar',
    component:() => import('../views/Element/ShoppingCar.vue')
  },
  {
    path:'/historyOrder',
    name:'historyOrder',
    component:() => import('../views/Element/HistoryOrder.vue')
  },
  {
    path:'/managerHistoryOrder',
    name:'managerHistoryOrder',
    component:() => import('../views/Element/ManagerHistoryOrder.vue')
  },
  {
    path:'/managerSales',
    name:'managerSales',
    component:() => import('../views/Element/ManagerSales.vue')
  },
  {
    path:'/allSales',
    name:'allSales',
    component:() => import('../views/Element/AllSales.vue')
  },
  {
    path:'/databaseLog',
    name:'databaseLog',
    component:() => import('../views/Element/DatabaseLog.vue')
  },
  {
    path:'/loginLog',
    name:'loginLog',
    component:() => import('../views/Element/LoginLog.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
