package com.Peter.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExportSensitiveWordDto {
    @ExcelProperty("敏感词")
    private String word;
    @ExcelProperty("敏感词类型")
    private String category;
}
