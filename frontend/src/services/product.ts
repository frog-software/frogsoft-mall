import request from "./request";
import { ProductDetails } from "../types/product";

export const getProductDetails = async (productID: string) => {
    return await request.get<ProductDetails>(`/commodities/${productID}/simple`)
}
