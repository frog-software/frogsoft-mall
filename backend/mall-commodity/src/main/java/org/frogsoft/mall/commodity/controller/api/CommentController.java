package org.frogsoft.mall.commodity.controller.api;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.controller.request.AddCommentRequest;
import org.frogsoft.mall.commodity.dto.CommentDto;
import org.frogsoft.mall.commodity.service.CommentService;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CommentController {

    private final CommentService commentService;

    // PD02-01
    @PostMapping("/{id}/comment")
    public ResponseEntity<?> addCommentToProduct(
        @RequestBody AddCommentRequest addCommentRequest,
        @PathVariable(value = "id") Long product_id,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        CommentDto savedComment = commentService.saveComment(product_id, addCommentRequest, authenticatedUser);
        return new ResponseBodyWrapper<CommentDto>()
            .status(HttpStatus.CREATED)
            .body(savedComment)
            .build();
    }

    // PD02-02
    @GetMapping("/{id}/comment/{index}")
    public ResponseEntity<?> getSingleCommentOfProduct(
        @PathVariable(value = "id") Long product_id,
        @PathVariable(value = "index") int comment_index,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<CommentDto>()
            .status(HttpStatus.OK)
            .body(commentService.getSingleCommentOfProduct(product_id, comment_index))
            .build();
    }

    // PD02-03
    @PutMapping("/{id}/comment/{index}")
    public ResponseEntity<?> putSingleCommentOfProduct(
        @RequestBody AddCommentRequest addCommentRequest,
        @PathVariable(value = "id") Long product_id,
        @PathVariable(value = "index") int comment_index,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ) {
        CommentDto savedComment = commentService.editSingleCommentOfProduct(product_id, comment_index, addCommentRequest);
        return new ResponseBodyWrapper<CommentDto>()
            .status(HttpStatus.CREATED)
            .body(savedComment)
            .build();
    }

    // PD02-04
    @DeleteMapping("/{id}/comment/{index}")
    public ResponseEntity<?> deleteSingleCommentOfProduct(
        @PathVariable(value = "id") Long product_id,
        @PathVariable(value = "index") int comment_index,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        commentService.deleteSingleCommentOfProduct(product_id, comment_index);
        return ResponseEntity.noContent().build();
    }

    // PD02-05
    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getAllCommentsOfProduct(
        @PathVariable(value = "id") Long product_id
    ){
        return new ResponseBodyWrapper<ArrayList<CommentDto>>()
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentsOfProduct(product_id))
            .build();
    }

}
