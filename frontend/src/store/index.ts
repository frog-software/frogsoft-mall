import {createStore, Store} from 'vuex'
import {InjectionKey} from "vue";

// 为 store state 声明类型
export interface State {
    count: number
}

// 定义 injection key
export const key: InjectionKey<Store<State>> = Symbol()

export const store = createStore<State>({
    state: {
        count: 0
    }
})
