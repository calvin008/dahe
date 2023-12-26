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
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String goodsCategoryName;

    /**
     * 
     */
    private String name;

    /**
     * 显示次序
     */
    private Integer displayOrder;

    /**
     * 默认价格
     */
    private Integer defaultPrice;

    /**
     * 是否在卖
     */
    private Boolean isSell;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 描述
     */
    private String description;

    /**
     * 
     */
    private Integer calorieNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}