package com.Peter.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//增加评论，前端到后端
public class AddCommentRequestParam {
    private String userId;//用户id
    private String content; //评论内容
    private Integer module;//模块
    private String resourceId;//资源 id
    private Integer score;//评分
}
