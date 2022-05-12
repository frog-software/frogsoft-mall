//+-------------------------------------------------------------------------
//
//  有关购物车的请求
//
//  File:       cart.ts
//
//  Directory:  src/services
//
//  History:    5月-03-2022   QQK  Created
//
//--------------------------------------------------------------------------
import request                                    from "./request";
import { CartResponseInfo } from "../types/cart";

// CM03-01
// export const editCartGoods = async (username: string, index: number, item: CartShopItemPostInfo) => {
//   return await request.put<CartShopItemPostInfo>(`/customers/${username}/cart/${index.toFixed(0)}`, item)
// }

// CM03-03
export const deleteCartGoods = async (username: string, index: number) => {
  return await request.del(`/customers/${username}/cart/${index.toFixed(0)}`)
}

// cm03-04
// export const addCartGoods = async (username: string, item: CartShopItemPostInfo) => {
//   return await request.post<CartShopItemPostInfo>(`/customers/${username}/cart`, item)
// }

// CM03-05
export const getCartDetails = async (username: string) => {
  return await request.get<CartResponseInfo>(`/customers/${username}/cart`)
}
