package com.Peter.controller;

import com.Peter.Param.AddSensitiveWordParam;
import com.Peter.Param.BaseResult;
import com.Peter.dto.SensitiveWordsDto;
import com.Peter.service.SensitiveService;
import com.Peter.utils.BaseResultUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 参数校验
     * @param param
     */
    private void checkParam(AddSensitiveWordParam param) {
        Assert.isTrue(param!=null,"入参不能为空");
        Assert.isTrue(StringUtils.isNotBlank(param.getWord()),"敏感词不能为空");
        Assert.isTrue(StringUtils.isNotBlank(param.getCategory()),"敏感词类型不能为空");
    }
}