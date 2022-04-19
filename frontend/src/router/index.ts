import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
      redirect: { name: 'Main' }
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import('../views/Main.vue'),
    children: [
      {
        path: '/main',
        redirect: '/main/shop',
      },
      {
        path: 'shop',
        component: () => import('../views/Shop.vue'),
      },
      {
        path: 'test1',
        component: () => import('../views/Home.vue'),
      },
    ]
  },
  {
    path: '/goods',
    name: 'Goods',
    component: () => import('../views/GoodsDetail.vue'),
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('../views/Cart.vue'),
  },
  {
    path: '/user',
    name: 'User',
    component: () => import('../views/User.vue'),
  },
]

export const router = createRouter({
  history: createWebHashHistory(),
  routes,
})
