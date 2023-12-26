package top.wdahe.food_system.system.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.food_system.entity.dto.SysDepartmentDTO;
import top.wdahe.food_system.system.mapper.SysDepartmentMapper;
import top.wdahe.food_system.system.service.SysDepartmentService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SysDepartmentServiceImpl implements SysDepartmentService {

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    @Override
    @Transactional
    public List<SysDepartmentDTO> getDepartment() {
        //获取全部dep
        List<SysDepartmentDTO> AllDp = sysDepartmentMapper.selectList(null);
        //过滤数据
        return AllDp.stream()
                .filter(item -> item.getParentId().equals(0)) //parentId为0的顶级部门
                .map(item -> item.setChildren(getChild(item.getId(), AllDp)))
                .sorted(Comparator.comparingInt(item -> (item.getOrderNum() == null ? 0 : item.getOrderNum())))
                .collect(Collectors.toList());


    }


    public  List<SysDepartmentDTO> getChild(Integer Id,List<SysDepartmentDTO> allDepartment) {
        return allDepartment.stream()
                .filter(item -> item.getParentId().equals(Id))
                .map(item -> item.setChildren(getChild(item.getId(),allDepartment)))
                .sorted(Comparator.comparingInt(item -> (item.getOrderNum() == null ? 0 : item.getOrderNum())))
                .collect(Collectors.toList());
    }

}
