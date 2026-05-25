package com.Peter.utils;

import com.Peter.common.Constants;
import com.Peter.dto.UserInfoDto;
import com.Peter.dto.UserTokenInfoDto;
import com.Peter.rpc.UserRpcService;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Token工具类
 */
@Component
@Slf4j
public class TokenUtils {


    private static UserRpcService staticUserService;

    @Autowired
    UserRpcService userRpcService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userRpcService;
    }


    public static String getUsername() {
        return getCurrentUser().getUsername();
    }
    public static String getAvatar() {
        return getCurrentUser().getAvatar();
    }

    public static Long getUserId() {
        return getCurrentUser().getId();
    }

    /**
     * 获取当前登录的用户信息
     */
    public static UserInfoDto getCurrentUser() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if(requestAttributes==null){
                return new UserInfoDto();
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String token = request.getHeader(Constants.TOKEN);
            if (StringUtils.isNotBlank(token)) {
                UserTokenInfoDto userTokenInfoDto = JSONObject.parseObject(JWT.decode(token).getAudience().get(0), UserTokenInfoDto.class);
                UserInfoDto result = new UserInfoDto();
                BeanUtils.copyProperties(userTokenInfoDto, result);
                return result;
            }
        } catch (Exception e) {
            log.error("获取当前用户信息出错", e);
        }
        return new UserInfoDto();  // 返回空的账号对象
    }
}

