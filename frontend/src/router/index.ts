import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: () => import('../views/HomePage.vue'),
  },
  {
    path: '/shop',
    name: 'ShopPage',
    component: () => import('../views/ShopPage.vue'),
  },
  {
    path: '/productDetail/:id',
    name: 'ProductDetailsPage',
    component: () => import('../views/ProductDetailsPage.vue'),
    props: true,
  },
  {
    path: '/orders/:id',
    name: 'OrderDetailsPage',
    component: () => import('../views/OrderDetailsPage.vue'),
    props: true,
  },
  {
    path: '/cart',
    name: 'CartPage',
    component: () => import('../views/CartPage.vue'),
  },
  {
    path: '/user',
    name: 'UserPage',
    component: () => import('../views/UserPage.vue'),
  },
  {
    path: '/about',
    name: 'AboutPage',
    component: () => import('../views/Home.vue')
  },
]

export const router = createRouter({
  history: createWebHashHistory(),
  routes,
})
