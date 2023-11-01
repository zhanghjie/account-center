package com.hzjt.platform.account.api.interceptor;

import com.hzjt.platform.account.api.AccountUserPermission;
import com.hzjt.platform.account.api.exception.AccountCenterException;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.utils.AccountUserInfoUtil;
import com.hzjt.platform.account.api.utils.SpringContextUtil;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * PermissionsInterceptor
 * 功能描述：PermissionsInterceptor
 *
 * @author zhanghaojie
 * @date 2023/10/31 19:33
 */
@Component
public class PermissionsInterceptor implements HandlerInterceptor, Ordered {


    private List<AccountUserPermission> permissionList;

    @EventListener(ContextRefreshedEvent.class)
    private void initPermissionList() {
        permissionList = SpringContextUtil.getListBean(AccountUserPermission.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccountUserInfo accountUserInfo = AccountUserInfoUtil.getUserInfo();
        if (CollectionUtils.isEmpty(permissionList)) {
            return true;
        }
        for (AccountUserPermission accountUserPermission : permissionList) {
            try {
                // 如果只是返回
                if (!accountUserPermission.verifyThatTheUserHasPermissions(accountUserInfo)) {
                    return requestError(response, "10001", "系统异常");
                }
            } catch (AccountCenterException ac) {
                return requestError(response, ac.getErrorCode(), ac.getMessage());
            }
        }
        // todo 实现权限校验

        return true;
    }

    private Boolean requestError(HttpServletResponse response, String code, String message) throws Exception {
        // 设置响应内容类型为 JSON
        response.setContentType("application/json;charset=UTF-8");
        // 要返回的 JSON 数据
        StringBuilder builder = new StringBuilder("{\"code\": ");
        builder.append(code);
        builder.append(",\"msg\": \"");
        builder.append(message);
        builder.append("\"}");
        // 获取输出流
        PrintWriter out = response.getWriter();
        // 将 JSON 数据写入输出流，发送给客户端
        out.println(builder.toString());
        return false;
    }


    @Override
    public int getOrder() {
        return 2;
    }
}
