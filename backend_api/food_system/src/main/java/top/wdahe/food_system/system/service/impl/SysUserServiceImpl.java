package top.wdahe.food_system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.constant.Const;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.common.util.GeneratorUtil;
import top.wdahe.entity.SysUser;
import top.wdahe.food_system.common.session.SessionUtil;
import top.wdahe.food_system.entity.dto.SysUserDTO;
import top.wdahe.food_system.entity.form.SysUserLoginForm;
import top.wdahe.food_system.system.mapper.RoleMapper;
import top.wdahe.food_system.system.mapper.RolePermissionMapper;
import top.wdahe.food_system.system.mapper.SysUserMapper;
import top.wdahe.food_system.system.service.SysUserService;
import top.wdahe.service.RedisService;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Transactional
    @Override
    public SysUserDTO login(SysUserLoginForm loginVo, HttpServletRequest request) throws ServiceException {
            //校验验证码
        String verifyCode = String.valueOf(redisService.get(Const.CONST_verify_code_redis_prefix + loginVo.getUuid()));
        redisService.del(Const.CONST_verify_code_redis_prefix + loginVo.getUuid());
        if(StringUtils.isEmpty(verifyCode) || !verifyCode.equals(loginVo.getVerifyCode().trim().toLowerCase()))
            throw ServiceException.CONST_verify_code_error_or_expire;
        //校验账号密码正确
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("username",loginVo.getUsername())
                .eq("password", GeneratorUtil.md5Base64(loginVo.getPassword())));

        if(user == null) {
            log.error("管理员登录失败:",loginVo);
            throw ServiceException.CONST_login_failed;
        }

        //确定账号是否可用
        if(!user.getStatus()) {
            log.error("管理员登录失败,账号已经冻结",user);
            throw ServiceException.CONST_user_is_forbidden;
        }

        SysUserDTO userDTO = new SysUserDTO();
        BeanUtils.copyProperties(user,userDTO,"password");
        /**
         * 给权限
         */
        userDTO.setPermissions(new HashSet<>(rolePermissionMapper.selectPermissionByRoleId(user.getRoleId())));
        userDTO.setRoleName(roleMapper.selectById(user.getRoleId()).getName());

        //生成token
        SessionUtil.setSysUserSession(userDTO);



        //return
        return userDTO;


    }

    @Transactional
    @Override
    public SysUserDTO loginByToken(HttpServletRequest request)throws Exception {
        SysUserDTO userDTO = SessionUtil.getCurrentSysUserDTO(request);
        if(userDTO == null) {
            log.error("管理员登录失败,token已经过期");
            throw ServiceException.CONST_token_expire;
        }
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id",userDTO.getId()));
        if(user == null) {
            log.error("管理员登录失败:",user);
            throw ServiceException.CONST_login_failed;
        }

        //确定账号是否可用
        if(!user.getStatus()) {
            log.error("管理员登录失败,账号已经冻结",user);
            throw ServiceException.CONST_user_is_forbidden;
        }

        /**
         * 给权限
         */
        userDTO.setPermissions(new HashSet<>(rolePermissionMapper.selectPermissionByRoleId(user.getRoleId())));
        userDTO.setRoleName(roleMapper.selectById(user.getRoleId()).getName());

        SessionUtil.setSysUserSession(userDTO); //续期
        return userDTO;
    }


}
