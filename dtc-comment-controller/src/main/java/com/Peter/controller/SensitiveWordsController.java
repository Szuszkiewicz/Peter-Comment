package com.Peter.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.Peter.Param.AddSensitiveWordParam;
import com.Peter.Param.BaseResult;
import com.Peter.dto.SensitiveWordsDto;
import com.Peter.entity.SensitiveWordsExample;
import com.Peter.service.SensitiveService;
import com.Peter.utils.BaseResultUtils;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

//敏感词
@Slf4j
@RestController
@RequestMapping("/sensitivewords")
public class SensitiveWordsController {
    @Autowired
    private SensitiveService sensitiveService;
    @PostMapping(value = "/add")
    public BaseResult<Boolean> addWord(@RequestBody AddSensitiveWordParam param) {
        try {
            log.info("增加敏感词-controller-addWord:入参:{}", JSON.toJSONString(param));
            //参数校验
            checkParam(param);
            SensitiveWordsDto sensitiveWordsDto=new SensitiveWordsDto();
            sensitiveWordsDto.setWord(param.getWord());
            sensitiveWordsDto.setCategory(param.getCategory());
            //敏感词落库
            int insert=sensitiveService.insert(sensitiveWordsDto);
            log.info("增加敏感词-controller-addWord:出参:{}", insert>0);
            return BaseResultUtils.generateSuccess(insert>0);
        } catch (Exception e) {
            log.error("增加敏感词-controller-addWord:异常:{}", e);
            return BaseResultUtils.generateError("添加敏感词失败");

        }

    }
    /**
     * 批量导入敏感词
     * * @param param
     */
    @RequestMapping(value = "/import")
    public BaseResult<Boolean> importWords(@RequestBody MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<List<Object>> list=reader.read(1);
            /**
             * List<List<Object>> 是二维列表
             * 外层 List：每一行数据
             * 内层 List：每行中的每个单元格
             */
            int total=0;
            for(int i=0;i<list.size();i++){
                List<Object> blocks=list.get(i);
                SensitiveWordsDto sensitiveWordsDto=new SensitiveWordsDto();
                sensitiveWordsDto.setWord(blocks.get(0).toString());
                sensitiveWordsDto.setCategory(blocks.get(1).toString());
               // todo 后续可以优化为批量插入
                int count=sensitiveService.insert(sensitiveWordsDto);
                total+=count;
            }
          log.info("批量导入敏感词-controller-importWords:插入敏感词数量:{}", total);
          return BaseResultUtils.generateSuccess(total>0);
        } catch (Exception e) {
            log.error("批量导入敏感词-controller-importWords:异常:", e);
            return BaseResultUtils.generateError("批量导入敏感词失败");
        }
    }
    /**
     *
     * 批量导出敏感词
     */
    @GetMapping(value = "/export")
    public BaseResult<Boolean> exportWords(HttpServletResponse  response) {
        try {
            SensitiveWordsExample example=new SensitiveWordsExample();
            example.setLimit(1000);
            //查询数据
            List<SensitiveWordsDto> sensitiveWordsDtos=sensitiveService.queryByParam(example);

            ExcelWriter writer=ExcelUtil.getWriter();
            writer.write(sensitiveWordsDtos,true);

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=sensitive_words.xlsx");

            ServletOutputStream outputStream = response.getOutputStream();
            writer.flush(outputStream,true);
            writer.close();

            return BaseResultUtils.generateSuccess(true);
         } catch (Exception e) {
            log.error("批量导出敏感词-controller-exportWords:异常:", e);
            return BaseResultUtils.generateError("批量导出敏感词失败");
        }

    }

    /**
     * 参数校验
     * @param param
     */
    private void checkParam(AddSensitiveWordParam param) {
        Assert.isTrue(param!=null,"入参不能为空");
        Assert.isTrue(StringUtils.isNotBlank(param.getWord()),"敏感词不能为空");
        Assert.isTrue(StringUtils.isNotBlank(param.getCategory()),"敏感词类型不能为空");
    }
}