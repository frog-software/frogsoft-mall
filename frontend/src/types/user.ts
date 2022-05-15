import {Address} from "./logistics"
import {ProductSimpleInfo} from "./product";

// User 区
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

export interface UserModifyInfo {
    avatar: string,
    nickname: string,
    id: string,
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

// 疑似 Customer 却是 User 区

export interface UserBankCardInfo {
    username: string,
    accountList: {
        accountNo: string,
        balance: number,
    }[],
    purchaseHistory: ProductSimpleInfo[],
}

export interface UserBankCardPostInfo {
    username: string,
    bankcardNum: string,
    bankcardInc: string
}

// Customer 区

export interface CustomerSimpleInfo {
    avatar: string,
    nickname: string,
    id: number
}

export interface CustomerDetails {
    avatar: string,
    nickname: string,
    id: number,
    phoneNum: string,
    gender: number,
    addressList: Address[],
    accountList: string[],
    balance: number,
    purchaseHistory: ProductSimpleInfo[],
}

export interface CustomerAddressInfo {
    username: string,
    addressList: Address[]
}
