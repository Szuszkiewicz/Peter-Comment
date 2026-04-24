package com.Peter.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.Peter.Param.AddSensitiveWordParam;
import com.Peter.Param.BaseResult;
import com.Peter.dto.ExportSensitiveWordDto;
import com.Peter.dto.SensitiveWordsDto;
import com.Peter.entity.SensitiveWordsExample;
import com.Peter.service.SensitiveService;
import com.Peter.utils.BaseResultUtils;
import com.Peter.utils.DateUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//敏感词
@Slf4j
@RestController
@RequestMapping("/sensitivewords")
public class SensitiveWordsController {
    /**
     * 导出最大限制1000
     */
    private static final int EXPORT_MAX_LIMIT=1000;
    private static final String EXPORT_SUFFIX=".xlsx";
    /**
     * Excel文档的后缀
     */
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
    @Deprecated
    @GetMapping(value = "/exportOld")
    public BaseResult<Boolean> exportWordsOld(HttpServletResponse  response) {
        try {
            SensitiveWordsExample example=new SensitiveWordsExample();
            example.setLimit(1000);
            //查询数据
            //todo 不能一次查询全部，要分页查询
            List<SensitiveWordsDto> sensitiveWordsDtos=sensitiveService.queryByParam(example);

            ExcelWriter writer=ExcelUtil.getWriter();
            writer.write(sensitiveWordsDtos,true);
            //todo 响应头显示中文
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=sensitive_words.xlsx");
            //todo 文件名称增加区分
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
     * 批量导出敏感词
     */
    @GetMapping(value = "/export")
    public void exportWords(@RequestParam(value = "category",required = false) String category ,HttpServletResponse  response) {
        try {
            SensitiveWordsExample example=new SensitiveWordsExample();
            SensitiveWordsExample.Criteria criteria=example.createCriteria();
            if(StringUtils.isNotBlank(category)){
                criteria.andCategoryEqualTo(category);
            }//如果为空,则查询所有
            criteria.andCategoryEqualTo(category);
            example.setLimit(EXPORT_MAX_LIMIT);
            List<SensitiveWordsDto> sensitiveWordsDtos=sensitiveService.queryByParam(example);
            //从数据库实体类转化为导出实体类
            List<ExportSensitiveWordDto> exportSensitiveWordDtos=buildExportSensitiveWordDtos(sensitiveWordsDtos);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            //文件名
            String fileName = URLEncoder.encode("导出敏感词"+ DateUtils.getCurrentTimeStr()+EXPORT_SUFFIX, StandardCharsets.UTF_8);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            //用内容填充输出流
            EasyExcel.write(response.getOutputStream(), ExportSensitiveWordDto.class).sheet("敏感词").doWrite(exportSensitiveWordDtos);
        } catch (Exception e) {
            log.error("批量导出敏感词-controller-exportWords:异常:", e);
        }

    }

    private List<ExportSensitiveWordDto> buildExportSensitiveWordDtos(List<SensitiveWordsDto> sensitiveWordsDtos) {
     if(CollectionUtils.isEmpty(sensitiveWordsDtos)){
         return new ArrayList<>();
     }
     List<ExportSensitiveWordDto> targetList=new ArrayList<>();
     for (int i=0;i<sensitiveWordsDtos.size();i++) {
           SensitiveWordsDto source= sensitiveWordsDtos.get(i);
           ExportSensitiveWordDto target=new ExportSensitiveWordDto();
            BeanUtils.copyProperties(source,target);
            targetList.add(target);
        }
     return targetList;
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