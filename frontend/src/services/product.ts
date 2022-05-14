import request from "./request";
import { ProductDetails } from "../types/product";

export const getProductDetails = async (productID: number) => {
    return await request.get<ProductDetails>(`/commodities/${productID}/simple`)
}
