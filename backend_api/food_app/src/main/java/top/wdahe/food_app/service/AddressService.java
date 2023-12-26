package top.wdahe.food_app.service;

import top.wdahe.common.util.result.Result;
import top.wdahe.entity.Address;
import top.wdahe.entity.form.CreateAddressForm;

import java.util.List;

public interface AddressService {


    Result<Integer> creatAddress(CreateAddressForm addressPrams, String wxOpenid);

    //获取用户地址
    List<Address> getAddressList(String wxOpenid);

    //删除地址
    Integer deleteAddress(String addressId);

    //地址修改
    Integer updateAddress(CreateAddressForm addressForm);
}
