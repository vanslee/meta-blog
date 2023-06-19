package com.ldx.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ldx.blog.pojo.Categories;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.impl.CategoriesServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Uaena
 * @date 2023/6/18 18:22
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoriesServiceImpl categoriesService;
    @GetMapping("/list")
    public Result<IPage<Categories>> getCategoriesApi(@RequestParam("current") Integer current, @RequestParam("size") Integer size){
        return Result.success(categoriesService.getTagsPage(current,size));
    }

}
