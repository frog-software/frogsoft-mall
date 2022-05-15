import request from "../request"
import {CustomerAddressInfo, UserBankCardInfo, UserBankCardPostInfo} from "../../types/user";
import {Address} from "../../types/logistics";

////////////////////////////////////////////////////////////////////////////////

// CM01-01 获取购物地址
export const getCustomerAddress = async (username: string) => {
  return await request.get<CustomerAddressInfo>(`/customers/${username}/address`);
};

// CM01-02 添加购物地址
export const addCustomerAddress = async (username: string, address: Address) => {
  return await request.post<CustomerAddressInfo>(`/customers/${username}/address`, address);
};

// CM01-03 删除购物地址
export const deleteCustomerAddress = async (username: string, index: number) => {
  return await request.del<CustomerAddressInfo>(`/customers/${username}/address/${index}`);
};

// CM01-04 修改购物地址
export const updateCustomerAddress = async (username: string, index: number, address: Address) => {
  return await request.put<CustomerAddressInfo>(`/customers/${username}/address/${index}`, address);
};

////////////////////////////////////////////////////////////////////////////////

// CM02-01 查看银行卡
export const getCustomerBankCard = async (username: string) => {
  return await request.get<UserBankCardInfo>(`/customers/${username}/bankcard`);
};

// CM02-02 添加银行卡
export const addCustomerBankCard = async (username: string, bankCard: UserBankCardPostInfo) => {
  return await request.post<UserBankCardInfo>(`/customers/${username}/bankcard`, bankCard);
};

// CM02-03 删除银行卡
export const deleteCustomerBankCard = async (username: string, card: UserBankCardPostInfo) => {
  return await request.del<UserBankCardInfo>(`/customers/${username}/bankcard`, card);
};
