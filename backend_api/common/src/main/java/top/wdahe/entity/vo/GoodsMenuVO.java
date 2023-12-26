package top.wdahe.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(description = "三级菜单")
public class GoodsMenuVO {
    private Integer displayOrder;

    private String goodsCategoryName;

    private Boolean goodsCategoryShow;

    private List<GoodsVO> goodsList;


}
