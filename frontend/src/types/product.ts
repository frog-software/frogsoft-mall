import { ShopResponseInfo } from "./shop";

export interface ProductSimpleInfo {
    id: string
    category: string
    brand: string
    productName: string
    price: number
    description: string
    shop: ShopResponseInfo
    thumb: string
}

export interface ProductDetails {
    id: string
    shop: ShopResponseInfo
    category: string
    brand: string
    productName: string
    price: number
    description: string
    imageList: string[]
    thumb: string
}
