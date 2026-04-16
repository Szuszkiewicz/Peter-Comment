package com.Peter.service.Impl;

import com.Peter.dto.CommentInfoDto;
import com.Peter.entity.CommentEntity;
import com.Peter.mapper.CommentMapper;
import com.Peter.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public int addComment(CommentInfoDto dto) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(dto, commentEntity);//dto转entity
        int count=commentMapper.addComment(commentEntity);
        return count;
    }

}
