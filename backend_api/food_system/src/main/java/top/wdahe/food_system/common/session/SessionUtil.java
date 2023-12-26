package top.wdahe.food_system.common.session;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.wdahe.common.constant.Const;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.GeneratorUtil;
import top.wdahe.common.util.jwtConfig.JwtConfig;
import top.wdahe.common.util.spring.SpringContextUtil;
import top.wdahe.food_system.entity.dto.SysUserDTO;
import top.wdahe.service.RedisService;

import java.util.Set;

@Slf4j
public class SessionUtil {
    private static  final RedisService redisService = SpringContextUtil.getBeanByClass(RedisService.class);

    public static void setSysUserSession(SysUserDTO sysUserDTO) {
        sysUserDTO.setToken(JwtConfig.generateTokenWithUserId(sysUserDTO.getId(),GeneratorUtil.generateExpireTime(Const.CONST_half_month)));

        redisService.hset(Const.CONST_sys_user_session_map,sysUserDTO.getId().toString(),sysUserDTO);
    }

    public static Integer getCurrentSysUserId(HttpServletRequest request) {
        return Integer.valueOf(request.getAttribute(Const.CONST_sys_user_id).toString());
    }
    public static SysUserDTO getCurrentSysUserDTO(Integer sysUserId) {
        Object o = redisService.hget(Const.CONST_sys_user_session_map,sysUserId.toString());
        if(o != null)
            return (SysUserDTO) o;

        return  null;
    }
    public static SysUserDTO getCurrentSysUserDTO(HttpServletRequest request) {


        Object o = redisService.hget(Const.CONST_sys_user_session_map,getCurrentSysUserId(request).toString());

        if(o != null)
            return (SysUserDTO) o;

        return  null;
    }

    //获取request请求中的token
    public static String getToken(HttpServletRequest request) throws ServiceException {
        String token = request.getParameter(Const.CONST_token);
        if(StringUtils.isEmpty(token))
            token = request.getHeader(Const.CONST_token);
        if(token == null)
            throw ServiceException.CONST_user_not_login;

        return token;

    }

    //检测管理员是否登录
    public static boolean checkSysUserLogin(Integer sysUserId) {
        return  redisService.hHasKey(Const.CONST_sys_user_session_map,sysUserId.toString());
    }

    public static boolean hasPermission(String needPermission, Integer sysUserId) {
        Set<String> permissions = getCurrentSysUserDTO(sysUserId).getPermissions();
        if(StringUtils.isEmpty(needPermission))
            return true;

        if(permissions.contains(needPermission))
            return true;
        for(String userPermission : permissions)
            if(needPermission.startsWith(userPermission) || "*".equals(userPermission))
                return true;


        return false;
    }
    //退出登录
    public static boolean logout(HttpServletRequest request){
        return redisService.hdel(Const.Const_user_session_map,request.getAttribute(Const.CONST_sys_user_id));
    }

}
