package com.Peter.service;

import com.Peter.Param.BaseResult;
import com.Peter.api.UserFeignService;
import com.Peter.dto.UserInfoDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserHttpService {
    @Resource
    private UserFeignService userFeignService;;

    public UserInfoDto getUserInfo(Long id){
        if(id == null){
            return null;
        }
        BaseResult<UserInfoDto> userInfoDtoBaseResult = userFeignService.queryUserInfoById(String.valueOf(id));
        if(!userInfoDtoBaseResult.getSuccess()){
            return null;
        }
        return userInfoDtoBaseResult.getData();
    }
}
