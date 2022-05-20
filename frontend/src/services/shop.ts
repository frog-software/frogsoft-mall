import request from "./request";
import {ShopDetails, ShopPostInfo, ShopResponseInfo} from "../types/shop";
import {ProductPostInfo, ProductSimpleInfo} from "../types/product";

// SP01-01 获取商店基本信息
export const getShopInfo = async (id: number) => {
    return await request.get<ShopResponseInfo>(`/shops/${id}`);
}

// SP01-02 修改商店基本信息
export const updateShopInfo = async (id: number, data: ShopPostInfo) => {
    return await request.put<ShopResponseInfo>(`/shops/${id}`, data);
}

// SP01-03 新增商店
export const addShop = async (data: ShopPostInfo) => {
    return await request.post<ShopDetails>('/shops/', data);
}

////////////////////////////////////////////////////////////////////////////////////////////

// Deprecated

// SP02-01 获取商店货品列表
// export const getProductListInShop = async (id: number) => {
//     return await request.get<ProductSimpleInfo[]>(`/shops/${id}/cargo`);
// }

// SP02-02 修改商店货品列表中的商品
// export const updateProductInShop = async (id: number, index: number, data: ProductPostInfo) => {
//     return await request.put<ProductSimpleInfo>(`/shops/${id}/cargo/${index}`, data);
// }

// SP02-03 删除商店货品列表中的商品
// export const deleteProductInShop = async (id: number, index: number) => {
//     return await request.del<null>(`/shops/${id}/cargo/${index}`);
// }

// SP02-04 新增商店货品列表中的商品
// export const addProductInShop = async (id: number, data: ProductPostInfo) => {
//     return await request.post<ProductSimpleInfo>(`/shops/${id}/cargo`, data);
// }
