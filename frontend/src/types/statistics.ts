//+-------------------------------------------------------------------------
//
//  数据统计数据模型
//
//  File:       statistics.ts
//
//  Directory:  src/types
//
//  History:    9月-01-2022   QQK  Created
//
//--------------------------------------------------------------------------

export interface ActionAnalysis {
  click: number
  add: number
  purchase: number
  subscribe: number
}

export interface AgeAnalysis {
  gender: number
  ageRange: number
  count: number
}

export interface CategoryAnalysis {
  category: number
  count: number
}

export interface GenderAnalysis {
  female: number
  male: number
  unknown: number
}

export interface PredictionAnalysis {
  prediction: boolean
}

export interface ProvinceAnalysis {
  province: string
  count: number
}

export interface PredictPostInfo {
  user_id?: string
  age_range?: number
  gender?: number
  merchant_id: string
}
