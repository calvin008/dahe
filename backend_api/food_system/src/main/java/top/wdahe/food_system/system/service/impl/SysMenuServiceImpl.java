package top.wdahe.food_system.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.food_system.entity.dto.SysMenuDTO;
import top.wdahe.food_system.system.mapper.RolePermissionMapper;
import top.wdahe.food_system.system.mapper.SysMenuMapper;
import top.wdahe.food_system.system.service.SysMenuService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;



    //获取全部menu
    @Transactional
    @Override
    public List<SysMenuDTO> getMenu() {
        List<SysMenuDTO> menuAll = sysMenuMapper.selectList(null);
        return menuAll.stream()
                .filter(item -> item.getParentId().equals(0))
                .map(item -> item.setChildren(getChild(item.getMenuId(), menuAll)))
                .sorted(Comparator.comparingInt(menu -> (menu.getOrderNum() == null ? 0 : menu.getOrderNum())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<SysMenuDTO> getLevelMenu() {
        List<SysMenuDTO> menuAll = sysMenuMapper.selectList(new QueryWrapper<SysMenuDTO>().ne("type",2));
        return menuAll.stream()
                .filter(item -> item.getParentId().equals(0))
                .map(item -> item.setChildren(getChild(item.getMenuId(), menuAll)))
                .sorted(Comparator.comparingInt(menu -> (menu.getOrderNum() == null ? 0 : menu.getOrderNum())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<SysMenuDTO> getUserRoleMenu(Integer roleId) {
        QueryWrapper<SysMenuDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("type",2).in("permission",rolePermissionMapper.selectPermissionByRoleId(roleId));
        List<SysMenuDTO> menuAll = sysMenuMapper.selectList(queryWrapper);
        return menuAll.stream()
                .filter(item -> item.getParentId().equals(0))
                .map(item -> item.setChildren(getChild(item.getMenuId(), menuAll)))
                .sorted(Comparator.comparingInt(menu -> (menu.getOrderNum() == null ? 0 : menu.getOrderNum())))
                .collect(Collectors.toList());
    }


    public List<SysMenuDTO> getChild(Integer MenuId,List<SysMenuDTO> allMenu) {
        return allMenu.stream()
                .filter(item -> item.getParentId().equals(MenuId))
                .map(item -> item.setChildren(getChild(item.getMenuId(), allMenu)))
                .sorted(Comparator.comparingInt(menu -> (menu.getOrderNum() == null ? 0 : menu.getOrderNum())))
                .collect(Collectors.toList());
    }
}
