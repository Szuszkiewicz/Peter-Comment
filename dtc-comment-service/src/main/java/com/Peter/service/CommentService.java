package com.Peter.service;

import com.Peter.dto.CommentInfoDto;
import com.Peter.dto.CommentResultInfoDto;
import com.Peter.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    int addComment(CommentInfoDto dto);
    int deleteComment(CommentInfoDto dto);//校验之后再删除，不能直接删除
    CommentResultInfoDto queryCommentByParam(CommentInfoDto dto);
}
