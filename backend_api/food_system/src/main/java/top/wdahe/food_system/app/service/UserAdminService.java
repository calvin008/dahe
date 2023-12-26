package top.wdahe.food_system.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wdahe.entity.User;
import top.wdahe.food_system.app.mapper.UserAdminMapper;

import java.util.List;

@Slf4j
@Service
public class UserAdminService {

    @Resource
    private UserAdminMapper userAdminMapper;

    public List<User> getAllUserAdmins() {
        return userAdminMapper.selectList(null);
    }

    public Page<User> getUserAdminByPage(int pageNo,int pageSize) {
        Page<User> page = new Page<>(pageNo,pageSize);

        return userAdminMapper.selectPage(page,null);
    }

    @Transactional
    public int addUserAdmin(User user){
        return userAdminMapper.insert(user);
    }

    @Transactional
    public int deleteUserAdminBatchIds(List<String> userAdminIdList) {
        return  userAdminMapper.deleteBatchIds(userAdminIdList);
    }

    @Transactional
    public int updateUserAdmin(User user) {
        return userAdminMapper.updateById(user);
    }

}
