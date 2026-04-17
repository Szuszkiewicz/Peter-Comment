package com.Peter.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//查询评论，前端到后端
public class QueryCommentRequestParam {
    private String userId;//用户id
    private String content; //评论内容
    private Integer module;//模块
    private String resourceId;//资源 id
    private Integer score;//评分
    private Integer order;//排序方式1：最新2：最热3：最早
    private Integer condition;//评分
    private Integer pageSize;//评论数量
    private Integer replyNum;//回复数量
    private Integer pageNum;//页数
    private String orderBy;
}
