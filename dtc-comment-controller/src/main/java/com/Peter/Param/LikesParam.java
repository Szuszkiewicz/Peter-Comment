package com.Peter.Param;

import lombok.Data;

import java.util.Date;

/**
 * 点赞入参实体类
 * @author Peter
 *
 */
@Data
public class LikesParam {


    /**
     * 点赞主键ID
     */
    private Integer id;
    /**
     * 目标ID
     */
    private Long commentId;
    /**
     * 点赞用户ID
     */
    private Long userId;
    /**
     * 点赞时间
     */
    private Date createdAt;

}
