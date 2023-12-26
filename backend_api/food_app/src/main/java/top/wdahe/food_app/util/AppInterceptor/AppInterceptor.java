package top.wdahe.food_app.util.AppInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.wdahe.common.annotation.NeedLogin;
import top.wdahe.common.constant.Const;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.jwtConfig.JwtConfig;
import top.wdahe.food_app.util.session.SessionUtil;

import java.io.IOException;

@Component
@Slf4j
public class AppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws  Exception {
        if(handler instanceof HandlerMethod) {
            NeedLogin annotation = ((HandlerMethod) handler).getMethod().getAnnotation(NeedLogin.class);
            if(annotation != null) {
                //获取用户token
                String token = SessionUtil.getToken(request);
                if(StringUtils.isEmpty(token)) {
                    log.error("未携带token,拦截请求路径",request.getMethod(),request.getServletPath());
                    return false;
                }

                //验证token
                if(!JwtConfig.verify(token))
                    throw ServiceException.CONST_token_is_not_validate;

                //验证用户是否已经登录
                String wxOpenid = JwtConfig.getWxOpenid(token);
                if(!SessionUtil.checkUserLogin(wxOpenid)) {
                    log.error("拦截未登录的请求路径",request.getMethod(),request.getServletPath());
                    renderResponse(request,response);
                    return false;
                }

                //把wxOpenid放到request属性里面
                request.setAttribute(Const.CONST_wx_openid,wxOpenid);
            }
        }
        return true;
    }

    private void renderResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("content-type","application/json;charset=UTF-8");
        response.getWriter().println("""
                {"code": 10001,"message":"未登录或者登录已经过期","data":null}
                """);
        response.flushBuffer();
    }
}
