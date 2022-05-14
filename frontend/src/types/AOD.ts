import {OrderSimpleInfo} from "./order";
import {Address} from "./logistics";

export enum Status {
    OPEN = "OPEN",
    FINISHED = "FINISHED",
    CLOSED = "CLOSED"
}

export interface AODDetails {
    id: string,
    createTime: Date,
    order: OrderSimpleInfo,
    remarks: string,
    status: Status,
    logisticsNumber: string,
    logisticsAddress: Address,
}

export interface AODSimpleInfo {
    id: string,
    createTime: Date,
    orderId: string,
    remarks: string,
    status: Status,
    logisticsNumber: string,
    logisticsAddress: Address,
}

export interface AODPostInfo {
    orderId: number,
    remarks: string,
}

export interface AODLogPostInfo {
    logisticsStatus: boolean, // 是否同意修改
    logisticsNumber: string,
    logisticsAddress: Address,
}
