package com.thyro.framework.web.util;

import com.thyro.framework.common.exception.BizException;
import com.thyro.framework.common.exception.ResultCode;
import com.thyro.framework.common.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet工具类
 */
@Slf4j
public class ServletUtil {

    private ServletUtil() {

    }

    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";

    public static Map<String, String[]> getParams(ServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    public static Map<String, String> getParamMap(ServletRequest request) {
        Map<String, String> params = new HashMap();

        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
        }

        return params;
    }

    public static String getBody(ServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            String var3 = IOUtils.toString(reader);
            return var3;
        } catch (IOException e) {
            throw new BizException(ResultCode.BIZ_ERROR);
        }
    }

    public static byte[] getBodyBytes(ServletRequest request) {
        try {
            return IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new BizException(ResultCode.BIZ_ERROR);
        }
    }


    public static String getClientIP(HttpServletRequest request) {
        // String[] headers = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String strXff = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(strXff) || "unknown".equals(strXff)) {
            return request.getRemoteAddr();
        }
        String[] strIPs = strXff.split(",");
        for (int i = 0, n = strIPs.length; i < n; i++) {
            strIPs[i] = strIPs[i].trim();
            if (StringUtils.isNotEmpty(strIPs[i]) && !"unknown".equals(strIPs[i])) {
                return strIPs[i];
            }
        }
        return request.getRemoteAddr();
    }

    public static String getClientIP() {
        try {
            HttpServletRequest request = getRequest();
            return getClientIP(request);
        } catch (Exception e) {
            try {
                InetAddress localHost = Inet4Address.getLocalHost();
                return localHost.getHostAddress();
            } catch (UnknownHostException unknownHostException) {

            }
        }
        return "127.0.0.1";
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static boolean isJsonRequest(ServletRequest request) {
        return request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE);
    }

    public static byte[] getBodyBytes(HttpServletRequest request) {
        // 只有在 json 请求在读取，因为只有 CacheRequestBodyFilter 才会进行缓存，支持重复读取
        if (isJsonRequest(request)) {
            return ServletUtil.getBodyBytes(request);
        }
        return null;
    }

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object   对象，会序列化成 JSON 字符串
     */
    @SuppressWarnings("deprecation") // 必须使用 APPLICATION_JSON_UTF8_VALUE，否则会乱码
    public static void writeJSON(HttpServletResponse response, Object object) {
        String content = JsonUtils.toJsonString(object);
        ServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        Writer writer = null;

        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new BizException(ResultCode.SYSTEM_ERROR.getCode(), "IO操作异常");
        } finally {
            try {
                IOUtils.close(writer);
            } catch (IOException e) {
                throw new BizException(ResultCode.SYSTEM_ERROR.getCode(), "IO关闭异常");
            }
        }

    }
}
