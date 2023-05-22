package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.Visitor;
import com.ldx.blog.service.VisitorService;
import com.ldx.blog.mapper.VisitorMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【visitor】的数据库操作Service实现
* @createDate 2023-05-15 23:17:28
*/
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor>
    implements VisitorService{

}




