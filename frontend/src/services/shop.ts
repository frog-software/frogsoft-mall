import request from "./request";
import {ShopResponseInfo} from "../types/shop";

// SP01-01 获取商店基本信息
export const getShopInfo = async (id: number) => {
    return await request.get<ShopResponseInfo>(`/shops/${id}`);
}
