import { Address } from "./logistics"
export interface UserResponseInfo {
    avatar: string,
    nickname: string,
    id: string,
    phoneNum: string,
    gender: number,
    addressList: Address[],
    accountList: string[],
    balance: string,
}

export interface CustomerSimpleInfo {
    avatar: string,
    nickname: string,
    id: string
}