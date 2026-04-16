package com.Peter.mapper;

import com.Peter.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
//用xml文件配置sql语句，动态生成sql语句，多表关联更清晰
@Mapper
public interface CommentMapper {
  int addComment(CommentEntity commentEntity);

}
