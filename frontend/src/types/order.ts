import {Address} from "./logistics";
import {CustomerSimpleInfo} from "./user";
import {ShopResponseInfo} from "./shop";
import {ProductDetails} from "./product";

export interface OrderItemInfoCustomer {
    id: number,
    remarks: string,
    amount: number
}

export interface OrderItemDetails extends OrderItemInfoCustomer {
    product: ProductDetails
}

export interface OrderSimpleInfo {
    id: string,
    orderTime: Date,
    buyerName: string,
    sellerId: number,
    totalPrice: number,
    products: OrderItemInfoCustomer[],
    remarks: string,
    payment: string,
    status: number,
    logisticsAddress: Address,
    logisticsNumber: string
}

export interface OrderPostInfoCustomer {
    customerName: string,
    shopId: number,
    orderProducts: OrderItemInfoCustomer[],
    remarks: string,
    logisticsAddressID: number
}

export interface OrderDetailsInfo extends OrderSimpleInfo {
    buyer: CustomerSimpleInfo,
    seller: ShopResponseInfo
    products: OrderItemDetails[]
}

export interface OrderPayingPostInfo {
    payment: string,
    bankCardId: number,
}

export interface OrderEditPostInfoSeller {
    shopId: number,
    orderProducts: OrderItemInfoCustomer[],
    remarks: string
}

export interface OrderLogPostInfo {
    logisticsNumber: string,
}
