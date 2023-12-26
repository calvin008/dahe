package top.wdahe.food_app.service;

import top.wdahe.entity.vo.GoodsMenuVO;

import java.util.List;

public interface GoodsService {
    /**
     * 商品菜单列表
     */
    List<GoodsMenuVO> getGoodsMenuDetailList();

}
