package com.Peter.Param;

import lombok.Data;

//回复信息
@Data
public class ReplyInfoEntity {
    private String userId;
    private String replyId;
    private String content;
    private Integer module;
    private String username;
    private Integer likeNum;
    private String replyTime;//格式yyyy-MM-dd：HH:mm:ss
    private String avatar;
    private String repliedName;
    private String repliedId;
    private Integer status;//0不置顶，1置顶

}
