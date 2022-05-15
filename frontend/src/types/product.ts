import { ShopResponseInfo } from "./shop";

export interface ProductSimpleInfo {
    id: number
    category: string
    brand: string
    productName: string
    price: number
    description: string
    shop: ShopResponseInfo
    thumb: string
}

export interface ProductDetails {
    id: number
    shop: ShopResponseInfo
    category: string
    brand: string
    productName: string
    price: number
    description: string
    imageList: string[]
    thumb: string
}

export interface ProductPostInfo {
    shopId: number,
    category: string,
    brand: string,
    productName: string,
    price: number,
    description: string,
    imageList: string[],
}
