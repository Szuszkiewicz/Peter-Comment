package com.Peter.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//删除评论 前端到后端
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCommentRequestParam {
    private String commentId;
    private String userId;
    private Integer module;
    private String resourceId;

}
