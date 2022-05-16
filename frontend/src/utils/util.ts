//+-------------------------------------------------------------------------
//
//  一些多次调用的小方法
//
//  File:       util.ts
//
//  Directory:  src/utils
//
//  History:    5月-08-2022   QQK  Created
//
//--------------------------------------------------------------------------
import { DetailsAddress } from "../types/logistics";

export const addressFormat = (e: DetailsAddress) => {
  return e?.province + e?.city + e?.county + e?.details.split('。')[0]
}

export const contentFormat = (str: string, length: number) => {
  if (str.length >= length)
    return str.substring(0, length) + "..."
  else return str
}

export const getDecimal = (n: number) => {
  if (!n) return '00'
  return String(n.toFixed(2)).split('.')[1]
}

export const getStatusString = (idx: number) => {
  switch (idx) {
    case -1:
      return '已取消'
    case 1:
      return '已提交'
    case 2:
      return '已支付'
    case 3:
      return '已发货'
    case 4:
      return '已完成'
  }
}

export const getStatusColor = (idx: number) => {
  switch (idx) {
    case -1:
      return '#909399'
    case 1:
      return '#409eff'
    case 2:
      return '#67c23a'
    case 3:
      return '#e6a23c'
    case 4:
      return '#f56c6c'
  }
}
