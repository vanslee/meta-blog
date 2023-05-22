package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.CommentLikesRecord;
import com.ldx.blog.service.CommentLikesRecordService;
import com.ldx.blog.mapper.CommentLikesRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【comment_likes_record】的数据库操作Service实现
* @createDate 2023-05-15 23:17:28
*/
@Service
public class CommentLikesRecordServiceImpl extends ServiceImpl<CommentLikesRecordMapper, CommentLikesRecord>
    implements CommentLikesRecordService{

}




