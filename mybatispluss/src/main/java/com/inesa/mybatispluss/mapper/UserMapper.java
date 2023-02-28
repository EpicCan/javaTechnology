package com.inesa.mybatispluss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inesa.mybatispluss.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper//表明这是一个Mapper，也可以在启动类上加上包扫描
public interface UserMapper extends BaseMapper<User> {
}
//继承BaseMapper可以省略xml的编写
