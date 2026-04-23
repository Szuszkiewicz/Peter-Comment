package com.Peter.service;

import com.Peter.dto.LikesInfoDto;

public interface LikesService {
  /**
     * 点赞和取消点赞
     * @param likesInfoDto 点赞信息
     * @return
     */
    int set(LikesInfoDto likesInfoDto);
}
