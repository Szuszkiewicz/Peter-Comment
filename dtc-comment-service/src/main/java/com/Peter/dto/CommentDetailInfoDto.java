package com.Peter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//入参
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
