package com.Peter.entity;

import lombok.Data;

import java.util.Date;
//字段与数据库表的列一一对应
//包含所有字段：id、userId、module、content、status、score、likeNum、isDelete、createTime、updateTime
//用于存储和传输完整数据
@Data
public class CommentEntity {
    private Long id;
    private Long userId;
    private Integer module;
    private Long resourceId;
    private String content;
    private Integer status;
    private Integer score;
    private Integer likeNum;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
}
