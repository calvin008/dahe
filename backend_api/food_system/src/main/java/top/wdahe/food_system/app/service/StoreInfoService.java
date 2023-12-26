package top.wdahe.food_system.app.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.StoreInfo;
import top.wdahe.entity.form.CreateStoreForm;
import top.wdahe.food_system.app.mapper.StoreInfoMapper;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StoreInfoService {

    @Resource
    private StoreInfoMapper storeInfoMapper;

    public List<StoreInfo> getStoreList() {
        return storeInfoMapper.selectList(new QueryWrapper<StoreInfo>().orderByDesc("create_time"));
    }


    public Page<StoreInfo> getStoreINfoByPage(int pageNo,int pageSize) {
        Page<StoreInfo> page = new Page<>(pageNo,pageSize);
        return storeInfoMapper.selectPage(page,new QueryWrapper<StoreInfo>().orderByDesc("create_time"));
    }

    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> addStoreInfo(CreateStoreForm storeForm) {
        Date currentTime = new Date();

        StoreInfo storeInfo = new StoreInfo();

        storeInfo.setStoreCity(storeForm.getStoreCity());
        storeInfo.setStoreName(storeForm.getStoreName());
        storeInfo.setStoreLatitude(storeForm.getStoreLatitude());
        storeInfo.setStoreLongitude(storeForm.getStoreLongitude());
        storeInfo.setCreateTime(currentTime);
        storeInfoMapper.insert(storeInfo);
        storeInfoMapper.insert(storeInfo);
        return Result.ok("添加门店成功",storeInfo.getStoreId());
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteStoreInfo (Integer store_id) {
        return storeInfoMapper.deleteById(store_id);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateStoreInfo(Integer store_id,CreateStoreForm storeForm) {
        Date currentTime = new Date();
        UpdateWrapper<StoreInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("store_id",store_id);
        StoreInfo storeInfo = new StoreInfo();

        storeInfo.setStoreCity(storeForm.getStoreCity());
        storeInfo.setStoreName(storeForm.getStoreName());
        storeInfo.setStoreLatitude(storeForm.getStoreLatitude());
        storeInfo.setStoreLongitude(storeForm.getStoreLongitude());
        storeInfo.setUpdateTime(currentTime);
        return storeInfoMapper.update(storeInfo,updateWrapper);
    }

}
