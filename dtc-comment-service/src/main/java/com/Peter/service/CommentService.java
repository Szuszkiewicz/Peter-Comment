package com.Peter.service;

import com.Peter.dto.CommentInfoDto;
import com.Peter.dto.CommentResultInfoDto;
import com.Peter.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    /**
     * 添加评论
     * @param dto
     * @return
     */
    int addComment(CommentInfoDto dto);
   /**
     * 删除评论
     * @param dto
     * @return
     */
    int deleteComment(CommentInfoDto dto);//校验之后再删除，不能直接删除
    /**
     * 查询评论
     * @param dto
     * @return
     */
    CommentResultInfoDto queryCommentByParam(CommentInfoDto dto);
    /**
     * 添加点赞数
     * @param commentId
     * @return
     */
    int addLikesCount(Long commentId);
    /**
     * 减少点赞数
     * @param commentId
     * @return
     */
    int subtractLikesCount(Long commentId);
}
