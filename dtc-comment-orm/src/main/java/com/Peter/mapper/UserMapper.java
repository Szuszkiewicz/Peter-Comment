package com.Peter.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int countUserTotal();

}
