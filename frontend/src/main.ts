import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import {store} from './store'
import {router} from './router'
import {Request} from './services/request'
import {axios} from "axios";

const app = createApp(App)

app.use(ElementPlus)
app.use(store)
app.use(router)
app.use(axios, Request.init())
app.mount('#app')
