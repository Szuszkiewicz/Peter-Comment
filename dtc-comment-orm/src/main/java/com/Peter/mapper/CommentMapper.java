package com.Peter.mapper;

import com.Peter.entity.CommentEntity;
import com.Peter.entity.CommentParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//用xml文件配置sql语句，动态生成sql语句，多表关联更清晰
@Mapper
public interface CommentMapper {
  //添加评论
  int addComment(CommentEntity commentEntity);
  //删除评论
  int deleteCommentById(Long Id);
  //查询评论
  List<CommentEntity> queryCommentByParam(CommentParam commentParam);
  //查询评论总数
  int countCommentCountByParam(CommentParam commentParam);

}
