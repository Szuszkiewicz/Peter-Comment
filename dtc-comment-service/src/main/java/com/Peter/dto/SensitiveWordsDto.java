package com.Peter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 敏感词DTO
 */
public class SensitiveWordsDto {
    /**
     * 敏感词
     */
    private String word;
    /**
     * 类别
     */

    private String category;
}
