import { Address }           from "./logistics"
import { ProductSimpleInfo } from "./product";
export interface UserResponseInfo {
    avatar: string,
    nickname: string,
    id: number,
    phoneNum: string,
    gender: number,
    addressList: Address[],
    accountList: string[],
    balance: number,
    purchaseHistory: ProductSimpleInfo[],
    shopName: string,
}

export interface CustomerSimpleInfo {
    avatar: string,
    nickname: string,
    id: number
}

export interface UserModifyInfo {
    avatar: string,
    nickname: string,
    id: number,
    phoneNum: string,
    gender: number,
    addressList: Address[],
    accountList: string[],
}

export interface UserRegisterPost {
    username: string,
    nickname: string,
    password: string,
    phone: string,
    code: string,
    avatar: string
}
