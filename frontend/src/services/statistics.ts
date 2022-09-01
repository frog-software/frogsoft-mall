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
import { VITE_BACKEND_URL } from "../consts/urls";

let PREFIX = "";
let prefixFlag = false;

export const changePrefix = (newPrefix: string) => {
  if (prefixFlag) {
    PREFIX = ""
  } else {
    PREFIX = "/statistics"
  }
  prefixFlag = !prefixFlag
}

// 获取用户行为分析数据
export const getActionAnalysis = async () => {
  return await request.get<ActionAnalysis>(PREFIX + '/action');
}

// 获取用户年龄分析数据
export const getAgeAnalysis = async () => {
  return await request.get<AgeAnalysis[]>(PREFIX + '/age');
}

// 获取商品类别分析数据
export const getCategoryAnalysis = async () => {
  return await request.get<CategoryAnalysis[]>(PREFIX + '/category');
}

// 获取用户性别分析数据
export const getGenderAnalysis = async () => {
  return await request.get<GenderAnalysis>(PREFIX + '/gender');
}

// 获取回头客预测结果
export const getPredictionAnalysis = async (data: PredictPostInfo) => {
  return await request.get<PredictionAnalysis>(PREFIX + '/prediction', data);
}

// 获取收货地址省份分析数据
export const getProvinceAnalysis = async () => {
  return await request.get<ProvinceAnalysis[]>(PREFIX + '/province');
}

export const runActionAnalysis = async () => {
  return await fetch(VITE_BACKEND_URL + PREFIX + "/action/run", {
    method: "POST"
  })
}

export const runAgeAnalysis = async () => {
  return await fetch(VITE_BACKEND_URL + PREFIX + "/age/run", {
    method: "POST"
  })
}

export const runCategoryAnalysis = async () => {
  return await fetch(VITE_BACKEND_URL + PREFIX + "/category/run", {
    method: "POST"
  })
}

export const runGenderAnalysis = async () => {
  return await fetch(VITE_BACKEND_URL + PREFIX + "/gender/run", {
    method: "POST"
  })
}

export const runProvinceAnalysis = async () => {
  return await fetch(VITE_BACKEND_URL + PREFIX + "/province/run", {
    method: "POST"
  })
}

