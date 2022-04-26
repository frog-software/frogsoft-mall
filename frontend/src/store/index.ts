import { createStore } from 'vuex'
// 创建一个新的 store 实例
export const store = createStore({
    state () {
        return {
            count: 0
        }
    },
    mutations: {
        increment (state) {
            state.count++
        }
    }
})
