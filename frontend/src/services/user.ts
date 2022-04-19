import { UserResponseInfo } from "../types/user";
import request from "./request";

export const getUserInformation = async (username: string) => {
    return await request.get<UserResponseInfo>(`/users/${username}`)
}