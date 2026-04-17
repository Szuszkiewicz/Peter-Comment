package com.Peter.service.Impl;

import com.Peter.dto.CommentDetailInfoDto;
import com.Peter.dto.CommentInfoDto;
import com.Peter.dto.CommentResultInfoDto;
import com.Peter.entity.CommentEntity;
import com.Peter.entity.CommentParam;
import com.Peter.mapper.CommentMapper;
import com.Peter.service.CommentService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Override
    public int deleteComment(CommentInfoDto dto) {
        log.info("删除评论：-入参：{}", dto);
        //缺少参数信息校验
       int count= commentMapper.deleteCommentById(dto.getId());
        return count;
    }

    @Override
    public CommentResultInfoDto queryCommentByParam(CommentInfoDto dto) {
        log.info("查询评论-queryCommentByParam-入参:{}", dto);
        CommentResultInfoDto resultInfoDto= new CommentResultInfoDto();
        //检查参数是否合理
        checkParam(dto);
        //构建查询条件，用户可能需要根据不同条件组合查询:
        //只按模块查
        //只按用户ID查
        //按模块 + 时间范围查
        //按多个条件组合查
        CommentParam queryParam= buildQueryCommentParam(dto);
        //查询评论种数
        int total= commentMapper.countCommentCountByParam(queryParam);
        resultInfoDto.setTotal(Long.valueOf( total+""));
        if(total<=0){
            return resultInfoDto;
        }
        //查询评论结果
        List<CommentEntity> commentEntities= commentMapper.queryCommentByParam(queryParam);

        //组装结果集
        List<CommentDetailInfoDto> list=buildResultList(commentEntities);
        resultInfoDto.setList(list);
        log.info("查询评论-queryCommentByParam-结果:{}", resultInfoDto);
        return resultInfoDto ;
    }
         //构建结果集
    private List<CommentDetailInfoDto> buildResultList(List<CommentEntity> commentEntities) {
        if(CollectionUtils.isEmpty(commentEntities)){
            return new ArrayList<>();
        }
        List<CommentDetailInfoDto> resultInfoDtos= new ArrayList<>();
        for(int i=0;i<commentEntities.size();i++){
            CommentEntity commentEntity= commentEntities.get(i);
            if(commentEntity== null){
                continue;
            }
            CommentDetailInfoDto target=new CommentDetailInfoDto();
            BeanUtils.copyProperties(commentEntity, target);
            resultInfoDtos.add(target);
        }
        return resultInfoDtos;
    }


    private void checkParam(CommentInfoDto dto) {
        Assert.isTrue(dto!= null,"参数不能为空");
        Assert.isTrue(dto.getModule()!= null,"模块不能为空");
        Assert.isTrue(dto.getResourceId()!= null,"资源id不能为空");
       //补全分页信息
        if(dto.getPageNum()== null||dto.getPageSize()== null){
            dto.setPageNum(1);
            dto.setPageSize(10);
        }
    }
    private CommentParam buildQueryCommentParam(CommentInfoDto dto) {
     CommentParam commentParam= new CommentParam();
     commentParam.setModule(dto.getModule());
     commentParam.setResourceId(dto.getResourceId());
     commentParam.setLimit(dto.getPageSize());
     commentParam.setOffset(buildOffset(dto.getPageNum(), dto.getPageSize()));
     if(dto.getOrder()==null) {
         commentParam.setOrderBy("create_time");
         commentParam.setOrderDirection("desc");
     }else {
         if(dto.getOrder()==2){
             commentParam.setOrderBy("like_num");
             commentParam.setOrderDirection("desc");
         }else{
             commentParam.setOrderBy("create_time");
             commentParam.setOrderDirection("asc");
         }
     }
     return commentParam;
    }

    private Integer buildOffset(Integer pageNum, Integer pageSize) {
        Integer offset= (pageNum-1)*pageSize;//获取跳过的记录数
        return Math.max(offset, 0);
    }

}
