package com.Peter.controller;

import com.Peter.Param.*;
import com.Peter.dto.CommentInfoDto;
import com.Peter.service.CommentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public BaseResult<Boolean> deleteComment(DeleteCommentRequestParam param)
    {
        return null;
    }
    //查询评论
    @GetMapping(value = "/query")
    public BaseResult<CommentResultParam> queryComment(QueryCommentRequestParam  param){
        return null;
    }

}
