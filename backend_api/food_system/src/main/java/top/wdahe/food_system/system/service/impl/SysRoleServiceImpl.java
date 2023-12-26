package top.wdahe.food_system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.SysRole;
import top.wdahe.entity.SysRolePermission;
import top.wdahe.food_system.entity.form.UpdateRolePermissionForm;
import top.wdahe.food_system.system.mapper.RoleMapper;
import top.wdahe.food_system.system.mapper.RolePermissionMapper;
import top.wdahe.food_system.system.service.SysRoleService;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    //分页查询
    @Transactional
    @Override
    public List<SysRole> listByPageAndCondition(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception{
        QueryWrapper<SysRole> wrapper = new QueryWrapper<SysRole>();
        if(params == null)
            return roleMapper.selectPage(new Page<>(pageNo,pageSize),wrapper).getRecords();

        params.forEach((key,value)-> {
            wrapper.eq(key,value);
        });
        return roleMapper.selectPage(new Page<>(pageNo,pageSize),wrapper).getRecords();

    }

    @Transactional
    @Override
    public void  updateRolePermission(UpdateRolePermissionForm vo) throws ServiceException {
            rolePermissionMapper.delete(new QueryWrapper<SysRolePermission>().eq("role_id",vo.getRoleId()));
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(vo.getRoleId());
            for (String permission : vo.getPermissions()) {
                rolePermission.setPermission(permission);
                rolePermissionMapper.insert(rolePermission);
            }
            log.info("更新角色权限",vo);
    }


}
