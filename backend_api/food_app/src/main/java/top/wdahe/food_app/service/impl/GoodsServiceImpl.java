package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;
import top.wdahe.common.constant.Const;
import top.wdahe.entity.Goods;
import top.wdahe.entity.GoodsCategory;
import top.wdahe.entity.GoodsProperty;
import top.wdahe.entity.vo.GoodsMenuVO;
import top.wdahe.entity.vo.GoodsVO;
import top.wdahe.entity.vo.SameCategoryPropertyVO;
import top.wdahe.common.enums.GoodsPropertyCategory;
import top.wdahe.food_app.mapper.GoodsCategoryMapper;
import top.wdahe.food_app.mapper.GoodsMapper;
import top.wdahe.food_app.mapper.GoodsPropertyMapper;
import top.wdahe.food_app.service.GoodsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPropertyMapper goodsPropertyMapper;

    @Resource
    private RedisTemplate redisTemplate;

    //本地缓存
    private List<GoodsMenuVO> goodsMenuVOSLocalCache = new ArrayList<>();

    public List<GoodsMenuVO> getGoodsMenuDetailList() {
            //判断是否读取本地缓存菜单
            Object o = redisTemplate.opsForValue().get(Const.CONST_goods_menu_vo_cache);
            if(o !=null && !CollectionUtils.isEmpty(goodsMenuVOSLocalCache)) {
                System.out.println("缓存数据");
                return goodsMenuVOSLocalCache;
            }

            //  获取所有类目
            List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.selectList(
                    new QueryWrapper<GoodsCategory>().eq("show_status",1)
            );
            //使用2个hashMap去关联关系
        HashMap<String,List<Goods>> sameCategoryGoodsMp = new HashMap<>(goodsCategoryList.size());
        HashMap<String,GoodsMenuVO> goodsMenuVOMap = new HashMap<>(goodsCategoryList.size()) ;
        for(GoodsCategory goodsCategory:goodsCategoryList) {
            sameCategoryGoodsMp.put(goodsCategory.getName(),new ArrayList<>());
            GoodsMenuVO goodsMenuVO = new GoodsMenuVO();
            goodsMenuVO.setGoodsCategoryName(goodsCategory.getName());
            goodsMenuVO.setDisplayOrder(goodsCategory.getDisplayOrder());
            goodsMenuVO.setGoodsCategoryShow(goodsCategory.getShowStatus());
            goodsMenuVOMap.put(goodsCategory.getName(),goodsMenuVO);
        }

        //得到所有商品
        List<Goods> allGoods = goodsMapper.selectList(
                new QueryWrapper<Goods>().eq("is_sell",1)
        );

        //将所有商品分类
        for(Goods goods: allGoods)
            if(sameCategoryGoodsMp.containsKey(goods.getGoodsCategoryName()))
                sameCategoryGoodsMp.get(goods.getGoodsCategoryName()).add(goods);
        //关联商品排序,放到对应类目
        for( Map.Entry<String,GoodsMenuVO> goodsMenuVOEntry :goodsMenuVOMap.entrySet()) {
            List<Goods> sameCategoryGoodsList = sameCategoryGoodsMp.get(goodsMenuVOEntry.getKey());
            sameCategoryGoodsList.sort(((o1, o2) -> o1.getDisplayOrder() - o2.getDisplayOrder()));
            List<GoodsVO> goodsVos = new ArrayList<>();
            //填充商品属性
            for(Goods goods : sameCategoryGoodsList) {
                List<GoodsProperty> goodsPropertyList = goodsPropertyMapper.selectList(
                        new QueryWrapper<GoodsProperty>().eq("goods_id",goods.getId())
                );


                HashMap<String,List<GoodsProperty>> propertyMap = new HashMap<>();
                for(GoodsProperty goodsProperty:goodsPropertyList) {
                    if(propertyMap.containsKey(goodsProperty.getCategory())) {
                        propertyMap.get(goodsProperty.getCategory()).add(goodsProperty);
                    } else {
                        propertyMap.put(goodsProperty.getCategory(),new ArrayList<GoodsProperty>(){{
                            add(goodsProperty);
                        }});
                    }

                    //计算价格
                    if(goodsProperty.getIsDefault() && GoodsPropertyCategory.ENUM_size.value.equals(goodsProperty.getCategory())) {
                        goods.setDefaultPrice(goodsProperty.getRebasePrice());
                    }
                }
                List<SameCategoryPropertyVO> goodsPropertyVos = new ArrayList<>();
                for(Map.Entry<String,List<GoodsProperty>> entry : propertyMap.entrySet()) {
                    //属性类别->转对象
                    SameCategoryPropertyVO goodsPropertyVo = new SameCategoryPropertyVO();
                    goodsPropertyVo.setCategory(entry.getKey());
                    goodsPropertyVo.setRequired(!GoodsPropertyCategory.ENUM_Feeding.value.equalsIgnoreCase(entry.getKey())); //除了加料,其它必选
                    goodsPropertyVo.setPropertyList(entry.getValue());
                    goodsPropertyVos.add(goodsPropertyVo);

                }
                GoodsVO goodsVO = new GoodsVO();
                BeanUtils.copyProperties(goods,goodsVO);//深拷贝
                goodsVO.setGoodsPropertyVos(goodsPropertyVos);
                goodsVO.setRealPrice(goods.getDefaultPrice());
                goodsVO.setImage(goods.getImage());
                goodsVos.add(goodsVO);

            }
            goodsMenuVOEntry.getValue().setGoodsList(goodsVos);
        }
        List<GoodsMenuVO> goodsMenuVOList = new ArrayList<>(goodsMenuVOMap.values());
        redisTemplate.opsForValue().set(Const.CONST_goods_menu_vo_cache,true,60,TimeUnit.MINUTES);
        this.goodsMenuVOSLocalCache = goodsMenuVOList;
        return goodsMenuVOList;
    }
}
