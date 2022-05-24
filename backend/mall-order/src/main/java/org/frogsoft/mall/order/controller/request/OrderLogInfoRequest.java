package org.frogsoft.mall.order.controller.request;

import java.io.Serializable;
import lombok.Data;

@Data // 上传物流信息
public class OrderLogInfoRequest implements Serializable {
    private String logisticsNumber; // 物流单号
}
