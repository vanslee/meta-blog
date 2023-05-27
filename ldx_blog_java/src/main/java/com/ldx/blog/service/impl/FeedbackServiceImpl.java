package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.Feedback;
import com.ldx.blog.service.FeedbackService;
import com.ldx.blog.mapper.FeedbackMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【feedback】的数据库操作Service实现
* @createDate 2023-05-15 23:17:28
*/
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback>
    implements FeedbackService{

}




