package top.wdahe.food_system.app.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.wdahe.common.config.TencentCos.TencentCOSUploadFileUtil;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.GoodsProperty;
import top.wdahe.entity.dto.GoodsDTO;
import top.wdahe.food_system.app.mapper.GoodsAdminMapper;
import top.wdahe.food_system.app.mapper.GoodsPropertyAdminMapper;

import java.util.List;

@Slf4j
@Service
public class GoodsAdminService {

    @Resource
    private GoodsAdminMapper goodsAdminMapper;

    @Resource
    private GoodsCategoryAdminService goodsCategoryAdminService;

    @Resource
    private GoodsPropertyAdminMapper goodsPropertyAdminMapper;

    /**
     * 分页查询
     */
    public Page<GoodsDTO> getGoodsAdminByPage(int pageNo,int pageSize) {
        Page<GoodsDTO> page = new Page<>(pageNo,pageSize);

        goodsAdminMapper.selectPage(page,new QueryWrapper<GoodsDTO>().orderByAsc("goods_category_name"));
        List<GoodsDTO> goodsVOList = page.getRecords();
        for(GoodsDTO goods:goodsVOList) {
            //设置商品的属性列表
            List<GoodsProperty> goodsPropertyList = goodsPropertyAdminMapper.selectList(
                    new QueryWrapper<GoodsProperty>().eq("goods_id",goods.getId())
            );
            goods.setGoodsPropertyList(goodsPropertyList);
        }
        return page;
    }

    public GoodsDTO getGoodsById(Integer goodsId) {
        GoodsDTO goods = goodsAdminMapper.selectById(goodsId);
        goods.setGoodsPropertyList(
                goodsPropertyAdminMapper.selectList(
                        new QueryWrapper<GoodsProperty>().eq("goods_id",goods.getId()))
        );
        return goods;
    }
    @Transactional
    public int addGoodsAdmin(GoodsDTO goodsDTO) {
        return goodsAdminMapper.insert(goodsDTO);
    }

    @Transactional
    public int deleteGoodsAdminBatchIds (List<Integer> goodsAdminIdList) {
        return goodsAdminMapper.deleteBatchIds(goodsAdminIdList);
    }
    @Transactional
    public int updateGoodsAdmin(GoodsDTO goodsDTO) {
        return goodsAdminMapper.updateById(goodsDTO);
    }


    public  String uploadGoodsImage(MultipartFile file) throws ServiceException {
        try {
            if(null == file) {
                log.error("文件为空");
            }
            //验证文件大小,不能超过1m
            if(file.getSize() > 1024 * 1024)
                throw ServiceException.CONST_goods_image_upload_failed;

            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if(!("jpg".equals(fileSuffix) || "png".equals(fileSuffix) || "jpeg".equals(fileSuffix) || "gif".equals(fileSuffix)) )
                throw ServiceException.CONST_goods_image_format_invalid;

            //获取文件上传cos的链接
            String goodsImageName = TencentCOSUploadFileUtil.uploadFile(file);
            log.info("上传成功,上传文件的地址为:" + goodsImageName);
            return goodsImageName;
        } catch (Exception e) {
            log.error(e.getMessage());
           throw new ServiceException("图片上传失败:" + e.getMessage());
        }
    }

}
