package org.frogsoft.mall.commodity.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

// 定义一条查询条件：
// operation：操作符，包括=、>、<
@Getter
@Setter
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public boolean isOrPredicate() {
        return operation.equals("or");
    }
}
