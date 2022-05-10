package org.frogsoft.mall.commodity.repository;


import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.comment.Comment;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    ArrayList<Comment> findAllBy();
}
