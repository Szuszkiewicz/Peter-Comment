package com.Peter.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//增加敏感词入参
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSensitiveWordParam {
    //敏感词
    private String word;
    //敏感词类型
    private String category;
}
