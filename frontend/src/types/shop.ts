import {UserResponseInfo} from "./user";

export interface ShopResponseInfo {
    rate: number
    shopName: string
    ownerName: string
    shopImage: string
    id: number
    tradeQuantity: number
}

export interface ShopPostInfo {
    shopName: string
    shopImage: string
}

export interface ShopDetails {
    owner: UserResponseInfo,
    rate: number
    shopName: string
    shopImage: string
    id: number
    tradeQuantity: number
}
