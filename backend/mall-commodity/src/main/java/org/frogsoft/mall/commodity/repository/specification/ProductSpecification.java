package org.frogsoft.mall.commodity.repository.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.data.jpa.domain.Specification;

// 定义单条件查询，由字符串解析而成
@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) {

        List<Predicate> list = new ArrayList<Predicate>();

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("=")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                // 如果是字符串类型，则使用模糊查询
                return criteriaBuilder.like(
                    root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                // 否则， 使用准确查询
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
