import { ProductSimpleInfo } from "./product";
import { CustomerSimpleInfo } from "./user";

export interface CommentDetails {
    type: number
    content: string
    customer: CustomerSimpleInfo
    product: ProductSimpleInfo
    commentTime: string
    parentId: number
}

export interface CommentPostInfo {
    type: number
    content: string
    productId: number
    parentId: number
}
