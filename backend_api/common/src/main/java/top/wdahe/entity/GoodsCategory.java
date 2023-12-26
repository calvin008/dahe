package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品类别
 * @TableName goods_category
 */
@TableName(value ="goods_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCategory implements Serializable {
    /**
     * 
     */
    @TableId
    private String name;

    /**
     * 显示顺序
     */
    private Integer displayOrder;

    /**
     * 是否显示
     * TinyInt(1)
     */
    private Boolean showStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}