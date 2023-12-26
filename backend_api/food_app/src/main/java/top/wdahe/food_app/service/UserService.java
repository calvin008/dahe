package top.wdahe.food_app.service;

import top.wdahe.common.exception.ServiceException;
import top.wdahe.entity.User;
import top.wdahe.entity.form.LoginByWeixinForm;
import top.wdahe.entity.form.UpdateUserForm;


public interface UserService {
    User loginByWeixin(LoginByWeixinForm loginByWeixinForm) throws ServiceException;

    User getUser(String wxOpenid);

    int updateUser(UpdateUserForm form, String wxOpenid) throws Exception;


}
