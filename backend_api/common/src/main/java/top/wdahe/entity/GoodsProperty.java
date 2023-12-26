package top.wdahe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品属性，甜度，温度等
 * @TableName goods_property
 */
@TableName(value ="goods_property")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsProperty implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 
     */
    private Integer goodsId;

    /**
     * 内置的属性类型
     */
    private String category;

    /**
     * 可选项，eg: 常温
     */
    private String propertyOption;

    /**
     * 是否作为默认选择tinyInt
     */
    private Boolean isDefault;

    /**
     * 重置基础价格,只有选择大小属性时需要
     */
    private Integer rebasePrice;

    /**
     * 额外价格
     */
    private Integer extraPrice;

    /**
     * 
     */
    private Integer stock;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}