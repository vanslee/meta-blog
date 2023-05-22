package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.Tags;
import com.ldx.blog.service.TagsService;
import com.ldx.blog.mapper.TagsMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2023-05-15 23:17:28
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

}




