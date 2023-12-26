package top.wdahe.entity.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "小程序相关配置")
public class AppConfig {
    @Schema(description = "店铺名称")
    private String shopName = "王大合美食城";

    @Schema(description = "包装费(单位: 分)")
    private int packingPrice = 0;

    @Schema(description = "外卖配送费(单位: 分)")
    private int sendingPrice = 0;

    @Schema(description = "起送需要的最低价格")
    private int sendingNeedLeastPrice = 2000;

    @Schema(description = "营业开始时间, 整点(不要大于结束时间)")
    private int businessStartTime = 8;

    @Schema(description = "营业结束时间, 整点(不要小于开始时间)")
    private int businessEndTime = 24;

    @Schema(description = "联系电话")
    private String telephone= "18388888888";

    @Schema(description = "备用联系电话")
    private String altTelephone = "18388888888";

    @Schema(description = "联系QQ")
    private String ContactQQ = "2888888";

    @Schema(description = "店铺开放状态, 商家休息|营业中")
    private Boolean shopStatus = true;

    @Schema(description = "菜单上面的公告通知")
    private String shopNotice = "王大合美食城,11点到21点可以送餐~";

    @Schema(description = "允许测试号登录")
    private Boolean testUserLoginEnabled = true;
}
