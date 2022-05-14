import {AODDetails, AODLogPostInfo, AODPostInfo, AODSimpleInfo} from "../../types/AOD";
import request from "../request";

// OD03-01 顾客提交售后申请
export const customerSubmitAftermarket = async (data: AODPostInfo) => {
    return await request.post<AODSimpleInfo>('/orders/aftermarket', data);
}

// OD03-02 获取单个售后申请
export const getAftermarket = async (id: string) => {
    return await request.get<AODDetails>(`/orders/aftermarket/${id}`);
}

// OD03-03 商家处理售后单
export const sellerHandleAftermarket = async (id: string, data: AODLogPostInfo) => {
    return await request.put<AODSimpleInfo>(`/orders/aftermarket/${id}/seller`, data);
}

// OD03-04 顾客处理售后单
export const customerHandleAftermarket = async (id: string, data: { cancel: boolean, finished: boolean }) => {
    return await request.put<AODSimpleInfo>(`/orders/aftermarket/${id}/customer`, data);
}

// OD03-05 顾客查询所有售后单
export const customerQueryAftermarket = async (data = {page: 1, size: 20}) => {
    return await request.get<AODSimpleInfo[]>(`/orders/aftermarket/customer`, data);
}

// OD03-06 商家查询所有售后单
export const sellerQueryAftermarket = async (data = {page: 1, size: 20}) => {
    return await request.get<AODSimpleInfo[]>(`/orders/aftermarket/seller`, data);
}
