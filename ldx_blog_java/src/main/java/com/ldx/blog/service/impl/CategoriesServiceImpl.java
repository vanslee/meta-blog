package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.CategoriesMapper;
import com.ldx.blog.pojo.Categories;
import com.ldx.blog.service.CategoriesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ldx
* @description 针对表【categories】的数据库操作Service实现
* @createDate 2023-05-22 20:47:41
*/
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories>
    implements CategoriesService{
    @Resource
    private CategoriesMapper categoriesMapper;

    public IPage<Categories> getTagsPage(Integer current, Integer size) {
        IPage<Categories> iPage = new Page<>(current, size);
        page(iPage);
        return iPage;
    }
}




