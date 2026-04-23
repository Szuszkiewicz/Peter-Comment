package com.Peter.service.Impl;

import com.Peter.dao.CommentLikesDao;
import com.Peter.dto.LikesInfoDto;
import com.Peter.entity.CommentLikes;
import com.Peter.entity.CommentLikesExample;
import com.Peter.mapper.CommentMapper;
import com.Peter.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService  {
    @Autowired
    private CommentLikesDao commentLikesDao;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int set(LikesInfoDto likesInfoDto) {
        //查询该用户是否点赞过该目标评论
        //因为 CommentLikesExample 是根据 CommentLikes 实体类自动生成的
        CommentLikesExample commentLikesExample = new CommentLikesExample();
        CommentLikesExample.Criteria criteria = commentLikesExample.createCriteria();
        criteria.andUserIdEqualTo(likesInfoDto.getUserId());
        criteria.andCommentIdEqualTo(likesInfoDto.getCommentId());

        /**
         * 查询条件：user_id = ? AND comment_id = ?
         * 如果该用户点赞过这条评论，会返回记录
         * 如果没点赞过，返回空列表
         */
        List<CommentLikes> commentLikes = commentLikesDao.selectByExample(commentLikesExample);
        if(!CollectionUtils.isEmpty(commentLikes)){
            //如果点赞过，那么取消点赞
            int delete=commentLikesDao.deleteByPrimaryKey(commentLikes.get(0).getId());
            if(delete>0){
                commentMapper.updateLikeNum(likesInfoDto.getCommentId(), -1);
                return 1;
            }
        }else {
            //如果没点赞过，那就点赞
            CommentLikes commentLike = new CommentLikes();
            commentLike.setUserId(likesInfoDto.getUserId());
            commentLike.setCommentId(likesInfoDto.getCommentId());
            commentLike.setCreatedAt(new Date());
            int insert= commentLikesDao.insert(commentLike);
            if(insert>0){
                commentMapper.updateLikeNum(likesInfoDto.getCommentId(), 1);
                return 1;
            }

        }
        //如果点赞过，那么取消点赞

        //如果没点赞过，那就点赞
        return -1;
    }


}
