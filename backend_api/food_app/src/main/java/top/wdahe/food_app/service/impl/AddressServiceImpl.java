package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.Address;
import top.wdahe.entity.form.CreateAddressForm;
import top.wdahe.food_app.mapper.AddressMapper;
import top.wdahe.food_app.service.AddressService;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;


    //新增地址
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> creatAddress(CreateAddressForm addressPrams, String wxOpenid) {
        Address address = new Address();
        address.setWxOpenid(wxOpenid);
        address.setAddressContent(addressPrams.getAddressContent());
        address.setName(addressPrams.getName());
        address.setPhone(addressPrams.getPhone());
        address.setSex(addressPrams.getSex());
        Integer num  = addressMapper.insert(address);
        if(num == 1) {
            return Result.ok("添加地址成功",address.getAddressId());
        }
        return null;
    }

    //获取用户地址
    @Override
    public List<Address> getAddressList(String wxOpenid) {
        return addressMapper.selectList(
                new QueryWrapper<Address>().eq("wx_openid",wxOpenid).orderByDesc("create_time")
        );
    }

    //删除地址
    @Override
    public Integer deleteAddress(String addressId) {
        return addressMapper.deleteById(addressId);
    }

    //地址修改
    @Override
    public Integer updateAddress(CreateAddressForm addressForm) {
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("address_id",addressForm.getAddressId());
        Address address = new Address();
        address.setSex(addressForm.getSex());
        address.setAddressContent(addressForm.getAddressContent());
        address.setName(addressForm.getName());
        address.setPhone(addressForm.getPhone());
        return addressMapper.update(address,updateWrapper);
    }



}
