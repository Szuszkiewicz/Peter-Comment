package com.Peter.controller;

import com.Peter.Param.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @PostMapping(value = "/add")
    public BaseResult<Boolean> addComment(AddCommentRequestParam param)//声明请求参数，接受前段传来的评论添加数据
    {
         return  null;
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
