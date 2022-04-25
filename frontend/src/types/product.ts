import { CommentDetails } from "./comment";
import { ShopResponseInfo } from "./shop";
export interface ProductSimpleInfo {
    id: string,
    catagory: string,
    brand: string,
    productName: string,
    price: number,
    shop: ShopResponseInfo
}

export interface ProductDetails {
    id: string,
    catagory: string,
    brand: string,
    productName: string,
    price: number,
    shop: ShopResponseInfo,
    description: string,
    commentList: CommentDetails[]
}