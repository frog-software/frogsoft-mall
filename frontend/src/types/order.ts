import { Address } from "./logistics";

export interface OrderItemInfoCustomer {
  id: number,
  remarks: string,
  amount: number
}

export interface OrderSimpleInfo {
  id: string,
  orderTime: Date,
  buyerName: string,
  sellerName: string,
  totalPrice: number,
  products: OrderItemResponseInfo[],
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

export interface OrderItemResponseInfo {
  id: number
  remarks: string
  amount: number
  orderPrice: number
  productName: string
  thumb: string
}
