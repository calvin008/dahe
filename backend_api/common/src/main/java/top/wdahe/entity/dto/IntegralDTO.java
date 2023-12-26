package top.wdahe.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户积分签到返回日期")
public class IntegralDTO {

    //当前签到时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT-8")
    private LocalDateTime date;


    //用户签到添加的积分
    private String info;

    public IntegralDTO(LocalDateTime localdate,String s) {
        date = localdate;
        info = s;
    }

}
