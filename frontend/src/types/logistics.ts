interface DetailsAddress {
    province: string, // 省
    city: string, // 市
    county: string, // 县
    details: string // 详细
}

export interface Address {
    phone: string, // 电话
    name: string, // 姓名
    address: DetailsAddress // 地址
}

export interface LogisticsDetails {
    id: string,
    sender: Address,
    receiver: Address,
    sendTime: Date,
    expectedReceiveTime: Date,
    companyObligate: string
}