import request from "./request";
import { ProductDetails } from "../types/product";
export const getProductDetails = async (id: number) => {
    return await request.get<ProductDetails>(`/commodities/${id}/simple`)
}