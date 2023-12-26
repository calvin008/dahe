package top.wdahe.food_system.system.service;

import org.springframework.transaction.annotation.Transactional;
import top.wdahe.food_system.entity.dto.SysDepartmentDTO;

import java.util.List;

public interface SysDepartmentService {
    @Transactional
    List<SysDepartmentDTO> getDepartment();
}
