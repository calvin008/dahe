package top.wdahe.food_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.annotation.NeedLogin;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.dto.IntegralDTO;
import top.wdahe.entity.vo.IntegralVO;
import top.wdahe.food_app.service.impl.IntegralServiceImpl;

import java.util.List;

@Tag(name = "积分签到服务")
@RestController
@RequestMapping("/api-app/integral")
public class IntegralController {

    @Resource
    private IntegralServiceImpl integralService;


    @Operation(summary = "签到")
    @NeedLogin
    @PostMapping("/sign")
    public Result<IntegralVO> signIn(String wxOpenid) throws ServiceException {
        return Result.ok(integralService.signIn(wxOpenid));
    }

    @Operation(summary = "获取用户连续签到数据")
    @NeedLogin
    @GetMapping("/signList")
    public Result<List<IntegralDTO>> signList(@Parameter(description = "微信openId") String wxOpenid) throws ServiceException {
            return Result.ok(integralService.signInList(wxOpenid));
    }


    @Operation(summary = "获取用户连续签到天数")
    @NeedLogin
    @GetMapping("/signDay")
    public Result<Integer> signDay(@Parameter(description = "微信openId") String wxOpenid) throws ServiceException  {
        return Result.ok(integralService.signDay(wxOpenid));
    }
}
