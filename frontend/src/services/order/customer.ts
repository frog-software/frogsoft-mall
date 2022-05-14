import request from "../request";
import {
    OrderItemInfoCustomer,
    OrderPayingPostInfo,
    OrderPostInfoCustomer,
    OrderSimpleInfo
} from "../../types/order";
import {getShopInfo} from "../shop";
import {getUserInformation} from "../user";
import {getProductDetails} from "../product";

// OD01-02 顾客查询单个订单
export const getOrderSimpleInfoCustomer = async (orderId: string) => {
    return await request.get<OrderSimpleInfo>(`/orders/${orderId}/customer`)
}

export const getOrderDetailsCustomersBySimple = async (order: OrderSimpleInfo) => {
    const seller = await getShopInfo(order.sellerId);
    const buyer = await getUserInformation(order.buyerName);
    const products = await Promise.all(order.products.map(async (product: OrderItemInfoCustomer) => {
        const details = await getProductDetails(product.id);
        return {
            ...product,
            product: details
        }
    }));
    return {
        ...order,
        seller,
        buyer,
        products
    }
}

export const getOrderDetailsCustomer = async (orderId: string) => {
    const order = await getOrderSimpleInfoCustomer(orderId)
    return await getOrderDetailsCustomersBySimple(order)
}

// OD01-03 顾客修改单个订单
export const updateOrderCustomer = async (orderId: string, order: OrderPostInfoCustomer) => {
    return await request.put<OrderSimpleInfo>(`/orders/${orderId}/customer`, order);
}

// OD01-04 顾客删除单个订单
export const deleteOrderCustomer = async (orderId: string) => {
    return await request.del<null>(`/orders/${orderId}/customer`);
}

// OD01-05 顾客查询所有订单
export const getAllOrdersCustomer = async (page: number = 1, size: number = 20) => {
    return await request.get<OrderSimpleInfo[]>(`/orders/customer/all`, {page, size});
}

// OD01-06 顾客付款
export const payOrderCustomer = async (orderId: string, data: OrderPayingPostInfo) => {
    return await request.post<OrderPayingPostInfo>(`/orders/${orderId}/customer/payment`, data);
}

// OD01-07 顾客收货
export const receiveOrderCustomer = async (orderId: string) => {
    return await request.put<OrderSimpleInfo>(`/orders/${orderId}/customer/receiving`);
}
