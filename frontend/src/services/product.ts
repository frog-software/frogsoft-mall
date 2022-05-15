import request                                      from "./request";
import { ProductDetails, ProductSearchPagingParam } from "../types/product";

// PD01-02 获取单个商品信息
export const getProductDetails = async (productID: number) => {
    return await request.get<ProductDetails>(`/commodities/${productID}/simple`)
}

// PD01-05 查找商品信息（分页返回）
export const getProductDetailsPaging = async (param: any) => {
    return await request.get<ProductDetails[]>(`/commodities/all`, param)
}
