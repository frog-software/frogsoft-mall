package org.frogsoft.mall.commodity.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class AddProductRequset implements Serializable {

    private int shopId;
    private String category;
    private String brand;
    private String productName;
    private BigDecimal price;
    private String description;
    private List<String> imageList;
}

