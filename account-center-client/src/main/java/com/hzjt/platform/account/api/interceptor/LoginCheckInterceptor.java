package com.hzjt.platform.account.api.interceptor;

import com.hzjt.platform.account.api.AccountCenterUserService;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.service.RegistryInterceptedClassMethod;
import com.hzjt.platform.account.api.utils.AccountUserInfoUtil;
import com.hzjt.platform.account.api.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * CustomInterceptor
 * 功能描述：CustomInterceptor
 *
 * @author zhanghaojie
 * @date 2023/10/27 15:40
 */
@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor, Ordered {

    private final String ACCOUNT_TOKEN = "account-token";

    // 自定义跳转页面
    @Value("${account.login.location.url:#{\"http://192.168.31.173:8011/login.html\"}}")
    private String locationUrl;

    // 自定义跳转页面
    @Value("${account.login.autologinpage:#{false}}")
    private Boolean accountLoginAutoLoginPage;

    @Autowired
    private AccountCenterUserService accountCenterUserService;

    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    public LoginCheckInterceptor() {
        log.info("Interceptor: CustomInterceptor constructor is called");
    }

    private void initRequestMappingHandlerMapping() {
        if (requestMappingHandlerMapping == null) {
            requestMappingHandlerMapping = (RequestMappingHandlerMapping) SpringContextUtil.getBean(RequestMappingHandlerMapping.class);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        initRequestMappingHandlerMapping();
        // 在请求处理之前执行，可以进行身份验证、日志记录等操作
        // 如果返回false，则请求将被拦截，不会被继续处理
        System.out.println("Interceptor: preHandle method is called");
        // 从请求头中获取token字符串并解析
        String accountToken = getAccountToken(request, response);

        Boolean ignoreUrl = isIgnoreUrl(findHandlerMethod(request));
        if (ignoreUrl && StringUtils.isEmpty(accountToken)) {
            return true;
        } else if (!StringUtils.isEmpty(accountToken)) {
            AccountUserInfo userInfo = getUserInfo(accountToken, request);
            if (Objects.isNull(userInfo) && accountLoginAutoLoginPage) {
                return requestNoLogin(request, response);
            } else if (Objects.isNull(userInfo) && !ignoreUrl) {
                return request302(request, response);
            }
            return true;
        }
        if (accountLoginAutoLoginPage) {
            return requestNoLogin(request, response);
        }
        // 设置302跳转
        return request302(request, response);
    }

    private void setCookie(HttpServletResponse response, String accountToken) {
        response.setHeader(ACCOUNT_TOKEN, accountToken);
        Cookie cookie = new Cookie(this.ACCOUNT_TOKEN, accountToken);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


        /**
     * 获取账户令牌
     * 从请求中获取账户令牌，顺序查找请求头、参数和Cookie
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 获取到的账户令牌
     */
    private String getAccountToken(HttpServletRequest request, HttpServletResponse response) {
        String accountToken;
        try {
            accountToken = request.getHeader(ACCOUNT_TOKEN);
            if (Objects.isNull(accountToken) || Objects.equals(accountToken, "")) {
                accountToken = request.getParameter(ACCOUNT_TOKEN);
            }
            Cookie[] cookies = request.getCookies();
            if (Objects.isNull(cookies)) {
                return accountToken;
            }
            for (Cookie cookiecookie : cookies) {
                if (Objects.equals(cookiecookie.getName(), ACCOUNT_TOKEN)) {
                    accountToken = cookiecookie.getValue();
                }
            }
            setCookie(response, accountToken);
        } catch (Exception e) {
            throw new RuntimeException("系统异常");
        }
        return accountToken;
    }


    private Boolean requestNoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应内容类型为 JSON
        response.setContentType("application/json;charset=UTF-8");
        // 要返回的 JSON 数据
        String jsonData = "{\"code\": 10002,\"msg\": \"请先登录\"}";
        // 获取输出流
        PrintWriter out = response.getWriter();
        // 将 JSON 数据写入输出流，发送给客户端
        out.println(jsonData);
        return false;
    }

    private Boolean request302(HttpServletRequest request, HttpServletResponse response) {
        String completeURLWithQueryString = request.getRequestURL().toString() + "?" + request.getQueryString();
        // 设置302跳转
        response.setStatus(HttpServletResponse.SC_FOUND);

        // requestURI为登录成功后再跳转的url
        try {
            response.sendRedirect(locationUrl + "?redirect=" + completeURLWithQueryString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private AccountUserInfo getUserInfo(String accountToken, HttpServletRequest request) {
        AccountUserInfo userInfo = accountCenterUserService.getUserInfoByToken(accountToken);
        // 获取用户信息
        if (Objects.isNull(userInfo)) {
            return null;
        }
        AccountUserInfoUtil.setUserInfo(userInfo);
        return userInfo;
    }

    public HandlerMethod findHandlerMethod(HttpServletRequest request) {
        try {
            HandlerExecutionChain handler = requestMappingHandlerMapping.getHandler(request);
            if (handler != null && handler.getHandler() instanceof HandlerMethod) {
                return (HandlerMethod) handler.getHandler();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后，视图渲染之前执行，可以修改ModelAndView中的数据
        log.info("Interceptor: postHandle method is called");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求处理完成之后执行，可以用来做一些资源清理操作
        log.info("Interceptor: afterCompletion method is called");
    }

    /**
     * 是不需要忽略的接口
     */
    private Boolean isIgnoreUrl(HandlerMethod handlerMethod) {
        if (Objects.isNull(handlerMethod)) {
            return true;
        }
        // 没有找到说明是不需要检验的接口
        for (Method method : RegistryInterceptedClassMethod.interceptedClassMethodList) {
            if (Objects.equals(method, handlerMethod.getMethod())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
