import {OrderEditPostInfoSeller, OrderLogPostInfo, OrderSimpleInfo} from "../../types/order";
import request from "../request";

// OD02-01 商家修改单个订单
export const updateOrderSeller = async (orderId: string, order: OrderEditPostInfoSeller) => {
    return await request.put<OrderSimpleInfo>(`/orders/${orderId}/seller`, order);
}

// OD02-02 商家上传物流单号
export const uploadLogistics = async (orderId: string, data: OrderLogPostInfo) => {
    return await request.post<OrderLogPostInfo>(`/orders/${orderId}/seller`, data);
}

// OD02-03 商家取消单个订单
export const cancelOrderSeller = async (orderId: string) => {
    return await request.put<OrderSimpleInfo>(`/orders/${orderId}/seller`);
}

// OD02-04 商家查询所有订单
export const getAllOrdersSeller = async (data = {page: 1, size: 20}) => {
    return await request.get<OrderSimpleInfo[]>(`/orders/seller`, data);
}
