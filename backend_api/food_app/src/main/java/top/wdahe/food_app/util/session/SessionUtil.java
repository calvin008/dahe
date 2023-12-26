package top.wdahe.food_app.util.session;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import top.wdahe.common.constant.Const;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.generator.GeneratorUtil;
import top.wdahe.common.util.jwtConfig.JwtConfig;
import top.wdahe.entity.User;
import top.wdahe.service.RedisService;


import static top.wdahe.common.util.spring.SpringContextUtil.getBeanByClass;

@Slf4j
public class SessionUtil {
    private static  final  RedisService redisService = getBeanByClass(RedisService.class);
    public static  void  setUserSession(User user) {
        /*计算token的有效时间*/
        long expireTime = GeneratorUtil.generateExpireTime(Const.CONST_half_month);
        user.setToken(JwtConfig.generateTokenWithOpenid(user.getWxOpenid(),expireTime));
        redisService.hset(Const.Const_user_session_map,user.getWxOpenid(),"" + expireTime);
        log.info("添加小程序登录缓存:",user);
    }

    public static String getToken(HttpServletRequest request) throws ServiceException {
        String token = request.getHeader(Const.Const_token);
        if(StringUtils.isEmpty(token))
            token = request.getParameter(Const.Const_token);
        if(token == null)
            throw ServiceException.CONST_user_not_login;
        return token;
    }

    //检测用户是否登录
    public static boolean checkUserLogin(String wxOpenid) {
        return redisService.hHasKey(Const.Const_user_session_map,wxOpenid);
    }

    //通过request获取用户openid
    public static String getCurrentWxOpenidFromRequest(HttpServletRequest request) throws ServiceException {
        String wxOpenid = request.getAttribute(Const.CONST_wx_openid).toString();
        if(wxOpenid == null) {
            throw ServiceException.CONST_user_not_login;
        }
        return wxOpenid;
    }

    //用户登出
    public static void logout(HttpServletRequest request) {
        redisService.hdel(Const.Const_user_session_map,request.getAttribute(Const.CONST_wx_openid));

    }
}
