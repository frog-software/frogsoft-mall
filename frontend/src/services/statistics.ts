//+-------------------------------------------------------------------------
//
//  数据统计相关请求
//
//  File:       statistics.ts
//
//  Directory:  src/services
//
//  History:    8月-31-2022   QQK  Created
//
//--------------------------------------------------------------------------
import request from "./request";
import {
  ActionAnalysis,
  AgeAnalysis,
  CategoryAnalysis,
  GenderAnalysis,
  PredictionAnalysis, PredictPostInfo,
  ProvinceAnalysis
} from "../types/statistics";

// 获取用户行为分析数据
export const getActionAnalysis = async () => {
  return await request.get<ActionAnalysis>('/statistics/action');
}

// 获取用户年龄分析数据
export const getAgeAnalysis = async () => {
  return await request.get<AgeAnalysis[]>('/statistics/age');
}

// 获取商品类别分析数据
export const getCategoryAnalysis = async () => {
  return await request.get<CategoryAnalysis[]>('/statistics/category');
}

// 获取用户性别分析数据
export const getGenderAnalysis = async () => {
  return await request.get<GenderAnalysis>('/statistics/gender');
}

// 获取回头客预测结果
export const getPredictionAnalysis = async (data: PredictPostInfo) => {
  return await request.get<PredictionAnalysis>('/statistics/prediction', data);
}

// 获取收货地址省份分析数据
export const getProvinceAnalysis = async () => {
  return await request.get<ProvinceAnalysis[]>('/statistics/province');
}



