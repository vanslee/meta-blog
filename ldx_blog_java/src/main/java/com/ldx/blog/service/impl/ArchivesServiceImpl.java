package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.Archives;
import com.ldx.blog.service.ArchivesService;
import com.ldx.blog.mapper.ArchivesMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【archives】的数据库操作Service实现
* @createDate 2023-05-15 23:17:28
*/
@Service
public class ArchivesServiceImpl extends ServiceImpl<ArchivesMapper, Archives>
    implements ArchivesService{

}




