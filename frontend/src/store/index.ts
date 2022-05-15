import {InjectionKey} from "vue";
import {createStore, useStore as baseUseStore, Store} from 'vuex'
import request from "../services/request";
import {ElMessage} from "element-plus";
import {getUserInformation} from "../services/user/user"
import {UserResponseInfo} from "../types/user";

// 为 store state 声明类型
export interface State {
    username: string
    info?: UserResponseInfo
}

// 定义 injection key
export const key: InjectionKey<Store<State>> = Symbol()

export const store = createStore<State>({
    state: {
        username: ''
    },
    getters: {
        hasLogin(state: State) {
            return state.username !== ''
        }
    },
    mutations: {
        login(state, username: string) {
            state.username = username
            ElMessage.success(`欢迎您！尊敬的${username}`)
        },
        logout(state) {
            localStorage.removeItem('ACCESS_TOKEN')
            ElMessage.success(`下次再见！尊敬的${state.username}`)
            state.username = ''
            delete state.info
        },
        updateUserDetails(state, payload: UserResponseInfo) {
            state.info = payload
        }
    },
    actions: {
        async login(context, payload: { username: string, password: string }) {
            await request.post<{
                username: string,
                token: string
            }>('/auth/login', {
                username: payload.username,
                password: payload.password
            }).then((res) => {
                localStorage.setItem('ACCESS_TOKEN', res.token)
                context.commit('login', res.username)
                context.dispatch('updateUserDetails')
            })
        },
        async updateUserDetails({ state, commit }) {
            await getUserInformation(state.username).then(res => {
                commit('updateUserDetails', res)
            })
        }
    }
})

// 定义自己的 `useStore` 组合式函数
export function useStore() {
    return baseUseStore(key)
}
