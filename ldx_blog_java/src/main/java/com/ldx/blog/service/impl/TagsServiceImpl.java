package com.ldx.blog.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.TagsMapper;
import com.ldx.blog.pojo.Tags;
import com.ldx.blog.service.TagsService;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2023-05-22 20:47:28
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

    public IPage<Tags> getTagsPage(Integer current, Integer size) {
        IPage<Tags> iPage = new Page<>(current,size);
        page(iPage);
        return iPage;
    }
}




