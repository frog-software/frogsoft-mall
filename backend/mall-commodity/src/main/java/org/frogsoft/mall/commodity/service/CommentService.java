package org.frogsoft.mall.commodity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import org.frogsoft.mall.commodity.controller.request.AddCommentRequest;
import org.frogsoft.mall.commodity.dto.CommentDto;
import org.frogsoft.mall.commodity.dto.CommentDtoMapper;
import org.frogsoft.mall.commodity.dto.ProductDtoMapper;
import org.frogsoft.mall.commodity.repository.CommentRepository;
import org.frogsoft.mall.commodity.repository.ProductRepository;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.comment.Comment;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;
    private final CommentRepository commentRepository;
    private final CommentDtoMapper commentDtoMapper;

    // 新建评论
    public CommentDto saveComment(Long product_id, AddCommentRequest addCommentRequest, User authenticatedUser){
        // 获得评论对应的商品
        Product targetProduct = productRepository.findById(product_id)
            .orElseThrow(() -> new NotFoundException("product not found."));

        // 新建商品
        Comment newComment = commentRepository.save(new Comment()
            .setCustomer(authenticatedUser)
            .setContent(addCommentRequest.getContent())
            .setType(addCommentRequest.getType())
            .setParentId(addCommentRequest.getParentId())
            .setCommentTime(LocalDateTime.now())
            .setProduct(targetProduct)
        );

        // 相应商品的评论列表外键更新
        List<Comment> currCommentList = targetProduct.getCommentList();
        if(currCommentList != null)
            currCommentList.add(newComment);
        else
            throw new NullPointerException("comment list not initialize!");
        productRepository.save(targetProduct.setCommentList(currCommentList));

        return commentDtoMapper.toCommentDto(newComment);
    }

    // 获得一个商品的所有评论列表
    public ArrayList<CommentDto> getAllCommentsOfProduct(Long product_id){
        Product targetProduct = productRepository.findById(product_id)
            .orElseThrow(() -> new NotFoundException("product not found."));
        // TODO：分页
        return targetProduct.getCommentList()
            .stream()
            .map(commentDtoMapper::toCommentDto)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    // 通过商品中的外键列表索引index获得评论
    public CommentDto getSingleCommentOfProduct(Long product_id, int index){
        Product targetProduct = productRepository.findById(product_id)
            .orElseThrow(() -> new NotFoundException("product not found."));
        try {
            Comment targetComment = targetProduct.getCommentList().get(index);
            return commentDtoMapper.toCommentDto(targetComment);
        }
        catch (IndexOutOfBoundsException e){
            throw new NotFoundException("comment not found.");
        }
    }

    // 通过商品中的外键列表索引index修改评论
    public CommentDto editSingleCommentOfProduct(Long product_id, int index, AddCommentRequest addCommentRequest){
        Product targetProduct = productRepository.findById(product_id)
            .orElseThrow(() -> new NotFoundException("product not found."));
        try {
            Comment targetComment = targetProduct.getCommentList().get(index);
            // TODO：比对user是否有修改权限
            Comment savedComment = commentRepository.save(targetComment
                .setContent(addCommentRequest.getContent())
                .setType(addCommentRequest.getType())
                .setParentId(addCommentRequest.getParentId()));
            return commentDtoMapper.toCommentDto(savedComment);
        }
        catch (IndexOutOfBoundsException e){
            throw new NotFoundException("comment not found.");
        }
    }

    // 通过商品中的外键列表索引index删除评论
    public void deleteSingleCommentOfProduct(Long product_id, int index){
        Product targetProduct = productRepository.findById(product_id)
            .orElseThrow(() -> new NotFoundException("product not found."));
        try {
            List<Comment> commentList = targetProduct.getCommentList();

            Comment targetComment = commentList.get(index);
            targetComment.setCustomer(null);
            targetComment.setProduct(null);
            Comment savedComment = commentRepository.save(targetComment);

            commentList.remove(index);
            productRepository.save(targetProduct.setCommentList(commentList));

            commentRepository.delete(savedComment);
        }
        catch (IndexOutOfBoundsException e){
            throw new NotFoundException("comment not found.");
        }
    }

    // 通过评论id获得评论
    private CommentDto getSingleComment(Long id){
        return commentDtoMapper.toCommentDto(commentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("comment not found.")));
    }

}
