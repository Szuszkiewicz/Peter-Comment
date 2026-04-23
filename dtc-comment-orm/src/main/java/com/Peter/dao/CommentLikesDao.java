package com.Peter.dao;

import com.Peter.entity.CommentLikesExample;
import com.Peter.entity.CommentLikes;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CommentLikesDao {
    long countByExample(CommentLikesExample example);

    int deleteByExample(CommentLikesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentLikes record);

    int insertSelective(CommentLikes record);

    List<CommentLikes> selectByExample(CommentLikesExample example);

    CommentLikes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentLikes record, @Param("example") CommentLikesExample example);

    int updateByExample(@Param("record") CommentLikes record, @Param("example") CommentLikesExample example);

    int updateByPrimaryKeySelective(CommentLikes record);

    int updateByPrimaryKey(CommentLikes record);
}