package org.frogsoft.mall.commodity.repository.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.data.jpa.domain.Specification;

// 连接多个SearchCriteria条件，返回能供JPA查询的Specification对象
// 用于实现多条件查询数据库
public class ProductSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ProductSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public ProductSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
            .map(ProductSpecification::new)
            .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                .isOrPredicate()
                ? Specification.where(result)
                .or(specs.get(i))
                : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
