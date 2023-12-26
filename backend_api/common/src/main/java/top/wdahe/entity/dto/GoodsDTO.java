package top.wdahe.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import top.wdahe.entity.Goods;
import top.wdahe.entity.GoodsProperty;

import java.util.List;

@Data
@TableName("goods")
public class GoodsDTO extends Goods {
    @TableField(exist = false)
    private List<GoodsProperty> goodsPropertyList;
}

