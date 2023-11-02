package com.hzjt.platform.account.user.domain.handler;

import com.hzjt.platform.account.api.exception.AccountCenterException;
import com.hzjt.platform.account.api.model.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 * 功能描述：异常拦截
 *
 * @author zhanghaojie
 * @date 2022/7/29 10:19
 */

@Slf4j
@ControllerAdvice
public class ExceptionInterceptHandler {

    private static String SYSTEM_ERROR = "10001";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    private AccountResponse handlerErrorInfo(HttpServletRequest request, Exception e) {
        log.error("ExceptionInterceptHandler成功拦截 {} Exception", request.getRequestURI(), e);
//        operationLogAspect.doLog(JSON.toJSONString(e.getMessage()));
        if (e instanceof NullPointerException) {
            return AccountResponse.returnFail(SYSTEM_ERROR, "系统异常");
        } else if (e instanceof AccountCenterException) {
            return AccountResponse.returnFail(((AccountCenterException) e).getErrorCode(), e.getMessage());
        }
        return AccountResponse.returnFail(SYSTEM_ERROR, "系统异常");
    }
}
