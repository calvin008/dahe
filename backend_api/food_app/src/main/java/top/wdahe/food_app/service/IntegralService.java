package top.wdahe.food_app.service;

import org.springframework.transaction.annotation.Transactional;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.dto.IntegralDTO;
import top.wdahe.entity.vo.IntegralVO;

import java.util.List;

public interface IntegralService {
    IntegralVO signIn(String wxOpenid) throws ServiceException;

    @Transactional(rollbackFor = Exception.class)
    List<IntegralDTO> signInList(String wxOpenid);

    @Transactional(rollbackFor = Exception.class)
    Integer signDay(String wxOpenid);
}
