package top.wdahe.food_system.app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.entity.GoodsCategory;
import top.wdahe.entity.dto.GoodsDTO;
import top.wdahe.food_system.app.mapper.GoodsAdminMapper;
import top.wdahe.food_system.app.mapper.GoodsCategoryAdminMapper;

import java.util.List;

@Slf4j
@Service
public class GoodsCategoryAdminService {

    @Resource
    private GoodsCategoryAdminMapper goodsCategoryAdminMapper;

    @Resource
    private GoodsAdminMapper goodsAdminMapper;

    public List<GoodsCategory> getAllGoodsCategoryAdmins() {
        return goodsCategoryAdminMapper.selectList(
                new QueryWrapper<GoodsCategory>().orderByAsc("display_order")
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public int addGoodsCategoryAdmin(GoodsCategory goodsCategory) {
        return goodsCategoryAdminMapper.insert(goodsCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    public  int deleteGoodsCategoryAdminByName(String categoryName) {
        goodsAdminMapper.delete(new QueryWrapper<GoodsDTO>().eq("goods_category_name",categoryName));
        return goodsCategoryAdminMapper.delete(
                new QueryWrapper<GoodsCategory>().eq("name",categoryName)
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateGoodsCategoryAdmin(String oldName,GoodsCategory goodsCategory) {
        goodsAdminMapper.updateGoodsCategory(oldName,goodsCategory.getName());
        return goodsCategoryAdminMapper.updateGoodsCategoryAdmin(oldName,goodsCategory.getName(),goodsCategory.getDisplayOrder());
    }





}
