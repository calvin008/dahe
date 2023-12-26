package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户签到表
 * @TableName integral
 */
@TableName(value ="integral")
@Data
@NoArgsConstructor
public class Integral implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 签到用户openId
     */
    private String wxOpenid;

    /**
     * 连续签到天数
     */
    private Integer continueDays;

    /**
     * 更新日期, 最后签到日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    public Integral(String wxOpenid,Integer continueDays, LocalDateTime updateTime) {
        this.wxOpenid = wxOpenid;
        this.continueDays = continueDays;
        this.updateTime = updateTime;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}