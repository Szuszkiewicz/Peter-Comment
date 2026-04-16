package com.Peter.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CommentInfoDto {
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
