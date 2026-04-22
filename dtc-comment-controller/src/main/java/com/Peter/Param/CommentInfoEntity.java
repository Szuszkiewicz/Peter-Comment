package com.Peter.Param;

import lombok.Data;

import java.util.List;

//评论详情信息 作用： 返回给前端的完整评论信息
@Data
public class CommentInfoEntity {
    private String userId;
    private String userName;
    private String commentId;
    private Integer module;
    private String resourceId;
    private Integer score;
    private String content;
    private String commentTime;//格式yyyy-MM-dd：HH:mm:ss
    private Integer likeNum;//点赞数
    private String avatar;
    private String username;
    //回复数
    private Integer replyNum;
    //状态
    private Integer status;//0不置顶1置顶
    //子回复列表
    private List<ReplyInfoEntity> replyList;

}
