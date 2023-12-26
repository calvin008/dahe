package top.wdahe.food_app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.User;
import top.wdahe.entity.form.LoginByWeixinForm;
import top.wdahe.entity.form.UpdateUserForm;
import top.wdahe.food_app.mapper.UserMapper;
import top.wdahe.food_app.service.UserService;
import top.wdahe.food_app.util.session.SessionUtil;
import top.wdahe.service.WeixinService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    WeixinService weixinService;
    @Resource
    UserMapper userMapper;

    @Override
    public User loginByWeixin(LoginByWeixinForm form) throws ServiceException {
       String wxOpenid = weixinService.getWeiXinOpenid(form.getCode());

        if(wxOpenid != null) {
            User user = userMapper.selectById(wxOpenid);
            if(user == null) { //没有账号就创建账号
                user = new User();
                user.setWxOpenid(wxOpenid);
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                user.setName("weixin_" + uuid);
                user.setStatus(true);
                userMapper.insert(user);
            }
            if(!user.getStatus()) {
                    throw ServiceException.CONST_user_is_forbidden;
            }
            SessionUtil.setUserSession(user);
            return user;

        } else {
            throw ServiceException.CONST_user_not_failed;
        }


    }

    @Override
    public User getUser(String wxOpenid) {
//        查询对应的账号
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>().like(User::getWxOpenid,wxOpenid);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public int updateUser(UpdateUserForm form, String wxOpenid) throws Exception{
        User user = new User();
        user.setName(form.getName());
        user.setAddress(form.getAddress());
        user.setWxAvatar(form.getWxAvatar());
        user.setPhone(form.getPhone());
        user.setSex(form.getSex());

        return userMapper.updateById(user);
    }


}
