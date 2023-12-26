package top.wdahe.food_system.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import top.wdahe.entity.GoodsCategory;

public interface GoodsCategoryAdminMapper extends BaseMapper<GoodsCategory> {

    @Update("update goods_category set name=#{param2}, display_order =#{param3} " +
           "where name =#{param1};")
    int updateGoodsCategoryAdmin(String oldName,String newName, Integer displayOrder);

    @Update("update goods_category set show_status = !show_status where name=#{param1};")
    int updateCategoryShowStatus(String name);
}
