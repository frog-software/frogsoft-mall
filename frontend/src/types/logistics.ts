// export interface DetailsAddress {
//   province: string, // 省
//   city: string,     // 市
//   county: string,   // 县
//   details: string   // 详细
//   id: number
// }

export interface Address {
  receiverPhone: string,        // 电话
  receiverName: string,         // 姓名
  detailAddress: string         // 地址
}

export interface LogisticsDetails {
  id: number,
  sender: Address,
  receiver: Address,
  sendTime: Date,
  expectedReceiveTime: Date,
  companyObligate: string
}
