package com.Peter.entity;

import lombok.Data;

import java.util.Date;
//数据传输 - 用于传递评论数据 接收前端传来的筛选、分页、排序条件
//包含查询相关字段 + 分页排序字段,包含分页内容
//额外字段：limit（每页条数）、offset（偏移量）、orderBy（排序字段）、orderDirection（排序方向）
//字段可以为 null，表示不作为查询条件
@Data
public class CommentParam {
    private Long id;//评论id
    private Long userId;//用户id
    private Integer module;//模块
    private Long resourceId;//资源 id
    private String content;//评论内容
    private Integer status;//状态1：置顶0：正常
    private Integer score;//评分
    private Integer likeNum;//点赞数
    private Integer isDelete;//是否删除1：删除0：正常
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private Integer limit;//限制查询返回的条数
    private Integer offset;//偏移量，跳过的条数
    private String orderBy;//排序
    private String orderDirection;//正序acs,倒序desc
}
