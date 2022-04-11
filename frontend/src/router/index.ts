import { createRouter,createWebHashHistory } from 'vue-router'
const routes = [
    {
        path: '/',
        redirect: '/main'
    },
    {
        path: '/main',
        name: 'Main',
        component: () => import('../views/Main.vue'),
        children: [
            {
                path: '/',
                redirect: 'shop',
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
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})
