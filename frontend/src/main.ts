import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import { store, key } from './store'
import { router } from './router'

const app = createApp(App)

app.use(ElementPlus)
app.use(store, key)
app.use(router)
app.mount('#app')
