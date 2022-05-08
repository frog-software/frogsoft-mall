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
import { ShopResponseInfo }  from "./shop";

export interface CartShopItemResponseInfo {
  product: ProductSimpleInfo
  addTime: string
  number: string
  remarks: string
}

export interface  CartShopResponseInfo {
  shop: ShopResponseInfo
  items: CartShopItemResponseInfo[]
}

export interface CartResponseInfo {
  user: string
  shops: CartShopResponseInfo[]
}
