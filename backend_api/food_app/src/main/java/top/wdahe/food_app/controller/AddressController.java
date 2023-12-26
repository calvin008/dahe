package top.wdahe.food_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import top.wdahe.common.annotation.NeedLogin;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.result.Result;
import top.wdahe.entity.Address;
import top.wdahe.entity.form.CreateAddressForm;
import top.wdahe.food_app.service.impl.AddressServiceImpl;
import top.wdahe.food_app.util.session.SessionUtil;

import java.util.List;

@Tag(name = "地址服务")
@RestController
@RequestMapping("/api-app/address")
public class AddressController {

    @Resource
    private AddressServiceImpl addressService;

    @Operation(summary = "新增地址")
    @NeedLogin
    @PostMapping("/create")
    public Result<Integer> createAddress(@RequestBody CreateAddressForm addressPrams, HttpServletRequest request) throws ServiceException {
        return  addressService.creatAddress(addressPrams, SessionUtil.getCurrentWxOpenidFromRequest(request));
    }

    @Operation(summary = "获取用户地址列表")
    @NeedLogin
    @GetMapping("/list/{wxOpenid}")
    public Result<List<Address>> getAddressList(@PathVariable String wxOpenid) {
        return Result.ok(addressService.getAddressList(wxOpenid));
    }

    @Operation(summary = "地址更新")
    @NeedLogin
    @PutMapping("/update")
    public Result<Integer> updateAddress(@RequestBody CreateAddressForm addressForm) {
        return Result.ok(addressService.updateAddress(addressForm));
    }

    @Operation(summary = "地址删除")
    @NeedLogin
    @DeleteMapping("/delete/{addressId}")
    public Result<Integer> deleteAddress(@PathVariable String addressId) {
        return Result.ok(addressService.deleteAddress(addressId));
    }




}
