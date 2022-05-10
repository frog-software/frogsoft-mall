import { createRouter,createWebHashHistory } from 'vue-router'
const routes = [
    {
        path: '/',
        name:'Home',
        component: () => import('../views/Home.vue')
    },
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})
