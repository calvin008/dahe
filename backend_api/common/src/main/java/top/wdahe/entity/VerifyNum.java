package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName verify_num
 */
@TableName(value ="verify_num")
@Data
public class VerifyNum implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer verifyId;

    /**
     * 
     */
    private Integer verifyContent;

    /**
     * 
     */
    private Integer verifyStatus;

    /**
     * 
     */
    private Integer verifyCategory;

    /**
     * 
     */
    private String storeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}