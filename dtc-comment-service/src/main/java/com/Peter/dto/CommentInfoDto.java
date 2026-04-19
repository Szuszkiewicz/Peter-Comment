package com.Peter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
//在 Controller 和 Service 之间传递评论信息
//作为查询入参：封装前端传来的查询条件和分页参数,包含分页信息
//作为单条评论数据：在 Service 层内部传递单条评论的基础信息
public class CommentInfoDto {
    private Long id;
    private Long userId;
    private Integer module;
    private Long resourceId;
    private String content;
    private Integer status;
    private Integer score;
    private Integer order;//排序方式1：最新2：最热3：最早
    private Integer likeNum;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
    private Integer pageNum;
    private Integer pageSize;


}
