package top.wdahe.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.wdahe.entity.GoodsProperty;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SameCategoryPropertyVO {
    private String category;

    private Boolean required;

    private List<GoodsProperty> propertyList;
}
