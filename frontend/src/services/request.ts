import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios' //导入 axios 和钩子
import { ElLoading, ElMessage } from 'element-plus' //导入ElLoading

// 初始化loading

class Request {
    public static axiosInstance: AxiosInstance
    public static loading?: ILoadingInstance //loading实例 挂载到公共的静态属性上 方便获取

    public static init() {
        // 创建axios实例
        this.axiosInstance = axios.create({
            baseURL: 'http://127.0.0.1:4523/mock/760256/', //转接
            timeout: 6000
        })
        // 初始化拦截器
        this.initInterceptors()
        return this.axiosInstance
    }

    // 初始化拦截器
    public static initInterceptors() {
        // 设置post请求头
        this.axiosInstance.defaults.headers.post['Content-Type'] =
            'application/x-www-form-urlencoded'

        /**
         * 请求拦截器
         * 每次请求前，如果存在token则在请求头中携带token
         */
        this.axiosInstance.interceptors.request.use(
            (config: AxiosRequestConfig) => {
                //  loading打开
                this.loading = ElLoading.service({
                    lock: true,
                    text: '正在请求数据...',
                    background: 'rgb(0,0,0,0.5)'
                })

                const token = localStorage.getItem('ACCESS_TOKEN') //保存token到localStorage中
                if (token) {
                    ; (config as any).headers.Authorization = 'Bearer ' + token //携带请求头
                    // ;(config as any).headers.Authorization = sessionStorage.token
                }
                return config
            },
            (error: any) => {
                console.log(error)
            }
        )

        // 响应拦截器
        this.axiosInstance.interceptors.response.use(
            // 请求成功
            (response: any) => {
                for (var i in response.data.data) {

                    if (i.toLowerCase().includes('time')) {
                        response.data.data[i] = new Date(response.data.data[i])
                    }
                    if (Array.isArray(response.data.data[i]))
                        for (var j in response.data.data[i])
                            for (var k in response.data.data[i][j]) {
                                if (k.toLowerCase().includes('time')) {
                                    response.data.data[i][j][k] = new Date(response.data.data[i][j][k])
                                }
                            }

                }
                this.loading?.close() //将loading移除
                return response
            },
            // 请求失败
            (error: any) => {
                this.loading?.close() //将loading移除
                const { response } = error
                if (response) {
                    // 请求已发出，但是不在2xx的范围
                    Request.errorHandle(response)
                    return Promise.reject(response.data)
                } else {
                    // 处理断网的情况
                    ElMessage.error('网络连接异常,请稍后再试!')
                }
            }
        )
    }

    /**
     * 根据返回码进行处理
     * @param res 响应回调,根据不同响应进行不同操作
     */
    private static errorHandle(res: any) {
        // 状态码判断
        switch (res.status) {
            // case 401:
            //     break
            // case 403:
            //     break
            // case 404:
            //     break
            default:
                ElMessage.error(res.data)
        }
    }
}
const axiosInstance = Request.init()
export function get<T>(url: string, data: object = {}) {
    return new Promise<T>((resolve, reject) => {
        axiosInstance.get(url, { params: data })
            .then((res: { data: { data: T } }) => {
                resolve(res.data.data);
            })
            .catch((err) => {
                reject(err);
            });
    });
}

export function post<T>(url: string, data: object = {}) {
    return new Promise<T>((resolve, reject) => {
        axiosInstance.post(url, data)
            .then((res: { data: { data: T } }) => {
                resolve(res.data.data);
            })
            .catch((err) => {
                reject(err);
            });
    });
}

export function put<T>(url: string, data: object = {}) {
    return new Promise<T>((resolve, reject) => {
        axiosInstance.put(url, data)
            .then((res: { data: { data: T } }) => {
                resolve(res.data.data);
            })
            .catch((err) => {
                reject(err);
            });
    });
}

export function del<T>(url: string, data: object = {}) {
    return new Promise<T>((resolve, reject) => {
        axiosInstance.delete(url, { params: data })
            .then((res: { data: { data: T } }) => {
                resolve(res.data.data);
            })
            .catch((err) => {
                reject(err);
            });
    });
}

export default {
    get, post, put, del,
};
