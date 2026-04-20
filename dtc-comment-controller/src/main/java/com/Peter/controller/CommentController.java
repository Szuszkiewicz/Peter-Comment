package com.Peter.controller;

import com.Peter.Param.*;
import com.Peter.dto.CommentDetailInfoDto;
import com.Peter.dto.CommentInfoDto;
import com.Peter.dto.CommentResultInfoDto;
import com.Peter.enums.CommentDeleteEnum;
import com.Peter.service.CommentService;
import com.Peter.utils.BaseResultUtils;
import com.Peter.utils.DateUtils;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping(value = "/add")
    public BaseResult<Boolean> addComment(@RequestBody AddCommentRequestParam param)//声明请求参数，接受前段传来的评论添加数据
    {
        try {
            log.info("增加评论-controller层-addComment-入参:{}", JSON.toJSONString(param));
            checkParam(param);
            CommentInfoDto dto = buildCommentInfoDto(param);
            int count = commentService.addComment(dto);//影响的行数
            log.info("增加评论-controller层-addComment-出参：{}", count);
            return BaseResultUtils.generateSuccess(count>0);
        }catch (Exception e){
        log.error("增加评论-controller层-addComment-异常:", e);
        return BaseResultUtils.generateError("增加评论异常:"+e.getMessage());
            }
        }

    private static @NonNull CommentInfoDto buildCommentInfoDto(AddCommentRequestParam param) {
        CommentInfoDto dto = new CommentInfoDto();
        dto.setUserId(Long.valueOf(param.getUserId()));
        dto.setModule(param.getModule());
        dto.setResourceId(Long.valueOf(param.getResourceId()));
        dto.setContent(param.getContent());
        dto.setStatus(0);
        dto.setScore(param.getScore());
        dto.setLikeNum(0);
        dto.setIsDelete(0);
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        return dto;
    }

    //删除评论
    @DeleteMapping(value = "/delete")
    public BaseResult<Boolean> deleteComment(@RequestBody DeleteCommentRequestParam param)
    {  
        try {
            log.info("删除评论-controller层-deleteComment-入参：{}", JSON.toJSONString(param));
            //缺少参数校验，用户信息校验
            checkDeleteCommentParam(param);
            CommentInfoDto dto = getCommentInfoDto(param);
            int count = commentService.deleteComment(dto);
            log.info("删除评论-controller层-deleteComment-出参：{}",count);
            if(count<=0){
                return BaseResultUtils.generateError("删除评论失败");
            }
            return BaseResultUtils.generateSuccess(count>0);
        }catch (Exception e){
            log.error("删除评论-controller层-deleteComment-异常:", e);
            return BaseResultUtils.generateError("删除评论异常");
        }
    }

    private void checkDeleteCommentParam(DeleteCommentRequestParam param) {
        Assert.isTrue(param!= null,"入参不能为空");
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(param.getUserId()),"用户id不能为空");
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(param.getCommentId()),"评论id不能为空");
        Assert.isTrue(param.getModule()!=null,"模块不能为空");
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(param.getResourceId()),"资源id不能为空");
    }

    private static @NonNull CommentInfoDto getCommentInfoDto(DeleteCommentRequestParam param) {
        CommentInfoDto dto = new CommentInfoDto();
        dto.setUserId(Long.valueOf(param.getUserId()));
        dto.setId(Long.valueOf(param.getCommentId()));
        dto.setModule(param.getModule());
        dto.setResourceId(Long.valueOf(param.getResourceId()));
        dto.setUpdateTime(new Date());
        dto.setIsDelete(CommentDeleteEnum.DELETE.getCode());
        return dto;
    }

    //查询评论
    @GetMapping(value = "/query")
    public BaseResult<CommentResultParam> queryComment(QueryCommentRequestParam  param){
        try {
            log.info("查询评论-controller层-queryComment-入参：{}", JSON.toJSONString( param));
            CommentInfoDto commentInfoDto = buildCommentInfoDto(param);//封装查询参数
            CommentResultInfoDto resultInfoDto = commentService.queryCommentByParam(commentInfoDto);
            CommentResultParam resultParam = buildResultParam(resultInfoDto);
            log.info("查询评论-controller层-queryComment-出参：{}", resultParam);
            return BaseResultUtils.generateSuccess(resultParam);
        }catch (Exception e){
            log.error("查询评论-controller层-queryComment-异常:", e);
            return BaseResultUtils.generateError("查询评论异常");
        }
    }
    private void checkParam(AddCommentRequestParam  param){
        Assert.isTrue(param!= null,"入参不能为空");
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(param.getUserId()),"用户id不能为空");//\t \n也不可以
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(param.getResourceId()),"资源id不能为空");
        Assert.isTrue(param.getModule()!=null,"模块不能为空");
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(param.getContent()),"内容不能为空");
    }
    private CommentInfoDto buildCommentInfoDto(QueryCommentRequestParam param){
        if(param==null){
            return null;
        }
        CommentInfoDto commentInfoDto=new CommentInfoDto();
        commentInfoDto.setUserId(param.getUserId()!=null?Long.valueOf(param.getUserId()): null);
        commentInfoDto.setModule(param.getModule());
        commentInfoDto.setResourceId(param.getResourceId()!=null?Long.valueOf(param.getResourceId()): null);
        commentInfoDto.setScore(param.getScore());
        commentInfoDto.setOrder(param.getOrder());
        commentInfoDto.setPageNum(param.getPageNum());
        commentInfoDto.setPageSize(param.getPageSize());
        commentInfoDto.setIsDelete(CommentDeleteEnum.NORMAL.getCode());
        return commentInfoDto;
   }
   private CommentResultParam buildResultParam(CommentResultInfoDto resultInfoDto){
        if(resultInfoDto==null){
            return null;
        }
        CommentResultParam resultParam=new CommentResultParam();
        resultParam.setTotal(resultInfoDto.getTotal());
        resultParam.setList(buildCommentInfoEntityList(resultInfoDto.getList()));
        return resultParam;
   }
   private List<CommentInfoEntity> buildCommentInfoEntityList(List<CommentDetailInfoDto> list) {
     if(CollectionUtils.isEmpty(list)){
         return new ArrayList<>();
     }
     List<CommentInfoEntity> resultList= new ArrayList<>();
     for(int i=0;i<list.size();i++){
         CommentDetailInfoDto source=list.get(i);
         if(source==null){
             continue;
         }
       CommentInfoEntity target=new CommentInfoEntity();
         target.setUserId(source.getUserId()+ "");//返回string不返回long
         target.setCommentId(source.getId()+ "");
         target.setModule(source.getModule());
         target.setResourceId(source.getResourceId()+ "");
         target.setContent(source.getContent());
         target.setCommentTime(DateUtils.date2Str(source.getCreateTime(), DateUtils.DATE_FORMAT));
         target.setLikeNum(source.getLikeNum());
         target.setAvatar(null);
         target.setUserName(null);
         target.setStatus(source.getStatus());
         target.setReplyList(null);
         resultList.add(target);
     }
        return resultList;
   }
}
