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
import request from "./request";
import {CartItemPostInfo, CartResponseInfo} from "../types/cart";
import {ProductSimpleInfo} from "../types/product";

// CM03-01 修改购物车商品
export const updateCartItem = async (username: string, index: number, cartItemPostInfo: CartItemPostInfo) => {
  return await request.post<ProductSimpleInfo>(`/customers/${username}/cart/${index}`, cartItemPostInfo);
};

// CM03-02 查询购物车商品（单个）
export const getCartItem = async (username: string, index: number) => {
  return await request.get<CartResponseInfo>(`/customers/${username}/cart/${index}`);
};

// CM03-03 从购物车中删除商品
export const deleteCartProduct = async (username: string, index: number) => {
  return await request.del<null>(`/customers/${username}/cart/${index}`)
}

// CM03-04 添加购物车商品
export const postCartProduct = async (username: string, item: CartItemPostInfo) => {
  return await request.post<CartResponseInfo>(`/customers/${username}/cart`, item)
}

// CM03-05 查询购物车商品
export const getCartDetails = async (username: string) => {
  return await request.get<CartResponseInfo>(`/customers/${username}/cart`)
}
