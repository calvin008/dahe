package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.wdahe.entity.StoreInfo;
import top.wdahe.food_app.mapper.StoreInfoMapper;
import top.wdahe.food_app.service.StoreInfoService;

import java.util.List;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {

    @Resource
    private StoreInfoMapper storeInfoMapper;
    @Override
    public List<StoreInfo> getStoreInfo() {
        List<StoreInfo> list = storeInfoMapper.selectList(
                new QueryWrapper<StoreInfo>().orderByDesc("create_time")
        );
        if(list != null && list.size()!=0) {
            return list;
        }

        return null;
    }
}
