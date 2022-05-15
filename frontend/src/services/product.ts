import request from "./request";
import {ProductDetails, ProductPostInfo, ProductSimpleInfo} from "../types/product";

// PD01-01 添加商品到商店
export const addProduct = (product: ProductPostInfo) => {
    return request.post<ProductSimpleInfo>("/commodities/simple", product);
};

// PD01-02 获取单个商品信息
export const getProductDetails = async (productID: number) => {
    return await request.get<ProductDetails>(`/commodities/${productID}/simple`)
}

// PD01-03 修改单个商品信息
export const updateProduct = (productID: number, product: ProductPostInfo) => {
    return request.put<ProductSimpleInfo>(`/commodities/${productID}/simple`, product);
};

// PD01-04 删除单个商品信息
export const deleteProduct = (productID: number) => {
    return request.del<null>(`/commodities/${productID}/simple`);
};

// PD01-05 查找商品信息（分页返回）
export const getProductDetailsPaging = async (param: any) => {
    return await request.get<ProductDetails[]>(`/commodities/all`, param)
}
