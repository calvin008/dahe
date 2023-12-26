package top.wdahe.common.constant;

public final class Const {
    /*redis的key值名称*/
    //小程序配置信息
    public static final  String CONST_app_config = "app_config";

    //商品菜单缓存
    public static final  String CONST_goods_menu_vo_cache = "Goods_menus_cache";

    // ****************** 时间/秒 ************************//
    public static final long COSNT_one_minute = 60;
    public  static final long CONST_half_hour = COSNT_one_minute * 30;
    public static final long CONST_one_hour = CONST_half_hour * 2;
    public static final long CONST_one_day = CONST_one_hour * 24;
    public static final long CONST_one_week = CONST_one_day * 7;
    public static final long CONST_half_month = CONST_one_week * 2;
    public static final long CONST_one_month = CONST_half_month * 2;
    // ****************** 用户 ************************//
    public static final String Const_user_session_map = "user_session_map";
    public static final String Const_token = "token";
    public static final String CONST_wx_openid = "wxOpenid";

    public static final String CONST_lock_redis_prefix = "lock:";
    // 最新订单消息列表
    public static final String CONST_recent_order_message_map = "recent_order_message_map";
    // 验证码
    public static final String CONST_verify_code_redis_prefix = "verify_code:";
    // 管理员会话缓存
    public static final String CONST_sys_user_session_map = "sys_user_session_map";

    // sysUserId
    public static final String CONST_sys_user_id = "sysUserId";
    // 令牌变量名
    public static final String CONST_token = "token";

}
