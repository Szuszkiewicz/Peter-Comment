package com.Peter.service;

import com.Peter.dto.SensitiveWordsDto;
import com.Peter.entity.SensitiveWordsExample;

import java.util.List;

/**
 * 敏感词过滤-service
 */
public interface SensitiveService {

    int insert(SensitiveWordsDto sensitiveWordsDto);

    List<SensitiveWordsDto> queryByParam(SensitiveWordsExample sensitiveWordsExample);



}
