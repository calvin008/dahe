package top.wdahe.common.enums;

public enum GoodsPropertyCategory {

    ENUM_size("大小"),
    ENUM_temperature("温度"),
    ENUM_sweetness("甜度"),
    ENUM_taste("口味"),
    ENUM_Feeding("加料");
    public String value;

    GoodsPropertyCategory(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
