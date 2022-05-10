package org.frogsoft.mall.commodity.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.comment.Comment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentDtoMapper {

    private final ProductDtoMapper productDtoMapper;

    public CommentDto toCommentDto(Comment comment){
        return new CommentDto()
            .setCommentId(comment.getId())
            .setCommentTime(comment.getCommentTime())
            .setContent(comment.getContent())
            .setCustomerId(comment.getCustomer().getId())
            .setParentId(comment.getParentId())
            .setProduct(productDtoMapper.toProductDto(comment.getProduct()));
    }
}
