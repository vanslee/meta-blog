package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.UserRole;
import com.ldx.blog.service.UserRoleService;
import com.ldx.blog.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2023-05-15 23:17:28
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




