package top.wdahe.food_system.system.service;

import org.springframework.transaction.annotation.Transactional;
import top.wdahe.entity.SysRole;
import top.wdahe.food_system.entity.form.UpdateRolePermissionForm;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    //分页查询
    @Transactional
    List<SysRole> listByPageAndCondition(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception;

    @Transactional
    void  updateRolePermission(UpdateRolePermissionForm vo) throws Exception;
}
