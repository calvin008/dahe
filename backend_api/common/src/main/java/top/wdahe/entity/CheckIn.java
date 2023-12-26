package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName check_in
 */
@TableName(value ="check_in")
@Data
public class CheckIn implements Serializable {
    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Date checkInTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}