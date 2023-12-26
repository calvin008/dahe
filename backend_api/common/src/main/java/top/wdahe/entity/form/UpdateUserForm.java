package top.wdahe.entity.form;

import lombok.Data;

@Data
public class UpdateUserForm {
    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址信息
     */
    private String address;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 微信头像
     */
    private String wxAvatar;
}
