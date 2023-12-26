package top.wdahe.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户积分签到返回数据")
@Data
public class IntegralVO {
    //签到状态
    private Integer daysDiff;

    //当前的签到时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT-8")
    private LocalDateTime updateTime;

    //连续签到天数
    private Integer continueDays;

    //用户积分
    private Integer integrals;
}
