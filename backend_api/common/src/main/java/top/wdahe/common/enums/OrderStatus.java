package top.wdahe.common.enums;

public enum OrderStatus {
    // 进行中
    ENUM_has_not_pay_money("未付款"),
    ENUM_on_making("制作中"),
    ENUM_on_sending("配送中"),
    ENUM_please_take_meal("请取餐"),
    ENUM_has_received("已送达"),
    ENUM_on_refunding("退款中"),

    ENUM_has_completed("已完成"),
    ENUM_has_canceled("已取消"),
    ENUM_has_refunded("已退款");

    public String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
