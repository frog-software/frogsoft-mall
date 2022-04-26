import { CommentDetails } from "./comment";
import { ShopResponseInfo } from "./shop";
export interface ProductSimpleInfo {
    id: string,
    category: string,
    brand: string,
    productName: string,
    price: number,
    shop: ShopResponseInfo,
    thumb: string,
}

export interface ProductDetails {
    id: string,
    category: string,
    brand: string,
    productName: string,
    price: number,
    shop: ShopResponseInfo,
    description: string,
    commentList: CommentDetails[]
    imageList: string[],
    thumb: string,
}
