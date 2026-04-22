package com.Peter.Param;

import lombok.Data;

import java.util.List;
//分页评论的结果，后端到前端
@Data
public class CommentResultParam {
    private Long total;//分页总数
    private List<CommentInfoEntity> list;//分页list
}
