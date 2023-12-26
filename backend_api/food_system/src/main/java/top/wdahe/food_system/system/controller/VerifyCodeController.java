package top.wdahe.food_system.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.constant.Const;

import top.wdahe.common.util.result.Result;

import top.wdahe.food_system.common.captcha.CustomArithmeticCaptcha;
import top.wdahe.service.RedisService;

import java.util.HashMap;
import java.util.UUID;

@Tag(name = "验证码服务")
@RestController
@Slf4j
@RequestMapping("/auth")
public class VerifyCodeController {

    @Resource
    private RedisService redisService;

    @Operation(summary = "获取验证码")
    @GetMapping("/code")
    public Result<HashMap<String,String>> getVerifyCodeImage() {
        /*easy*/
        CustomArithmeticCaptcha specCaptcha = new CustomArithmeticCaptcha(130, 48);
        specCaptcha.setLen(2);
        specCaptcha.getArithmeticString();
        specCaptcha.text();
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        //存入redis
        redisService.set(Const.CONST_verify_code_redis_prefix +key,verCode,30);
        return Result.ok(new HashMap<String,String>() {{
            put("uuid",key);
            put("image",specCaptcha.toBase64());
        }});
    }
}
