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

export const productDescriptionFormat = (str: string, length: number) => {
  if (str.length >= length)
    return str.substring(0, length) + "..."
  else return str
}

export const getDecimal = (n: number) => {
  if (!n) return '00'
  return String(n.toFixed(2)).split('.')[1]
}
