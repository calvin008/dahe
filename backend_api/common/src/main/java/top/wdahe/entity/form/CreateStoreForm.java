package top.wdahe.entity.form;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateStoreForm {


    @TableId(type = IdType.AUTO)
    private Integer storeId;


    /**
     *
     */
    private String storeName;

    /**
     *
     */
    private BigDecimal storeLatitude;

    /**
     *
     */
    private BigDecimal storeLongitude;

    /**
     *
     */
    private String storeCity;

    /**
     *
     */
    private String storeMobile;

    /**
     *
     */
    private String address;
}
