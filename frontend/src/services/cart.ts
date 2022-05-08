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
import request              from "./request";
import { CartResponseInfo } from "../types/cart";
export const getCartDetails = async (user: string) => {
  return await request.get<CartResponseInfo>(`/users/${user}/cart`)
}
