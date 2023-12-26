package top.wdahe.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.wdahe.entity.Goods;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO extends Goods {
    private Integer realPrice;

    private String propertyStr;

    private List<SameCategoryPropertyVO> goodsPropertyVos;
}
