package top.wdahe.food_app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.wdahe.common.util.QRcodeUtil.QRCodeUtil;
import top.wdahe.common.util.result.Result;

@Tag(name="用户二维码")
@RestController
@RequestMapping("/api-app/QRCode")
public class QRCodeController {

    @Operation(summary = "获取用户二维码")
    @GetMapping("/getQRCodeBase64")
    public Result<String> getQRCode(@RequestParam("openId") String openId,
                                    @RequestParam(value = "logoUrl",required = false) String logoUrl,
                                    @RequestParam(value = "width",required = false) Integer width,
                                    @RequestParam(value = "height",required = false) Integer height) {
        return Result.ok(QRCodeUtil.getBase64QRCode(openId,logoUrl));

    }
}
