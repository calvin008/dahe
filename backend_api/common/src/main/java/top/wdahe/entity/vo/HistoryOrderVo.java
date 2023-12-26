package top.wdahe.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "历史订单信息")
public class HistoryOrderVo {
    private String orderNo;

    private String orderStatus;

    private String goodsPreview;

    private Integer goodsTotalNum;

    private String createTime;

    private Integer payPrice;
}
