package top.wdahe.food_system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RouterVo {

    private  String path;

    private String redirect;

    private String name;

    private String component;

    private Meta meta;
    @Data
    @AllArgsConstructor
    public class  Meta {
        private String title;
        private String icon;

        private Integer keepAlive;

        private Integer isShow;


    }

    private List<RouterVo> children = new ArrayList<>();
}
