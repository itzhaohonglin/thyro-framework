package com.thyro.framework.web;

import com.thyro.framework.common.exception.ResultCode;
import com.thyro.framework.common.result.ResultWrapper;
import com.thyro.framework.web.util.ServletUtil;
import com.thyro.framework.web.util.WebFrameworkUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 演示 Filter，禁止用户发起写操作，避免影响测试数据
 */
public class DemoFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        String[] denyMethod = new String[]{ServletUtil.METHOD_POST, ServletUtil.METHOD_PUT, ServletUtil.METHOD_DELETE}; // 写操作时，不进行过滤率
        for (String method : denyMethod) {
            if (method.equalsIgnoreCase(requestMethod)) {
                return false;
            }
        }
        return WebFrameworkUtils.getLoginUserId(request) == null; // 非登录用户时，不进行过滤
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        // 直接返回 DEMO_DENY 的结果。即，请求不继续
        ServletUtil.writeJSON(response, ResultWrapper.fail(ResultCode.DEMO_DENY));
    }

}
