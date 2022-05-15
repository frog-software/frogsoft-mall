//+-------------------------------------------------------------------------
//
//  有关评论的请求
//
//  File:       comment.ts
//
//  Directory:  src/services
//
//  History:    5月-12-2022   QQK  Created
//
//--------------------------------------------------------------------------
import request                             from "./request";
import { CommentDetails, CommentPostInfo } from "../types/comment";

// PD02-01 提交商品评论
export const postComment = async (productID: number, item: CommentPostInfo) => {
  return await request.post<CommentPostInfo>(`/commodities/${productID}/comment`, item)
}

// PD02-05 查找商品评论（分页返回）
export const getCommentDetails = async (productID: number) => {
  return await request.get<CommentDetails[]>(`/commodities/${productID}/comment`)
}
