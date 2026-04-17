package com.Peter.controller;

import com.Peter.Param.*;
import com.Peter.dto.CommentDetailInfoDto;
import com.Peter.dto.CommentInfoDto;
import com.Peter.dto.CommentResultInfoDto;
import com.Peter.service.CommentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
        int count = commentService.addComment(dto);//影响的行数
         return  new BaseResult<>(0,true,"已发布评论",count>0);
    }
    //删除评论
    @DeleteMapping(value = "/delete")
    public BaseResult<Boolean> deleteComment(@RequestBody DeleteCommentRequestParam param)
    {
        CommentInfoDto dto = new CommentInfoDto();
        dto.setUserId(Long.valueOf(param.getUserId()));
        dto.setId(Long.valueOf(param.getCommentId()));
        dto.setModule(param.getModule());
        dto.setResourceId(Long.valueOf(param.getResourceId()));
        dto.setUpdateTime(new Date());
        int count = commentService.deleteComment(dto);
        return new BaseResult<>(0,true,"已删除评论",count>0);
    }
    //查询评论
    @GetMapping(value = "/query")
    public BaseResult<CommentResultParam> queryComment(QueryCommentRequestParam  param){
        log.info("查询评论-queryComment-入参：{}", param);
        CommentInfoDto commentInfoDto=buildCommentInfoDto(param);//封装查询参数
        CommentResultInfoDto resultInfoDto = commentService.queryCommentByParam(commentInfoDto);
        CommentResultParam resultParam=buildResultParam(resultInfoDto);
        log.info("查询结果-queryComment-出参：{}", resultParam);
        return new BaseResult<>(0,true,"查询成功",resultParam);
    }
   private CommentInfoDto buildCommentInfoDto(QueryCommentRequestParam param){
        if(param==null){
            return null;
        }
       CommentInfoDto commentInfoDto=new CommentInfoDto();
        commentInfoDto.setUserId( param.getUserId()!=null?Long.valueOf(param.getUserId()): null);
        commentInfoDto.setModule(param.getModule());
        commentInfoDto.setResourceId(param.getResourceId()!=null?Long.valueOf(param.getResourceId()): null);
        commentInfoDto.setScore(param.getScore());
        commentInfoDto.setOrder(param.getOrder());
        commentInfoDto.setPageNum(param.getPageNum());
        commentInfoDto.setPageSize(param.getPageSize());
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
         target.setCommentTime(null);
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
