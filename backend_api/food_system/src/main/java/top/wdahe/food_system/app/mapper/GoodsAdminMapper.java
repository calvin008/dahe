package top.wdahe.food_system.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import top.wdahe.entity.dto.GoodsDTO;

public interface GoodsAdminMapper extends BaseMapper<GoodsDTO> {


    @Update("update goods SET goods_category_name=#{param2} where goods_category_name=#{param1};")
    int updateGoodsCategory(String oldName,String name);


    @Update("update goods set is_sell = !is_sell where id=#{param1};")
    int updateSellStatus(Integer goodsId);

    @Update("update goods set default_price = #{param2} Where id =#{param1};")
   int updateGoodsDefaultPrice(Integer goodsId, Integer realPrice);
}
