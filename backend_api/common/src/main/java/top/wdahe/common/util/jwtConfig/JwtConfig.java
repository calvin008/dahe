package top.wdahe.common.util.jwtConfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import top.wdahe.common.exception.ServiceException;

import java.util.Date;

@Slf4j
public class JwtConfig {
    private static  final  String CONST_jwt_secret = "wong_da_he_~#$%^@#@#()_+";
    public static  String generateTokenWithOpenid(String wxOpenid,long expireTime) {
        try {
            return JWT.create()
                    .withClaim("wxOpenid",wxOpenid) //设置payload荷载
                    .withExpiresAt(new Date(expireTime)) //指定过期时间
                    .sign(Algorithm.HMAC256(CONST_jwt_secret));
        } catch (Exception e) {
            log.error("error:",e.getMessage());
            return null;
        }



    }


    //验证token
    public static boolean verify(String token) {
        try {
            //通过秘钥进行解密
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(CONST_jwt_secret)).build();
            //通过verifier的verify方法验证token
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            log.error("token已经过期",e.getMessage());
            return false;
        } catch (JWTVerificationException e) {
            log.error("token无效",e.getMessage());
            return false;
        }
    }

    //从token中获取用户的wxOpenid
    public static String getWxOpenid(String token) throws ServiceException {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("wxOpenid").asString();
        } catch (JWTDecodeException e) {
            log.error("decode error",e.getMessage());
            throw ServiceException.CONST_token_is_not_validate;
        }
    }

    public static  String generateTokenWithUserId(Integer userId,long expireTime) {
        try {
            return JWT.create()
                    .withClaim("userId",userId) //设置payload荷载
                    .withExpiresAt(new Date(expireTime)) //指定过期时间
                    .sign(Algorithm.HMAC256(CONST_jwt_secret));
        }catch (Exception e){
            log.error("error:",e.getMessage());
            return null;
        }
    }

    //从token中获取用户id
    public static  Integer getSysUserId(String token) throws ServiceException {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asInt();
        } catch (JWTDecodeException e) {
            log.error("error:",e.getMessage());
            throw ServiceException.CONST_token_is_not_validate;
        }
    }

}
