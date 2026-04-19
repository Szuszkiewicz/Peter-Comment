package com.Peter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//代表一条完整的评论记录，用于返回给上层
//包含评论的所有基础字段（id、userId、content、score 等）
//没有分页和排序字段（pageNum、pageSize、order）
//是数据库 CommentEntity 的直接映射对象
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDetailInfoDto {
    private Long total;
    private List<CommentInfoDto> list;
    private Long id;
    private Long userId;
    private Integer module;
    private Long resourceId;
    private String content;
    private Integer status;
    private Integer score;
    private Integer likeNum;
    private Integer isDelete;
    private java.util.Date createTime;
    private java.util.Date updateTime;


}
