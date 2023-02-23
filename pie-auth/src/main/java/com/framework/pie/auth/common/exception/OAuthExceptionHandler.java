package com.framework.pie.auth.common.exception;

import com.framework.pie.constant.ResultCode;
import com.framework.pie.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OAuthExceptionHandler {
    /**
     * 用户不存在
     * @param e
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public HttpResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        return HttpResult.error("用户不存在");
    }

    /**
     * 用户名和密码异常
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidGrantException.class)
    public HttpResult handleInvalidGrantException(InvalidGrantException e) {
        return HttpResult.error("用户名或密码错误");
    }


    /**
     * 账户异常(禁用、锁定、过期)
     * @param e
     * @return
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public HttpResult handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return HttpResult.error(e.getMessage());
    }

    /**
     * 处理refresh_token登录过期异常
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidTokenException.class)
    public HttpResult handleInvalidTokenException(InvalidTokenException e){
        return HttpResult.error(ResultCode.REFRESH_TOKEN_INVALID_OR_EXPIRED.getCode()
                ,ResultCode.REFRESH_TOKEN_INVALID_OR_EXPIRED.getMsg());
    }

}
