package top.wdahe.food_system.system.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.SysUser;
import top.wdahe.food_system.entity.dto.SysUserDTO;
import top.wdahe.food_system.entity.form.SysUserLoginForm;

import java.util.List;

public interface SysUserService {
    @Transactional
    SysUserDTO login(SysUserLoginForm loginVo, HttpServletRequest request) throws ServiceException;


    @Transactional
    SysUserDTO loginByToken(HttpServletRequest request)throws Exception;


}
