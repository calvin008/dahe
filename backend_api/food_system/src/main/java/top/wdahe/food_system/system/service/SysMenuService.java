package top.wdahe.food_system.system.service;

import org.springframework.transaction.annotation.Transactional;
import top.wdahe.food_system.entity.dto.SysMenuDTO;

import java.util.List;

public interface SysMenuService {
    //获取全部menu
    @Transactional
    List<SysMenuDTO> getMenu();

    @Transactional
    List<SysMenuDTO> getLevelMenu();

    @Transactional
    List<SysMenuDTO> getUserRoleMenu(Integer roleId);
}
