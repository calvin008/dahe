package top.wdahe.common.enums;


//订单取餐方式
public enum OrderTakeType {
    ENUM_take_in("到店自取"),
    ENUM_take_out("外卖配送");

    public String value;

    OrderTakeType(String value) {
        this.value = value;
    }

    public String toString() {
        return  value;
    }
}
