package top.wdahe.food_system.common.sysMenu;

import top.wdahe.food_system.entity.dto.SysMenuDTO;
import top.wdahe.food_system.entity.vo.RouterVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuUtils {
    /**
     * 生成路由数据格式
     */
    public static List<RouterVo> makeRouter(List<SysMenuDTO> menuList,Integer pid) {
        List<RouterVo> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item !=null && item.getParentId() == pid)
                .forEach(item -> {
                    RouterVo router = new RouterVo();
                    router.setName(item.getMenuName());
                    router.setPath(item.getRouter());
                    router.setRedirect(item.getRouter());
                    //返回文件的路径菜单
                    if(item.getParentId() == 0) {
                        router.setComponent("Layout");

                    } else {
                        router.setComponent(item.getViewPath());
                    }

                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getKeepalive(),
                            item.getIsShow()
                    ));

                    //设置children
                    if(item.getChildren().isEmpty()) {
                        RouterVo child = new RouterVo();
                        child.setPath(item.getRouter());
                        child.setName(item.getMenuName());
                        child.setComponent(item.getViewPath());
                        child.setMeta(router.new Meta(
                                item.getTitle(),
                                item.getIcon(),
                                item.getKeepalive(),
                                item.getIsShow()
                        ));

                        List<RouterVo> children = new ArrayList<>();
                        children.add(child);
                        router.setChildren(children);
                    } else  {
                        List<RouterVo> children = makeRouter(item.getChildren(), item.getMenuId());
                        router.setChildren(children);
                    }
                    list.add(router);
                });

        return  list;
    }
}
