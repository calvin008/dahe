package top.wdahe.food_system.common.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.wdahe.common.annotation.NeedPermission;
import top.wdahe.common.constant.Const;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.jwtConfig.JwtConfig;
import top.wdahe.food_system.common.session.SessionUtil;
import top.wdahe.service.RedisService;


@Component
@Slf4j
public class SystemApiInterceptor implements HandlerInterceptor {

    @Resource
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
            if((handler instanceof HandlerMethod)) {
                System.out.println("拦截器执行");
                NeedPermission needPermission = ((HandlerMethod) handler).getMethod().getAnnotation(NeedPermission.class);
                if(needPermission != null ) {
                    String token = SessionUtil.getToken(request);
                    if(StringUtils.isEmpty(token)) {
                        log.error("未携带token,拦截请求",request.getMethod(),request.getServletPath());
                        return  false;
                    }

                    //检测token有效性
                    if(!JwtConfig.verify(token)) {
                        log.error("未携带token,拦截请求",request.getMethod(),request.getServletPath());
                        throw ServiceException.CONST_token_is_not_validate;
                    }

                    Integer sysUserId = JwtConfig.getSysUserId(token);
                    if(StringUtils.isEmpty(token) || !SessionUtil.checkSysUserLogin(sysUserId)) {
                        System.out.println(sysUserId);
                        System.out.println(SessionUtil.checkSysUserLogin(sysUserId));
                        throw ServiceException.CONST_user_not_login;
                    }
                    //将认证后的sysUserId放到request属性中
                    request.setAttribute(Const.CONST_sys_user_id,sysUserId);

                    //检验权限
                    if(!SessionUtil.hasPermission(needPermission.value(),sysUserId)) {
                        log.error("后台请求没有权限[{}]请求路径[{}]:",needPermission.value(),request.getServletPath());
                        throw ServiceException.CONST_not_authorized;
                    }
                }
            }

            return true;
    }
}
