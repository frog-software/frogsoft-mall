//+-------------------------------------------------------------------------
//
//  聊天机器人
//
//  File:       chatRobot.ts
//
//  Directory:  src/services
//
//  History:    8月-23-2022   QQK  Created
//
//--------------------------------------------------------------------------
import request           from "./request";
import { RobotResponse } from "../types/robot";

// RO01-01 回复消息
export const getRobotResponse = async (chatRecord: string[]) => {
  return await request.post<RobotResponse>(`/robot`, chatRecord)
}
