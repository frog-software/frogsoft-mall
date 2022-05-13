package org.frogsoft.mall.commodity.repository;


import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.comment.Comment;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    ArrayList<Comment> findAllBy();

    // 查找同一商品的所有评论
    ArrayList<Comment> findAllByProduct(Product product);

}
