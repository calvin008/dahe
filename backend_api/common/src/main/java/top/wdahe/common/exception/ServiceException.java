package top.wdahe.common.exception;

import lombok.Data;

@Data
public class ServiceException extends Exception{

    private int code = 400;
    public ServiceException(String msg) {super(msg);}
    public ServiceException(String msg,int code){
        super(msg);
        this.code = code;
    }
    /*user*/
    public final static ServiceException CONST_user_not_exist = new ServiceException("用户不存在");
    public final static ServiceException CONST_user_is_forbidden = new ServiceException("用户已经冻结",10003);

    public final static ServiceException CONST_user_not_login = new ServiceException("用户未登录",10003);
    public final static ServiceException CONST_user_not_failed = new ServiceException("通过微信登录失败");

    public final static ServiceException CONST_token_is_not_validate = new ServiceException("token无效或者已经过期",10001);

    public final static ServiceException CONST_weixin_pay_exception = new ServiceException("微信支付异常");

    public final static ServiceException CONST_confirm_receive_failed =
            new ServiceException("确认收货失败");

    public final static ServiceException CONST_order_page_exception = new ServiceException("订单分页查询异常");
    public final static ServiceException CONST_order_handling_exception = new ServiceException("进行中订单查询异常");

    public final static ServiceException CONST_order_detail_exception = new ServiceException("订单详情查询异常");

    public final static ServiceException CONST_verify_code_error_or_expire =
            new ServiceException("验证码错误或已失效");

    public final static ServiceException CONST_login_failed =
            new ServiceException("账号不存在或密码错误");

    public final static ServiceException CONST_not_authorized =
            new ServiceException("权限不足", 10002);
    public final static ServiceException CONST_can_not_change_role_of_super_system_admin_user =
            new ServiceException("无法修改超级管理员的权限");

    public final static ServiceException CONST_goods_image_upload_failed =
            new ServiceException("文件上传失败,超过上传大小限制");
    public final static ServiceException CONST_goods_image_format_invalid =
            new ServiceException("文件上传失败,格式不是图片格式");

    public final static ServiceException Const_current_order_status_can_not_change =
            new ServiceException("订单状态无法改变");
    public final static ServiceException CONST_order_not_exist =
            new ServiceException("订单不存在");
    public final static ServiceException CONST_token_expire = new ServiceException("token已经过期或者失效");

}
