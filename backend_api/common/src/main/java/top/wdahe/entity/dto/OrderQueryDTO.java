package top.wdahe.entity.dto;

import lombok.Data;

@Data
public class OrderQueryDTO {
    private String orderNo;
    private Integer storeId;

    private String orderStatus;

    private String createTime;

    private String finishTime;



}
