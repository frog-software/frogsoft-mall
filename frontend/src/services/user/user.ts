import {UserModifyInfo, UserRegisterPost, UserResponseInfo} from "../../types/user";
import request from "../request";

// US01-01 登录
export const login = async (username: string, password: string) =>
    await request.post<{ username: string, token: string }>("/auth/login", {username, password});

// US01-02 注册
export const register = async (data: UserRegisterPost) =>
    await request.post<{ username: string, phoneNum: string }>("/users", data);

// US01-03 忘记密码
export const forgetPassword = async (username: string) =>
    await request.put<{ username: string, phoneNum: string }>(`/users/${username}/forget`,);

// US02-01 获取用户信息
export const getUserInformation = async (username: string) => {
    return await request.get<UserResponseInfo>(`/users/${username}`)
}

// US02-02 修改用户信息
export const updateUserInformation = async (username: string, data: UserModifyInfo) => {
    return await request.put<UserResponseInfo>(`/users/${username}`, data)
}

// US02-03 修改密码
export const updatePassword = async (username: string, oldPassword: string, newPassword: string) => {
    return await request.put<UserResponseInfo>(`/users/${username}/password`, {oldPassword, newPassword})
}
