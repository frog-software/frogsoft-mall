//+-------------------------------------------------------------------------
//
//  有关购物车的数据模型
//
//  File:       cart.ts
//
//  Directory:  src/types
//
//  History:    5月-03-2022   QQK  Created
//
//--------------------------------------------------------------------------
import { ProductSimpleInfo } from "./product";

export interface CartItemPostInfo {
  productID: string
  amount: number
  remarks: string
}

export interface CartItemResponseInfo {
  product: ProductSimpleInfo
  addTime: string
  amount: number
  remarks: string
}

export interface CartResponseInfo {
  username: string
  itemCount: number
  cartItems: CartItemResponseInfo[]
}
