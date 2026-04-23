package com.Peter.dto;

import lombok.Data;
@Data
public class LikesInfoDto {
    /**
     * 点赞主键ID
     */
   private Integer id;

   /**
    * 目标ID
    */
   private Long CommentId;

   /**
    * 点赞用户ID
    */
    private Long userId;

}
